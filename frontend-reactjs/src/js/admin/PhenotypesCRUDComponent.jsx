import React from 'react';

// Librerias
import BootstrapTable from 'react-bootstrap-table-next'; // Tabla
import paginationFactory from 'react-bootstrap-table2-paginator'; // Paginacion en tabla
import overlayFactory from 'react-bootstrap-table2-overlay'; // Para la animacion de carga
import filterFactory, { selectFilter } from 'react-bootstrap-table2-filter';

/**
 * Componente que renderiza el panel de CRUD de usuarios
 */
class PhenotypesCRUDComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            phenotypes: [],
            page: 0,
            sizePerPage: 10,
            totalSize: 0,
            selectedRow: [],
            selectedPhenotype: {},
            action: null,
            searchUserInput: ''
        }

        // Variables que no renderizan la vista
        this.addUserModalId = 'add-user-modal';
        this.deleteUserModalId = 'delete-user-modal';

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.getPhenotypes = this.getPhenotypes.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando termina de 
     * renderizar el componente
     */
    componentDidMount() {
        this.getPhenotypes();
    }

    /**
     * Muestra/esconde un modal especifico
     * @param {string} modalId Modal del id que se va a abrir
     * @param {string} modalAction 'show' o 'hide' para mostrar/esconder el modal
     */
    actionModal(modalId, modalAction) {
        $('#' + modalId).modal(modalAction);
    }

    /**
     * Obtiene los fenotipos via AJAX.
     * Para mas info acerca de los parametros: https://react-bootstrap-table.github.io/react-bootstrap-table2/docs/table-props.html#ontablechange-function
     * @param {string} type Tipo de accion realizada en la tabla 
     * @param {*} newState Atributos de la tabla actuales
     */
    getPhenotypes(actionType = '', newState = {}) {
        let self = this;
        // Genero los parametros del request
        let pageNumber = newState.page ? newState.page - 1 : self.state.page;
        let sizePerPage = newState.sizePerPage ? newState.sizePerPage : self.state.sizePerPage;

        let data = {
            newestPage: pageNumber,
            newestSizePerPage: sizePerPage,
            newestSortField: newState.sortField,
            newestSortOrder: newState.sortOrder,
            search: self.state.searchUserInput
        };

        // Cargo los fenotipos numericos y categoricos
        self.setState({
            phenotypes: [],
            loading: true
        }, () => {
            let ajaxNumeric = this.getNumericPhenotypes(data);
            let ajaxCategoric = {};//this.getCategoricPhenotypes(data);
            
            // Cuando terminan de ejecutarse (exitosa o falladamente)
            // se elimina el estado de cargando
            $.when(ajaxNumeric, ajaxCategoric).always(function() {
                self.setState({ loading: false});
            });
        });
    }

    /**
     * Obtiene los fenotipos numericos
     * @param {*} data Objeto con la informacion a enviar
     * @returns El envio ajax para su posterior evaluacion
     */
    getNumericPhenotypes(data) {
        let self = this;
        return $.ajax({
            url: 'http://localhost:8080/numeric-phenotypes/',
            data: data
        }).done(function (jsonReponse, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                let phenotypes = self.state.phenotypes;
                phenotypes = phenotypes.concat(jsonReponse.content);
                self.setState({ phenotypes });
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    /**
    * Obtiene los fenotipos categoricos
    * @param {*} data Objeto con la informacion a enviar
    * @returns El envio ajax para su posterior evaluacion
    */
    getCategoricPhenotypes(data) {
        let self = this;
        return $.ajax({
            url: 'http://localhost:8080/categoric-phenotypes/',
            data: data
        }).done(function (jsonReponse, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                let currentPhenotypes = self.state.phenotypes;
                currentPhenotypes = currentPhenotypes.concat(jsonReponse.content);
                self.setState({ currentPhenotypes });
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    /**
     * Selecciona una fila de la tabla
     * @param {*} row Fila a seleccionar
     * @param {boolean} isSelect True si la fila pasada por parametro esta seleccionada
     */
    selectRow(row, isSelect) {
        if (isSelect) {
            this.setState({
                selectedRow: [row.id], // Solo selecciono uno a la vez, pero debe ser un arreglo para la libreria
                selectedPhenotype: row
            });
        }
    }

    /**
     * Cambia el estado de la accion que estamos realizando
     * (agregando o editando un usuario) para poder reutilizar
     * el modal de alta de usuario
     * @param {string} action 'add' o 'edit' para el modal
     */
    changeAction(action) {
        this.setState({ action: action }, () => {
            // Una vez que cambiamos de accion, abrimos el modal de alta/edicion
            this.actionModal(this.addUserModalId, 'show');
        });
    }

    /**
     * Metodo que se ejecuta cuando los inputs cambian.
     * Sirve para refrescar el state
     * @param {Event} e Evento del cambio del input
     */
    handleChange(e) {
        this.setState({ [e.target.name]: e.target.value }, () => {
            this.getUsers();
        });;
    }

    /**
     * Limpia el estado
     */
    cleanState() {
        this.setState({
            selectedRow: [],
            selectedPhenotype: {}
        });
    }

    render() {
        const selectOptions = {
            0: 'Numérico',
            1: 'Categórico',
            2: 'Todos'
        };

        const columns = [{
            dataField: 'name',
            text: 'Nombre',
            sort: true,
        }, {
            dataField: 'type',
            text: 'Tipo',
            formatter: (cell, row, rowIndex, formatExtraData) => {
                return row.values ? "Categórico" : 'Numérico';
            },
            // filter: selectFilter({
            //     options: selectOptions,
            //     defaultValue: 0
            // })
        }, {
            dataField: 'values',
            text: 'Valor/es',
            formatter: (cell, row, rowIndex, formatExtraData) => {
                return row.values ? row.values.join(", ") : '-';
            }
        }];

        const selectRow = {
            mode: 'radio',
            clickToSelect: true,
            selected: this.state.selectedRow,
            onSelect: (row, isSelect) => {
                this.selectRow(row, isSelect);
            },
            selectionHeaderRenderer: () => <span title="Limpiar" className="cursor-pointer" onClick={this.cleanState}>X</span>
        };
    
        return (
            <div>
                <div className="row">
                    <div className="col-9 button-col">
                        <h4>Acciones</h4>
                        <button type="button" className="btn btn-success" onClick={() => this.changeAction('add')}>Agregar</button>
                        <button type="button" className="btn btn-dark" onClick={() => this.changeAction('edit')} disabled={!this.state.selectedPhenotype.id}>Editar</button>
                        <button type="button" className="btn btn-danger" disabled={!this.state.selectedPhenotype.id}>Eliminar</button>
                    </div>
                    <div className="col-3 button-col">
                        <div className="form-group">
                            <label htmlFor="search-user-input"><h5>Buscar</h5></label>
                            <input type="text"
                                className="form-control"
                                name="searchUserInput"
                                value={this.state.searchUserInput}
                                onChange={this.handleChange}
                                aria-describedby="searchHelpBlockPhenotypes" />
                            <small id="searchHelpBlockPhenotypes" className="form-text text-muted">
                                Buscará por nombre
                            </small>
                        </div>
                    </div>
                </div>
                <div id="admin-panel" className="row">
                    <div className="col-12">
                        <h4>Fenotipos</h4>
                        <BootstrapTable
                            remote={{ pagination: true, filter: true }}
                            keyField='id'
                            data={this.state.phenotypes}
                            columns={columns}
                            page={this.state.page}
                            sizePerPage={this.state.sizePerPage}
                            totalSize={this.state.totalSize}
                            noDataIndication="No hay información para mostrar"
                            onTableChange={this.getPhenotypes}
                            pagination={paginationFactory()}
                            filter={filterFactory()}
                            loading={this.state.loading}
                            selectRow={selectRow}
                            rowClasses="cursor-pointer"
                            overlay={
                                overlayFactory({
                                    spinner: true,
                                    background: 'rgba(192,192,192,0.3)'
                                })
                            } // Animacion mientras carga
                        />
                    </div>
                </div>

            </div>
        );
    }
}

export default PhenotypesCRUDComponent;
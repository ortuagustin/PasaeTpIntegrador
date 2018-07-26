import React from 'react';

// Componentes
import AddPathologyModal from './AddPathologyModal.jsx';

// Librerias
import BootstrapTable from 'react-bootstrap-table-next'; // Tabla
import paginationFactory from 'react-bootstrap-table2-paginator'; // Paginacion en tabla
import overlayFactory from 'react-bootstrap-table2-overlay'; // Para la animacion de carga
import filterFactory from 'react-bootstrap-table2-filter';

/**
 * Componente que renderiza el panel de CRUD de usuarios
 */
class PathologiesCRUDComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            pathologies: [],
            page: 0,
            sizePerPage: 10,
            totalSize: 0,
            selectedRow: [],
            selectedPathology: {},
            action: null,
            searchPhenotypeInput: '',
        }

        // Variables que no renderizan la vista
        this.addPathologyModalId = 'add-pathology-modal';
        this.deletePathologyModalId = 'delete-pathology-modal';

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.getPathologies = this.getPathologies.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando termina de 
     * renderizar el componente
     */
    componentDidMount() {
        this.getPathologies();
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
    getPathologies(actionType = '', newState = {}) {
        let self = this;

        // Genero los parametros del request
        let pageNumber = newState.page ? newState.page - 1 : self.state.page;
        let sizePerPage = newState.sizePerPage ? newState.sizePerPage : self.state.sizePerPage;

        // Cargo los fenotipos numericos y categoricos
        self.setState({ loading: true }, () => {
            $.ajax({
                url: 'http://localhost:8080/pathologies/',
                data: {
                    newestPage: pageNumber,
                    newestSizePerPage: sizePerPage,
                    newestSortField: newState.sortField,
                    newestSortOrder: newState.sortOrder,
                    search: self.state.searchPhenotypeInput
                }
            }).done(function (jsonReponse, textStatus, jqXHR) {
                if (jqXHR.status == 200) {
                    self.setState({
                        pathologies: jsonReponse.content,
                        page: pageNumber,
                        sizePerPage: sizePerPage,
                        totalSize: jsonReponse.totalElements
                    }, () => {
                        self.cleanState();
                    });
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR, textStatus, errorThrown);
            }).always(function() {
                self.setState({  loading: false });
            });
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
                selectedPathology: row
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
            this.actionModal(this.addPathologyModalId, 'show');
        });
    }

    /**
     * Metodo que se ejecuta cuando los inputs cambian.
     * Sirve para refrescar el state
     * @param {Event} e Evento del cambio del input
     */
    handleChange(e) {
        this.setState({ [e.target.name]: e.target.value }, () => {
            this.getPathologies();
        });;
    }

    /**
     * Limpia el estado
     */
    cleanState() {
        this.setState({
            selectedRow: [],
            selectedPathology: {}
        });
    }

    render() {
        const columns = [
            {
                dataField: 'name',
                text: 'Nombre',
                sort: true,
            },
            {
                dataField: 'numericPhenotypes',
                text: 'Fenotipos numéricos',
                formatter: (cell, row, rowIndex, formatExtraData) => {
                    return row.numericPhenotypes ? row.numericPhenotypes.map((numericPhenotype) => {
                        return numericPhenotype.name;
                    }).join(", ") : '-';
                }
            },
            {
                dataField: 'categoricPhenotypes',
                text: 'Fenotipos categóricos',
                formatter: (cell, row, rowIndex, formatExtraData) => {
                    return row.categoricPhenotypes ? row.categoricPhenotypes.map((numericPhenotype) => {
                        return numericPhenotype.name;
                    }).join(", ") : '-';
                }
            }
        ];

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
                        <button type="button" className="btn btn-dark" onClick={() => this.changeAction('edit')} disabled={!this.state.selectedPathology.id}>Editar</button>
                        <button type="button" className="btn btn-danger" onClick={() => this.actionModal(this.deletePathologyModalId, 'show')} disabled={!this.state.selectedPathology.id}>Eliminar</button>
                    </div>
                    <div className="col-3 button-col">
                        <div className="form-group">
                            <label htmlFor="search-user-input"><h5>Buscar</h5></label>
                            <input type="text"
                                className="form-control"
                                name="searchPhenotypeInput"
                                value={this.state.searchPhenotypeInput}
                                onChange={this.handleChange}
                                aria-describedby="searchHelpBlockPathology" />
                            <small id="searchHelpBlockPathology" className="form-text text-muted">
                                Buscará por nombre de patología o fenotipo
                            </small>
                        </div>
                    </div>
                </div>

                <div id="admin-panel" className="row">
                    <div className="col-12">
                        <h4>Patologías</h4>
                        <BootstrapTable
                            remote={{ pagination: true, filter: true }}
                            keyField='id'
                            data={this.state.pathologies}
                            columns={columns}
                            page={this.state.page}
                            sizePerPage={this.state.sizePerPage}
                            totalSize={this.state.totalSize}
                            noDataIndication="No hay información para mostrar"
                            onTableChange={this.getPathologies}
                            pagination={paginationFactory()}
                            filter={filterFactory()}
                            loading={this.state.loading}
                            selectRow={selectRow}
                            rowClasses="cursor-pointer" AddPathologyModal
                            overlay={
                                overlayFactory({
                                    spinner: true,
                                    background: 'rgba(192,192,192,0.3)'
                                })
                            } // Animacion mientras carga
                        />
                    </div>
                </div>

                {/* Modal de alta de fenotipo */}
                <AddPathologyModal
                    modalId={this.addPathologyModalId}
                    action={this.state.action}
                    selectedPathology={this.state.selectedPathology}
                    getPathologies={this.getPathologies}
                    actionModal={this.actionModal}
                />
                
                {/* Modal de confirmacion de eliminacion de fenotipo */}

            </div>
        );
    }
}

export default PathologiesCRUDComponent;
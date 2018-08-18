import React from 'react';

// Componentes
import AddPatientModal from './AddPatientModal.jsx';
import DeletePatientModal from './DeletePatientModal.jsx';

// Librerias
import BootstrapTable from 'react-bootstrap-table-next'; // Tabla
import paginationFactory from 'react-bootstrap-table2-paginator'; // Paginacion en tabla
import overlayFactory from 'react-bootstrap-table2-overlay'; // Para la animacion de carga
import filterFactory from 'react-bootstrap-table2-filter';

/**
 * Componente que renderiza el panel de CRUD de usuarios
 */
class PatientsCRUDComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            patients: [],
            page: 1,
            sizePerPage: 10,
            totalSize: 0,
            selectedRow: [],
            selectedPatient: {},
            action: null,
            searchPatientInput: '',
        }

        // Variables que no renderizan la vista
        this.addPatientModalId = 'add-pathology-modal';
        this.deletePatientModalId = 'delete-pathology-modal';

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.getPatients = this.getPatients.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.cleanState = this.cleanState.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando termina de 
     * renderizar el componente
     */
    componentDidMount() {
        this.getPatients();
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
    getPatients(actionType = '', newState = {}) {
        let self = this;

        // Genero los parametros del request
        let pageNumber = newState.page ? newState.page : self.state.page;
        let sizePerPage = newState.sizePerPage ? newState.sizePerPage : self.state.sizePerPage;

        // Cargo los pacientes
        self.setState({ loading: true }, () => {
            $.ajax({
                url: 'http://localhost:8080/patients/',
                data: {
                    newestPage: pageNumber - 1,
                    newestSizePerPage: sizePerPage,
                    newestSortField: newState.sortField,
                    newestSortOrder: newState.sortOrder,
                    search: self.state.searchPatientInput
                }
            }).done(function (jsonReponse, textStatus, jqXHR) {
                if (jqXHR.status == 200) {
                    self.setState({
                        patients: jsonReponse.content,
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
                selectedPatient: row
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
            this.actionModal(this.addPatientModalId, 'show');
        });
    }

    /**
     * Metodo que se ejecuta cuando los inputs cambian.
     * Sirve para refrescar el state
     * @param {Event} e Evento del cambio del input
     */
    handleChange(e) {
        this.setState({ [e.target.name]: e.target.value }, () => {
            this.getPatients();
        });;
    }

    /**
     * Limpia el estado
     */
    cleanState() {
        this.setState({
            selectedRow: [],
            selectedPatient: {}
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
                dataField: 'surname',
                text: 'Apellido',
                sort: true,
            },
            {
                dataField: 'dni',
                text: 'DNI',
                sort: true,
            },
            {
                dataField: 'email',
                text: 'Mail',
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

        // Seteos de paginacion
        let paginationOptions = {
            page: this.state.page,
            sizePerPage: this.state.sizePerPage,
            totalSize: this.state.totalSize,
            sizePerPageList: [
                { text: '10', value: 10 },
                { text: '15', value: 15 },
                { text: '30', value: 30 }
            ],
            prePageText: 'Anterior',
            nextPageText: 'Siguiente'
        };
    
        return (
            <div>
                <div className="row">
                    <div className="col-9 button-col">
                        <h4>Acciones</h4>
                        <button type="button" className="btn btn-success" onClick={() => this.changeAction('add')}>Agregar</button>
                        {/* <button type="button" className="btn btn-dark" onClick={() => this.changeAction('edit')} disabled={!this.state.selectedPatient.id}>Editar</button> */}
                        <button type="button"
                            className="btn btn-danger"
                            onClick={() => this.actionModal(this.deletePatientModalId, 'show')}
                            disabled={!this.state.selectedPatient.id}>Eliminar</button>
                    </div>
                    <div className="col-3 button-col">
                        <div className="form-group">
                            <label htmlFor="search-user-input"><h5>Buscar</h5></label>
                            <input type="text"
                                className="form-control"
                                name="searchPatientInput"
                                value={this.state.searchPatientInput}
                                onChange={this.handleChange}
                                aria-describedby="searchHelpBlockPathology" />
                            <small id="searchHelpBlockPathology" className="form-text text-muted">
                                Buscará por nombre, apellido, dni y mail
                            </small>
                        </div>
                    </div>
                </div>

                <div id="admin-panel" className="row">
                    <div className="col-12">
                        <h4>Pacientes</h4>
                        <BootstrapTable
                            remote={{ pagination: true, filter: true }}
                            keyField='id'
                            data={this.state.patients}
                            columns={columns}
                            page={this.state.page}
                            sizePerPage={this.state.sizePerPage}
                            totalSize={this.state.totalSize}
                            noDataIndication="No hay información para mostrar"
                            onTableChange={this.getPatients}
                            pagination={
                                paginationFactory(paginationOptions)
                            }
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

                {/* Modal de alta de paciente */}
                <AddPatientModal
                    modalId={this.addPatientModalId}
                    action={this.state.action}
                    selectedPatient={this.state.selectedPatient}
                    getPatients={this.getPatients}
                    actionModal={this.actionModal}
                />
                
                {/* Modal de confirmacion de eliminacion de paciente */}
                <DeletePatientModal
                    modalId={this.deletePatientModalId}
                    patientId={this.state.selectedPatient.id}
                    name={this.state.selectedPatient.name}
                    getPatients={this.getPatients}
                    actionModal={this.actionModal}
                />
            </div>
        );
    }
}

export default PatientsCRUDComponent;
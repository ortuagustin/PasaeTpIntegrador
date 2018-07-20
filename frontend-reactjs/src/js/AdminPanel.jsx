import React from 'react';

// Componentes React
import AddUserModal from './AddUserModal.jsx';

// Librerias
import BootstrapTable from 'react-bootstrap-table-next'; // Tabla
import paginationFactory from 'react-bootstrap-table2-paginator'; // Paginacion en tabla
import overlayFactory from 'react-bootstrap-table2-overlay'; // Para la animacion de carga

// Estilos
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css'; // Estilos de la tabla

class AdminPanel extends React.Component {
    constructor(props) {
        super(props);

        // Variables que no renderizan la vista
        this.addUserModalId = 'add-user-modal';

        this.state = {
            users: [],
            page: 0,
            sizePerPage: 10,
            totalSize: 0
        };

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.getUsers = this.getUsers.bind(this);
    }

    componentDidMount() {
        this.getUsers();
    }

    /**
     * Obtiene los usuarios via AJAX
     */
    // getUsers() {
    //     var self = this;
    //     fetch('http://localhost:8080/admin/users/', {
    //         method: 'GET',
    //         credentials: 'same-origin'
    //     })
    //     .then((response) => {
    //         // console.log(response);
    //         if (response.status >= 400) {
    //             throw new Error("Bad response from server");
    //         }
    //         let responseAns = null;
    //         try {
    //             responseAns = response.json();
    //         } catch (ex) {
    //             console.log("Ocurrió un error al parsear la respuesta", ex);
    //         }
    //         return responseAns;
    //     })
    //     .then((usersData) => {
    //         // Si todo salio bien actualizo el estado
    //         if (usersData) {
    //             self.setState({ users: usersData });
    //         }
    //     });
    // }
    
    /**
     * Obtiene los usuarios via AJAX.
     * Para mas info acerca de los parametros: https://react-bootstrap-table.github.io/react-bootstrap-table2/docs/table-props.html#ontablechange-function
     * @param {string} type Tipo de accion realizada en la tabla 
     * @param {*} newState Atributos de la tabla actuales
     */
    getUsers(actionType = '', newState = {}) {
        let self = this;
        let pageNumber = newState.page ? newState.page - 1 : self.state.page;
        self.setState({ loading: true });
        $.ajax({
            url: 'http://localhost:8080/admin/users/',
            data: {
                newestPage: pageNumber,
                newestSizePerPage: newState.sizePerPage,
                newestSortField: newState.sortField,
                newestSortOrder: newState.sortOrder
            }
        }).done(function (jsonReponse, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                self.setState({
                    users: jsonReponse.content,
                    page: jsonReponse.number - 1,
                    sizePerPage: jsonReponse.size,
                    totalSize: jsonReponse.totalElements
                });
            } 
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR, textStatus, errorThrown);
        }).always(function () {
            self.setState({ loading: false });
        });
    }

    /**
     * Muestra/oculta un modal en particular
     * @param {string} modalId Id del modal que se quiere mostrar/esconder
     * @param {*} action Accion a ejecutar 'show' o 'hide'
     */
    accionModal(modalId, action) {
        $('#' + modalId).modal(action);
    }

    render() {
        // Preparo la configuracion de las columnas de la tabla
        const columns = [{
                dataField: 'username',
                text: 'Usuario',
                sort: true
            }, {
                dataField: 'email',
                text: 'Mail',
                sort: true
            }, {
                dataField: 'firstName',
                text: 'Nombre',
                sort: true
            }, {
                dataField: 'lastName',
                text: 'Apellido',
                sort: true
            }
        ];
            
        return(
            <div className="container">
                <div id="admin-panel" className="row">
                    <div className="col col-10">
                        <h4>Usuarios</h4>
                        <BootstrapTable
                            remote={{ pagination: true }}
                            keyField='id'
                            data={this.state.users}
                            columns={columns}
                            page={this.state.page}
                            sizePerPage={this.state.sizePerPage}
                            totalSize={this.state.totalSize}
                            noDataIndication="No hay información para mostrar"
                            onTableChange={this.getUsers}
                            pagination={ paginationFactory() }
                            loading={this.state.loading}
                            overlay={
                                overlayFactory({
                                    spinner: true,
                                    background: 'rgba(192,192,192,0.3)'
                                })
                            } // Animacion mientras carga
                        />
                    </div>
                    <div className="col">
                        <h4>Acciones</h4>
                        <button type="button" className="btn btn-success" data-toggle="modal" data-target={"#" + this.addUserModalId}>Agregar</button>
                    </div>
                </div>

                {/* Modal de alta de usuario */}
                <AddUserModal
                    modalId={this.addUserModalId}
                />
            </div>
        );
    }
}

export default AdminPanel;
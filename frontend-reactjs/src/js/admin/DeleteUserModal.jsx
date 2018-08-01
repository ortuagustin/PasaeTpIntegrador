import React from 'react';

/**
 * Renderiza el modalcon un cartel de confirmacion de eliminacion
 * @param {string} modalId Id del modal
 * @param {number} userId Id del usuario a borrar
 * @param {string} username Username del usuario a borrar
 * @param {Function} getUsers Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class DeleteUserModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.getUsers = props.getUsers;
        this.actionModal = props.actionModal;

        this.state = {
            userId: props.userId,
            username: props.username
        }

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.removeUser = this.removeUser.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.setState({
            userId: props.userId,
            username: props.username
        });
    }

    /**
     * Hace un request al server para eliminar un usuario
     */
    removeUser() {
        let self = this;
        $.ajax({
            url: 'http://localhost:8080/admin/users/' + self.state.userId,
            contentType: "application/json; charset=utf-8",
            type: 'DELETE',
        }).done(function (jsonReponse, textStatus, jqXHR) {
            self.getUsers(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al eliminar al usuario. Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    render() {
        return(
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Eliminar usuario</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="col text-center">
                                <h4>¿Está seguro que desea eliminar el usuario <strong>{this.state.username}</strong></h4>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="button" className="btn btn-primary" onClick={this.removeUser}>Eliminar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default DeleteUserModal;
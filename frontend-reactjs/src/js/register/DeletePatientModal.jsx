import React from 'react';

/**
 * Renderiza el modal con un cartel de confirmacion de eliminacion
 * @param {string} modalId Id del modal
 * @param {number} patientId Id del paciente a borrar
 * @param {string} name Nombre del paciente a borrar
 * @param {Function} getPatients Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class DeletePatientModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.getPatients = props.getPatients;
        this.actionModal = props.actionModal;

        this.state = {
            patientId: props.patientId,
            name: props.name,
        }

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.removePatient = this.removePatient.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.setState({
            patientId: props.patientId,
            name: props.name,
        });
    }

    /**
     * Hace un request al server para eliminar un paciente
     */
    removePatient() {
        let self = this;

        $.ajax({
            url: 'http://localhost:8080/patients/' + self.state.patientId,
            contentType: "application/json; charset=utf-8",
            type: 'DELETE',
        }).done(function (jsonReponse, textStatus, jqXHR) {
            self.getPatients(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al eliminar al paciente. Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    render() {
        return(
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Eliminar paciente</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="col text-center">
                                <h4>¿Está seguro que desea eliminar al paciente <strong>{this.state.name}</strong></h4>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="button" className="btn btn-primary" onClick={this.removePatient}>Eliminar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default DeletePatientModal;
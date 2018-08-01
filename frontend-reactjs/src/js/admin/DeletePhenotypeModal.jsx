import React from 'react';

/**
 * Renderiza el modalcon un cartel de confirmacion de eliminacion
 * @param {string} modalId Id del modal
 * @param {number} phenotypeId Id del fenotipo a borrar
 * @param {string} name Nombre del fenotipo a borrar
 * @param {string} phenotypeType Tipo del fenotipo a borrar ('numeric' o 'categoric')
 * @param {Function} getPhenotypes Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class DeletePhenotypeModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.getPhenotypes = props.getPhenotypes;
        this.actionModal = props.actionModal;

        this.state = {
            phenotypeId: props.phenotypeId,
            name: props.name,
            phenotypeType: props.phenotypeType
        }

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.removePhenotypes = this.removePhenotypes.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.setState({
            phenotypeId: props.phenotypeId,
            name: props.name,
            phenotypeType: props.phenotypeType
        });
    }

    /**
     * Hace un request al server para eliminar un fenotipo
     */
    removePhenotypes() {
        let self = this;

        let url = self.state.phenotypeType == 'numeric' ? 'http://localhost:8080/numeric-phenotypes/' : 'http://localhost:8080/categoric-phenotypes/';

        $.ajax({
            url: url + self.state.phenotypeId,
            contentType: "application/json; charset=utf-8",
            type: 'DELETE',
        }).done(function (jsonReponse, textStatus, jqXHR) {
            self.getPhenotypes(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al eliminar al fenotipo. Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    render() {
        return(
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Eliminar fenotipo</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="col text-center">
                                <h4>¿Está seguro que desea eliminar el fenotipo <strong>{this.state.name}</strong></h4>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                            <button type="button" className="btn btn-primary" onClick={this.removePhenotypes}>Eliminar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default DeletePhenotypeModal;
import React from 'react';

/**
 * Renderiza el modal con los campos de alta de un fenotipo
 * @param {string} modalId Id del modal
 * @param {string} action Para saber si se esta agregando o eliminando un fenotipo
 * @param {string} phenotypeType Para saber si es numerico o categorico
 * @param {*} selectedPhenotype Si action == 'edit' se utiliza para cargar los datos del fenotipo a editar
 * @param {Function} getPhenotypes Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class AddPhenotypeModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.getPhenotypes = props.getPhenotypes;
        this.actionModal = props.actionModal;
        this.action = props.action;

        this.defaultState = {
            name: '',
            values: [''],
            phenotypeType: props.phenotypeType,
            adding: false,
            selectedPhenotype: props.selectedPhenotype
        };

        this.state = this.defaultState;

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.handleChange = this.handleChange.bind(this);
        this.handleKeyPress = this.handleKeyPress.bind(this);
        this.savePhenotype = this.savePhenotype.bind(this);
        this.addValue = this.addValue.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.action = props.action;
        this.setState({ 
            phenotypeType: props.phenotypeType,
            selectedPhenotype: props.selectedPhenotype
        });
    }

    /**
     * Metodo que se ejecuta cuando los inputs cambian.
     * Sirve para refrescar el state
     * @param {Event} e Evento del cambio del input
     */
    handleChange(e) {
        // Verifico que el formulario sea valido
        if (!e.target.validity.valid) {
            return;
        }

        // Modifico
        this.setState({ [e.target.name]: e.target.value });
    }

    /**
     * Agrega un nuevo valor al arreglo de valores
     */
    addValue() {
        if (this.state.phenotypeType == 'categoric') {
            let values = this.state.values;
            values.push('');
            this.setState({ values });
        }
    }

    /**
     * Elimina el valor con el indice especificado
     * @param {number} index Index del valor a eliminar
     */
    removeValue(index) {
        if (this.state.phenotypeType == 'categoric') {
            let values = this.state.values;
            values.splice(index, 1);
            this.setState({ values });
        }
    }

    /**
     * Limpia todos los inputs del formulario
     */
    cleanInputs() {
        this.setState(this.defaultState);
    }

    /**
     * Ni bien se renderiza el componente pedimos los roles
     * al servidor
     */
    componentDidMount() {
        let self = this;
        // Cada vez que se abra el modal, obtengo los roles
        $('#' + self.modalId).on('show.bs.modal', function() {
            if (self.action == 'edit') {
                // Si estamos editando, cargo los datos del fenotipo
                // seleccionado en el formulario
                self.setState({
                    name: self.state.selectedPhenotype.name,
                    values: self.state.selectedPhenotype.values ? self.state.selectedPhenotype.values : [''],
                });
            }
        });

        // Cuando ya esta abierto el modal, hago focus en el 
        // primer input
        $('#' + self.modalId).on('shown.bs.modal', function() {
            $('#phenotype-name-input').focus();
        });

        // Cuando se esconde limpio los inputs
        $('#' + self.modalId).on('hidden.bs.modal', function() {
            self.cleanInputs();
        });
    }

    /**
     * Maneja el evento cuando se presiona una tecla en el 
     * formualario
     * @param {Event} e Evento de la presion de la tecla
     */
    handleKeyPress(e) {
        // Si presiona enter hacemos el submit
        if (e.which == 13) {
            this.savePhenotype();
        }
    }

    /**
     * Hace un request al server para agregar un fenotipo
     */
    savePhenotype() {
        let self = this;
        // Si el formulario es invalido no hago nada
        if (!self.isFormValid()) {
            return;
        }

        let url;
        let values;
       
        if (self.state.phenotypeType == 'numeric') {
            url = 'http://localhost:8080/numeric-phenotypes/';
            values = null;
        } else {
            url = 'http://localhost:8080/categoric-phenotypes/';
            values = self.state.values;
        }

        let phenotypeId = self.action == 'edit' ? self.state.selectedPhenotype.id : '';

        $.ajax({
            url: url + phenotypeId,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: self.action == 'add' ? 'PUT' : 'PATCH',
            data: JSON.stringify({
                name: self.state.name,
                values: values
            })
        }).done(function (jsonReponse, textStatus, jqXHR) {
            self.getPhenotypes(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al " + (self.action == 'add' ? 'dar de alta' : 'editar el fenotipo') + ". Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    /**
     * Edita un valor
     * @param {Event} e Evento de cambio
     * @param {*} idx Index del valor a editar
     */
    changeValue(e, idx) {
        // Verifico que el formulario sea valido
        if (!e.target.validity.valid) {
            return;
        }

        let values = this.state.values;
        values[idx] = e.target.value;
        this.setState({ values });
    }

    /**
     * Valida el formulario
     * @returns True si el formulario es valido, false caso contrario
     */
    isFormValid() {
        return !this.state.adding
                && this.state.name
                && (
                    // Para los categoricos al menos debo ingresar 2.
                    // Y para los numericos solo tiene un unico valor en null
                    this.state.phenotypeType == 'numeric'
                    || (
                        this.state.phenotypeType == 'categoric'
                        && this.state.values.length > 1
                        && this.state.values.find((value) => !value.length) === undefined // No haya valores vacios
                    )
                );
    }

    render() {
        let valuesComponent = null;

        // Lista con los valores del fenotipo
        if (this.state.phenotypeType == 'categoric') {
            let valuesList = this.state.values.map((value, idx) => {
                let deleteButton = null;
                let addButton = null;
                
                // Agrego el boton de eliminar a todos menos al primero
                if (idx != 0) {
                    deleteButton = <button className="btn btn-danger" onClick={() => this.removeValue(idx)} title="Eliminar valor">-</button>;
                }
                
                // Agrego el boton de agregar solo al lado del ultimo
                if (idx == this.state.values.length - 1) {
                    addButton = (<button className="btn btn-success"
                                    onClick={this.addValue}
                                    title="Agregar valor"
                                    disabled={!this.state.values[idx]}>+</button>);
                }
    
                return (
                    <div key={"input-value-div-" + idx} className="row margin-bottom text-center">
                        <div className="col">
                            {deleteButton}
                        </div>
                        <div className="col">
                            <input key={"input-value-" + idx}
                                id={"rol-" + idx}
                                className="form-control"
                                type="text"
                                value={value}
                                onChange={(e) => this.changeValue(e, idx)}/>
                        </div>
                        <div className="col">
                            {addButton}
                        </div>
                    </div>
                );
            });

            valuesComponent = (
                <div className="form-row">
                    <div className="col-md-12">
                        <h5>Valores</h5>
                        {valuesList}
                    </div>
                </div> 
            );
        }

        // Verifico que no este cargando y que el formulario sea valido
        let isValid = this.isFormValid();
        return(
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Agregar fenotipo</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-row">
                                <div className="col-md-12">
                                    <label htmlFor="phenotype-name-input">Nombre de fenotipo</label>
                                    <input type="text" id="phenotype-name-input" name="name" value={this.state.name} className="form-control" onChange={this.handleChange} onKeyPress={this.handleKeyPress}/>
                                </div>
                            </div>
                            
                            {valuesComponent}
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                            <button type="button" className="btn btn-primary" disabled={!isValid} onClick={this.savePhenotype}>Guardar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddPhenotypeModal;
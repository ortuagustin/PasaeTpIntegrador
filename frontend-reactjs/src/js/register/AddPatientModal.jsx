import React from 'react';

// Librerias
import Autocomplete from 'react-autocomplete';

/**
 * Renderiza el modal con los campos de alta de un paciente
 * @param {string} modalId Id del modal
 * @param {string} action Para saber si se esta agregando o eliminando un paciente
 * @param {*} selectedPatient Si action == 'edit' se utiliza para cargar los datos del paciente a editar
 * @param {Function} getPatients Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class AddPatientModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.getPatients = props.getPatients;
        this.actionModal = props.actionModal;
        this.action = props.action;

        this.selectedPathologyDefault = {
            numericPhenotypes: [],
            categoricPhenotypes: []
        }

        this.defaultState = {
            patient: {
                name: '',
                surname: '',
                dni: '',
                email: '',
                genotype: '',
                numericPhenotypesValues: [], // Arreglo de valores de fenotipos numericos elegidos
                categoricPhenotypesValues: [], // Arreglo de valores de fenotipos categoricos elegidos
            },
            selectedPathology: this.selectedPathologyDefault,
            pathologyInput: '', // Valor actual del autocomplete
            pathologiesSuggeretion: [], // Las sugerencias del autocomplete actual
            phenotypeType: 'numeric', // Tipo de paciente a buscar
            adding: false,
            selectedPatient: props.selectedPatient
        };

        this.state = this.defaultState;

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.handleChange = this.handleChange.bind(this);
        this.handleKeyPress = this.handleKeyPress.bind(this);
        this.savePatient = this.savePatient.bind(this);
        this.getPathologySuggeretion = this.getPathologySuggeretion.bind(this);
        this.cleanPathology = this.cleanPathology.bind(this);
        this.handlePatientChanges = this.handlePatientChanges.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.action = props.action;
        this.setState({
            selectedPatient: props.selectedPatient
        });
    }

    /**
     * Metodo que se ejecuta cuando los inputs referentes al paciente cambian.
     * Sirve para refrescar el state
     * @param {Event} e Evento del cambio del input
     */
    handlePatientChanges(e) {
        // Verifico que el formulario sea valido
        if (!e.target.validity.valid) {
            return;
        }
        let patient = this.state.patient;
        patient[e.target.name] = e.target.value;
        // Modifico
        this.setState({ patient });
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
     * Limpia todos los inputs del formulario
     */
    cleanInputs() {
        this.setState(this.defaultState);
    }

    /**
     * Cambia el valor de un fenotipo elegido
     * @param {Event} e Evento de cambio
     * @param {number} idx Indice del arreglo donde se encuentra el valor a editar
     * @param {string} type 'numeric' o 'categoric' para saber que estamos editando
     */
    changePhenotypeValue(e, idx, type) {
        if (type == 'numeric') {
            // Si no es valido, retorno
            if (!e.target.validity.valid) {
                return;
            }

            let numericPhenotypesValues = this.state.patient.numericPhenotypesValues;
            numericPhenotypesValues[idx].value = e.target.value;
            this.setState({ numericPhenotypesValues });
        } else {
            let categoricPhenotypesValues = this.state.patient.categoricPhenotypesValues;
            categoricPhenotypesValues[idx].values = e.target.value;
            this.setState({ categoricPhenotypesValues });
        }
    }

    /**
     * Selecciona una patologia, y hace un request para obtener sus
     * fenotipos asociados
     * @param {*} pathology Patologia seleccionada
     */
    selectPathology(pathology) {
        let self = this;
        this.setState({ pathologyInput: pathology.name }, () => {
            $.ajax({
                url: 'http://localhost:8080/pathologies/' + pathology.id,
                dataType: "json",
            }).done(function (jsonReponse, textStatus, jqXHR) {
                if (jsonReponse) {
                    let patient = self.state.patient;

                    patient.numericPhenotypesValues = jsonReponse.numericPhenotypes.map((phenotype) => {
                        return {
                            phenotypeId: phenotype.id,
                            value: ''
                        }
                    });

                    patient.categoricPhenotypesValues = jsonReponse.categoricPhenotypes.map((phenotype) => {
                        return {
                            phenotypeId: phenotype.id,
                            values: ''
                        }
                    });

                    // Selecciono la patologia, limpio el input y las sugerencias
                    self.setState({
                        selectedPathology: jsonReponse,
                        patient,
                        pathologyInput: '',
                        getPathologySuggeretion: []
                    });
                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                alert("Error al obtener los datos de la patologia. Intente nuevamente más tarde");
                console.log(jqXHR, textStatus, errorThrown);
            });
        })
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
                // Si estamos editando, cargo los datos del paciente
                // seleccionado en el formulario
                self.getPatientData();
                // console.log("Actualizando  --> ", self.state.selectedPatient);
                // let newPatientState = {
                //     name: self.state.selectedPatient.name,
                //     surname: self.state.selectedPatient.surname,
                //     dni: self.state.selectedPatient.dni,
                //     email: self.state.selectedPatient.email,
                //     numericPhenotypesValues: self.state.selectedPatient.numericPhenotypes.map((phenotype) => {
                //         return {
                //             phenotypeId: phenotype.id,
                //             value: ''
                //         }
                //     }),
                //     categoricPhenotypesValues: self.state.selectedPatient.categoricPhenotypes
                // }
                // self.setState({ patient: newPatientState });
            }
        });

        // Cuando ya esta abierto el modal, hago focus en el 
        // primer input
        $('#' + self.modalId).on('shown.bs.modal', function() {
            $('#pathology-name-input').focus();
        });

        // Cuando se esconde limpio los inputs
        $('#' + self.modalId).on('hidden.bs.modal', function() {
            self.cleanInputs();
        });
    }

    /**
     * Hace un request para obtener los datos del paciente seleccionado
     */
    getPatientData() {
        let self = this;
        
        $.ajax({
            url: 'http://localhost:8080/patients/' + self.state.selectedPatient.id,
            dataType: "json",
        }).done(function (jsonReponse, textStatus, jqXHR) {
            // Hago esto para renombrar de 'numericPhenotypes' y 'categoricPhenotypes' 
            // a 'numericPhenotypesValues' y 'categoricPhenotypesValues'
            let newPatientState = {
                name: jsonReponse.name,
                surname: jsonReponse.surname,
                dni: jsonReponse.dni,
                email: jsonReponse.email,
                numericPhenotypesValues: jsonReponse.numericPhenotypes,
                categoricPhenotypesValues: jsonReponse.categoricPhenotypes
            }
            console.log("seteando el nuevo estado de paciente ", newPatientState);
            self.setState({ patient: newPatientState });
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al obtener los datos del paciente. Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
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
            this.savePatient();
        }
    }

    /**
     * Hace un request al server para agregar un paciente
     */
    savePatient() {
        let self = this;
        // Si el formulario es invalido no hago nada
        if (!self.isFormValid()) {
            return;
        }

        let patientId = self.action == 'edit' ? self.state.selectedPatient.id : '';
        
        $.ajax({
            url: 'http://localhost:8080/patients/' + patientId,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: self.action == 'add' ? 'PUT' : 'PATCH',
            data: JSON.stringify({
                userId: 1,
                name: self.state.patient.name,
                surname: self.state.patient.surname,
                dni: self.state.patient.dni,
                email: self.state.patient.email,
                numericPhenotypes: self.state.patient.numericPhenotypesValues,
                categoricPhenotypes: self.state.patient.categoricPhenotypesValues,
                genotype: self.state.patient.genotype
            })
        }).done(function (jsonReponse, textStatus, jqXHR) {
            // En caso de exito...
            self.getPatients(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al dar de alta al paciente. Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    /**
     * Edita un valor
     * @param {Event} e Evento de cambio
     * @param {number} idx Index del valor a editar
     */
    changeValue(e, idx) {
        // Verifico que el formulario sea valido
        if (!e.target.validity.valid) {
            return;
        }

        let phenotypes = this.state.phenotypes;
        phenotypes[idx] = e.target.value;
        this.setState({ phenotypes });
    }

    /**
    * Obtiene los phenotypos
    * @param {Event} e Evento de cambio del input
    */
    getPathologySuggeretion(e) {
        let self = this;
        self.setState({ pathologyInput: e.target.value }, () => {
            // Hago el request
            $.ajax({
                url: 'http://localhost:8080/pathologies/',
                data: {
                    search: self.state.pathologyInput
                }
            }).done(function (jsonResponse) {
                // Actualizo la lista de sugerencias
                self.setState({ pathologiesSuggeretion: jsonResponse.content });
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log("Error al obtener los nombres", jqXHR, textStatus, errorThrown);
            });;
        });
    }

    /**
     * Limpia el buscador y la patologia seleccionada
     */
    cleanPathology() {
        this.setState({
            pathologyInput: '',
            selectedPathology: this.selectedPathologyDefault
        });
    }

    /**
     * Valida el formulario
     * @returns True si el formulario es valido, false caso contrario
     */
    isFormValid() {
        return !this.state.adding
                && this.state.patient.name
                && this.state.patient.surname
                && this.state.patient.dni
                && this.state.patient.email
                && this.state.patient.genotype
                && this.state.patient.numericPhenotypesValues.length
                && this.state.patient.categoricPhenotypesValues.length
                && !this.state.patient.numericPhenotypesValues.some((phenotype) => phenotype.value.trim().length == 0) // No tiene que haber ningun fenotipo sin valores asignados
                && !this.state.patient.categoricPhenotypesValues.some((phenotype) => phenotype.values.trim().length == 0); // No tiene que haber ningun fenotipo sin valores asignados
    }

    render() {
        // Lista con los fenotipos seleccionados
        let valuesComponent = null;

        let numericPhenotypesvaluesList = this.state.selectedPathology.numericPhenotypes.map((phenotype, idx) => {
            return (
                <div key={"input-value-numeric-div-" + phenotype.id} className="row margin-bottom text-center">
                    <div className="col-md-6">
                        <h5>{phenotype.name}</h5>
                    </div>
                    <div className="col-md-6">
                        <input type="text"
                            className="form-control"
                            pattern="[0-9]*"
                            value={this.state.patient.numericPhenotypesValues[idx].value}
                            onChange={(e) => this.changePhenotypeValue(e, idx, 'numeric')}/>
                    </div>
                </div>
            );
        });

        let categoricPhenotypesvaluesList = this.state.selectedPathology.categoricPhenotypes.map((phenotype, idx) => {
            let options = phenotype.values.map((phenotypeValue) => {
                return <option key={phenotypeValue} value={phenotypeValue}>{phenotypeValue}</option>;
            });

            return (
                <div key={"input-value-categoric-div-" + phenotype.id} className="row margin-bottom text-center">
                    <div className="col-md-6">
                        <h5>{phenotype.name}</h5>
                    </div>
                    <div className="col-md-6">
                        <select name={"value-" + phenotype.id}
                            className="form-control"
                            value={this.state.patient.categoricPhenotypesValues[idx].values}
                            onChange={(e) => this.changePhenotypeValue(e, idx, 'categoric')}>
                            <option value="" defaultChecked>Seleccionar</option>

                            {options}
                        </select>
                    </div>
                </div>
            );
        });

        valuesComponent = (
            <div className="form-row">
                <div className="col-md-12">
                    <h5>Fenotipos</h5>

                    <p>Numéricos</p>
                    {numericPhenotypesvaluesList}

                    <p>Categóricos</p>
                    {categoricPhenotypesvaluesList}
                </div>
            </div> 
        );

        // Verifico que no este cargando y que el formulario sea valido
        let isValid = this.isFormValid();

        return (
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Agregar paciente</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-row">
                                <div className="col-md-3">
                                    <label htmlFor="patient-name-input">Nombre</label>
                                    <input type="text" id="patient-name-input" name="name" value={this.state.patient.name} className="form-control" onChange={this.handlePatientChanges} onKeyPress={this.handleKeyPress} />
                                </div>
                                <div className="col-md-3">
                                    <label htmlFor="patient-surname-input">Apellido</label>
                                    <input type="text" id="patient-surname-input" name="surname" value={this.state.patient.surname} className="form-control" onChange={this.handlePatientChanges} onKeyPress={this.handleKeyPress} />
                                </div>
                                <div className="col-md-3">
                                    <label htmlFor="patient-dni-input">DNI</label>
                                    <input type="text" id="patient-dni-input" name="dni" value={this.state.patient.dni} pattern="[0-9]*" maxLength="8" className="form-control" onChange={this.handlePatientChanges} onKeyPress={this.handleKeyPress} />
                                </div>
                                <div className="col-md-3">
                                    <label htmlFor="patient-email-input">Mail</label>
                                    <input type="text" id="patient-email-input" name="email" value={this.state.patient.email} className="form-control" onChange={this.handlePatientChanges} onKeyPress={this.handleKeyPress} />
                                </div>
                            </div>

                            <div className="form-row">
                                <div className="col-md-12">
                                    <label htmlFor="patient-genotype-input">Genotipo</label>
                                    <textarea type="text" id="patient-genotype-input" name="genotype" value={this.state.patient.genotype} className="form-control" onChange={this.handlePatientChanges} onKeyPress={this.handleKeyPress} />
                                </div>
                            </div>

                            <div className="form-row">
                                <div className="col-md-6 div-autocomplete margin-bottom">
                                    <h5>Nombre de la patología</h5>
                                    <Autocomplete
                                        getItemValue={(pathology) => pathology.name}
                                        items={this.state.pathologiesSuggeretion}
                                        renderItem={(pathology, isSelect) =>
                                            <div key={pathology.id.toString()} className={'autocomplete-option ' + (isSelect ? 'selected' : '')}>
                                                {pathology.name}
                                            </div>
                                        }
                                        value={this.state.pathologyInput}
                                        onChange={this.getPathologySuggeretion}
                                        onSelect={(value, pathology) => { this.selectPathology(pathology) }}
                                    />
                                </div>
                                <div className="col-md-6">
                                    <h5>Limpiar patología</h5>
                                    <button className="btn btn-danger" onClick={this.cleanPathology} disabled={!this.state.pathologyInput}>Limpiar</button>
                                </div>
                            </div>

                            {/* Listado con los valores para cada fenotipo de la patologia */}
                            <div className="row">
                                <div className="col-md-12">
                                    {valuesComponent}
                                </div>
                            </div>

                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                            <button type="button" className="btn btn-primary" disabled={!isValid} onClick={this.savePatient}>Guardar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddPatientModal;
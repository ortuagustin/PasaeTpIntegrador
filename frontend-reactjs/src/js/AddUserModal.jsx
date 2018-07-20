import React from 'react';

/**
 * Renderiza el modal con los campos de alta de un usuario
 * @param {string} modalId Id del modal
 * @param {string} action Para saber si se esta agregando o eliminando un usuario
 * @param {*} selectedUser Si action == 'edit' se utiliza para cargar los datos del usuario a editar
 * @param {Function} getUsers Funcion para refrescar la tabla
 * @param {Function} actionModal Funcion para manejar los modals
 */
class AddUserModal extends React.Component {
    constructor(props) {
        super(props);

        this.modalId = props.modalId;
        this.action = props.action;
        this.selectedUser = props.selectedUser;
        this.getUsers = props.getUsers;
        this.actionModal = props.actionModal;

        this.defaultState = {
            username: '',
            password: '',
            email: '',
            firstname: '',
            lastname: '',
            selectedRole: '',
            roles: [],
            adding: false
        };

        this.state = this.defaultState;

        // Bindeo la variable 'this' a los metodos llamados desde la vista
        this.handleChange = this.handleChange.bind(this);
        this.saveUser = this.saveUser.bind(this);
    }

    /**
     * Metodo que se ejecuta cuando cambian los props del 
     * componente
     * @param {*} props Nuevas props
     */
    componentWillReceiveProps(props) {
        this.action = props.action;
        this.selectedUser = props.selectedUser;
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
     * Ni bien se renderiza el componente pedimos los roles
     * al servidor
     */
    componentDidMount() {
        let self = this;
        // Cada vez que se abra el modal, obtengo los roles
        $('#' + self.modalId).on('show.bs.modal', function() {
            self.getRoles();
            if (self.action == 'edit') {
                // Si estamos editando, cargo los datos del usuario
                // seleccionado en el formulario
                console.log(self.selectedUser);
                self.setState({
                    username: self.selectedUser.username,
                    password: self.selectedUser.password,
                    email: self.selectedUser.email,
                    firstname: self.selectedUser.firstName,
                    lastname: self.selectedUser.lastName,
                    selectedRole: self.selectedUser.authorities[0].name,
                });
            }
        });

        // Cuando ya esta abierto el modal, hago focus en el 
        // primer input
        $('#' + self.modalId).on('shown.bs.modal', function() {
            $('#username-input').focus();
        });

        // Cuando se esconde limpio los inputs
        $('#' + self.modalId).on('hidden.bs.modal', function() {
            self.cleanInputs();
        });
    }

    /**
     * Hace un request al server para agregar un usuario
     */
    saveUser() {
        let self = this;
        let idUser = self.action == 'edit' ? self.selectedUser.id : ''; // Id para editar el usuario 
        $.ajax({
            url: 'http://localhost:8080/admin/users/' + idUser,
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: self.action == 'add' ? 'PUT' : 'PATCH',
            data: JSON.stringify({
                username: self.state.username,
                password: self.state.password,
                email: self.state.email,
                firstName: self.state.firstname,
                lastName: self.state.lastname,
                authorities: [ self.state.selectedRole ]
            })
        }).done(function (jsonReponse, textStatus, jqXHR) {
            self.getUsers(); // Refresco la tabla
            self.actionModal(self.modalId, 'hide'); // Escondo el modal
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al " + (self.action == 'add' ? 'dar de alta' : 'editar el usuario') + ". Intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    /**
    * Obtiene via AJAX los roles disponibles para el
    * alta de un usuario
    */
    getRoles() {
        let self = this;
        $.ajax({
            url: 'http://localhost:8080/admin/roles/',
        }).done(function (jsonReponse, textStatus, jqXHR) {
            if (jqXHR.status == 200) {
                self.setState({ roles: jsonReponse });
            }
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Error al obtener los roles. Cierre e intente nuevamente más tarde");
            console.log(jqXHR, textStatus, errorThrown);
        });
    }

    render() {
        // Armo una lista con las opciones
        let rolesList = this.state.roles.map((rol) => {
            return <option key={rol.id.toString()} value={rol.name}>{rol.name}</option>;
        });

        // Verifico que no este cargando y que el formulario sea valido
        let isValid = !this.state.adding 
                        && this.state.username
                        && this.state.password
                        && this.state.firstname
                        && this.state.lastname
                        && this.state.selectedRole;
        return(
            <div className="modal fade" id={this.modalId} tabIndex="-1" role="dialog" aria-labelledby={this.modalId} aria-hidden="true">
                <div className="modal-dialog" role="document">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title">Agregar usuario</h5>
                            <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div className="modal-body">
                            <div className="form-row">
                                <div className="col">
                                    <label htmlFor="username-input">Nombre de usuario</label>
                                    <input type="text" id="username-input" name="username" value={this.state.username} className="form-control" onChange={this.handleChange} />
                                </div>
                                <div className="col">
                                    <label htmlFor="password-input">Contraseña</label>
                                    <input type="password" id="password-input" name="password" value={this.state.password} className="form-control" onChange={this.handleChange} />
                                </div>
                            </div> 
                            <div className="form-row">
                                <div className="col">
                                    <label htmlFor="firstname-input">Nombre</label>
                                    <input type="text" id="firstname-input" name="firstname" value={this.state.firstname} className="form-control"onChange={this.handleChange} />
                                </div>
                                <div className="col">
                                    <label htmlFor="lastname-input">Apellido</label>
                                    <input type="text" id="lastname-input" name="lastname" value={this.state.lastname} className="form-control" onChange={this.handleChange} />
                                </div>
                            </div> 
                            <div className="form-row">
                                <div className="col">
                                    <label htmlFor="inputState">Rol</label>
                                    <select 
                                        id="inputState"
                                        name="selectedRole"
                                        className="form-control"
                                        disabled={this.state.roles.length == 0} value={this.state.selectedRole} onChange={this.handleChange}>
                                        <option value="">{this.state.roles.length ? 'Elegir' : 'Cargando'}</option>
                                        {rolesList}
                                    </select>
                                </div>
                            </div> 
                            
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                            <button type="button" className="btn btn-primary" disabled={!isValid} onClick={this.saveUser}>Guardar</button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddUserModal;
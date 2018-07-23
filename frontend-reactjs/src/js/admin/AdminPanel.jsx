import React from 'react';

// Componentes React
import UserCRUDComponent from './UserCRUDComponent.jsx';
import PhenotypesCRUDComponent from './PhenotypesCRUDComponent.jsx';

// Estilos
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css'; // Estilos de la tabla

class AdminPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            fenotypes: [],
            option: 'users'
        };
    }

    render() {
        let crudComponent;
        if (this.state.option == 'phenotypes') {
            crudComponent = <PhenotypesCRUDComponent/>;
        } else {
            crudComponent = <UserCRUDComponent/>;
        }

        return(
            <div className="container-fluid panel-component">
                <div className="row margin-bottom">
                    <div className="col-12">
                        <ul className="nav nav-pills">
                            <li className="nav-item">
                                <a className={"nav-link " + (this.state.option == "users" ? 'active' : '')} onClick={() => this.setState({ option: 'users' })} href="#">Usuarios</a>
                            </li>
                            <li className="nav-item">
                                <a className={"nav-link " + (this.state.option == "phenotypes" ? 'active' : '')} onClick={() => this.setState({ option: 'phenotypes' })} href="#">Fenotipos</a>
                            </li>
                            <li className="nav-item">
                                <a className={"nav-link " + (this.state.option == "genotypes" ? 'active' : '')} onClick={() => this.setState({ option: 'genotypes' })} href="#">Patologías</a>
                            </li>
                        </ul>
                    </div>
                </div>

                {/* Tabla dependiendo de la opcion seleccionada */}
                {crudComponent}
            </div>
        );
    }
}

export default AdminPanel;
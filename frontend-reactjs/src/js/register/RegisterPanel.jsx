import React from 'react';

// Componentes React
import { PatientsCRUDComponent } from './PatientsCRUDComponent.jsx';

// Estilos
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css'; // Estilos de la tabla

class RegisterPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            option: 'users'
        };
    }

    render() {
        return(
            <div className="container-fluid panel-component">
                <PatientsCRUDComponent/>
            </div>
        );
    }
}

export default AdminPanel;
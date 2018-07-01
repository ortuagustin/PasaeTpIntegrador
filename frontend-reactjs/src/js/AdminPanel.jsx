
import React from 'react';

class AdminPanel extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        };
    }

    componentDidMount() {
        var self = this;
        fetch('http://localhost:8080/admin/user-list', {
            method: 'GET'
        })
        .then((response) => {
            // console.log(response);
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            let responseAns = null;
            try {
                responseAns = response.json();
            } catch (ex) {
                console.log("Ocurrió un error al parsear la respuesta", ex);
            }
            return responseAns;
        })
        .then((usersData) => {
            // Si todo salio bien actualizo el estado
            if (usersData) {
                self.setState({ users: usersData });
            }
        });
    }

    render() {
        return(
            <div>
                <h1>Componente del panel de admin</h1>
                {this.users}    
            </div>
        );
    }
}

export default AdminPanel;
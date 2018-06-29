'use strict';
const React = require('react');
const ReactDOM = require('react-dom');
import './style.css';

class App extends React.Component {
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
			return response.json();
		})
		.then((usersData) => {
			self.setState({ users: usersData });
		})
	}

	render() {
		if (this.state.users.length > 0) {
			let usersList = this.state.users.map(function (user) {
				return <li key={user.id.toString()}>{user.id} - {user.username}</li>
			});

			return (
				<ul>
					{usersList}
				</ul>
			);
		}
		return (
			<span>Cargando...</span>
		);
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)


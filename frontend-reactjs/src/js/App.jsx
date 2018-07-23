// Librerias
import React from 'react';
import ReactDOM from 'react-dom';
import {
	BrowserRouter as Router,
	Route,
	Link
} from 'react-router-dom'
import 'bootstrap'; // Para los modals


// Componentes
import Home from './Home.jsx';
import AdminPanel from './admin/AdminPanel.jsx';
import ProfessionalPanel from './professional/ProfessionalPanel.jsx';

// Estilos
import '../css/style.css'; // Estilos propios'
import '../css/bootstrap.min.css'; // Bootstrap

/**
 * Componente principal
 */
class App extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<Router>
				<div>
					<nav className="navbar navbar-expand-lg navbar-light bg-light">
						<a className="navbar-brand" href="#">PASAE</a>
						<button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
							<span className="navbar-toggler-icon"></span>
						</button>

						<div className="collapse navbar-collapse" id="navbarSupportedContent">
							<ul className="navbar-nav mr-auto">
								<li className="nav-item active">
									<span className="nav-link">
										<div>
											<Link to="/home">Home</Link>
										</div>
									</span>
								</li>
								<li className="nav-item active">
									<span className="nav-link">
										<div>
											<Link to="/">Admin</Link>
										</div>
									</span>
								</li>
								<li className="nav-item active">
									<span className="nav-link">
										<div>
											<Link to="/app/professional">Profesional</Link>
										</div>
									</span>
								</li>
							</ul>
						
							{/* Boton de logout */}
							<a href="logout">
								<button className="btn btn-outline-success my-2 my-sm-0">Salir</button>
							</a>
						</div>
					</nav>

					{/* Renderiza los componentes seleccionados */}
					<div>
						<Route exact path="/home" component={Home} />
						<Route exact path="/" component={AdminPanel} />
						<Route exact path="/app/professional" component={ProfessionalPanel} />
					</div>
				</div>
			</Router>
		);
	}	
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)


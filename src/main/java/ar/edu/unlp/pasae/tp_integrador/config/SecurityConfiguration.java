package ar.edu.unlp.pasae.tp_integrador.config;

// TODO segurizar servicios: Patologia, Fenotipo, Usuarios
// - Solamente puede acceder a este servicio usuarios con rol ADMIN
// TODO segurizar servicio: Pacientes
// - Solamente puede acceder a este servicio usuarios con rol REGISTER
// - Update y Delete solamente puede hacerlo el usuario asociado al paciente (el que lo ingreso al sistema)
// TODO segurizar servicio: Analisis de datos
// - Solamente puede acceder a este servicio usuarios con rol SCIENTIST
// TODO segurizar servicio: Gestion de asociaciones genotipo-fenotivo en estado publicado
// - Solamente puede acceder a este servicio usuarios con rol CLINICAL_DOCTOR
// TODO segurizar servicio: Visualizacion de graficos de fenotipo/genotipo
// - Solamente puede acceder a este servicio usuarios con rol EPIDEMIOLOGIST

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	AppAuthenticationProvider authProvider;

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf().disable() // Necesario porque sino ignora los request que no son GET (ncesita el CRSF token)
		.authorizeRequests()
//		.antMatchers("/**").authenticated()
		.antMatchers("/scientist/**").hasAuthority(RoleName.SCIENTIST.toString())
//		.antMatchers("/admin/**").hasAuthority(RoleName.ADMIN.toString())
		.antMatchers("/admin/**").permitAll()
		.antMatchers("/clinical-doctor/**").permitAll()//hasAuthority(RoleName.CLINICAL_DOCTOR.toString())
		.antMatchers("/epidemiologist/**").permitAll()//.hasAuthority(RoleName.EPIDEMIOLOGIST.toString())
		.antMatchers("/css/**", "/resources/**").permitAll()
		//.antMatchers("/secured-admin/**").hasAnyAuthority(RoleName.ADMIN.toString(), RoleName.SCIENTIST.toString()) // Multiples roles
		// Dejo las siguientes dos linea como ejemplo de la funcion "access()"
		//.antMatchers("/admin/**").access("hasRole('ADMIN')")
		//.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		.and().formLogin()
		.and().exceptionHandling().accessDeniedPage("/Access_Denied");

	}
}

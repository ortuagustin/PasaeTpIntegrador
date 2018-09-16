package ar.edu.unlp.pasae.tp_integrador.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf().disable() // Necesario porque sino ignora los request que no son GET (ncesita el CRSF token)
		.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/patients/**").hasAuthority(RoleName.ADMIN.toString())
			.antMatchers("/scientist/**").hasAuthority(RoleName.SCIENTIST.toString())
			.antMatchers("/admin/**").hasAuthority(RoleName.ADMIN.toString())
			.antMatchers("/clinical-doctor/**").permitAll()//hasAuthority(RoleName.CLINICAL_DOCTOR.toString())
			.antMatchers("/css/**", "/resources/**").permitAll()
			.antMatchers("/**").authenticated()
			//.antMatchers("/secured-admin/**").hasAnyAuthority(RoleName.ADMIN.toString(), RoleName.SCIENTIST.toString()) // Multiples roles
			// Dejo las siguientes dos linea como ejemplo de la funcion "access()"
			//.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		.and()
			.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager(), this.userDetailsService))
			// this disables session creation on Spring Security
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.exceptionHandling().accessDeniedPage("/Access_Denied")
		.and()
			.logout()
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.logoutSuccessUrl("http://localhost:8081")
				.deleteCookies(JwtConfig.COOKIE_NAME);

		// Para poder utilizar la consola H2 con Spring Security
		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder);
	}
}

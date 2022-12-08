package fi.vaatteet.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import fi.vaatteet.demo.filter.AuthenticationFilter;
import fi.vaatteet.demo.web.UserDetailServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity // enables Spring Security web security support
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable() // Post metods did not work before this
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.GET,"/", "/h2-console/**", "/api/**", "/css/**", "/images/**").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
//				.anyRequest().authenticated().and().csrf().ignoringAntMatchers("/h2-console/**").and().headers()
//				.frameOptions().sameOrigin().and().formLogin().defaultSuccessUrl("/products", true).permitAll().and()
//				.logout()
//				// .logoutSuccessUrl("/")
//				.permitAll()
				.and().httpBasic().and()
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}

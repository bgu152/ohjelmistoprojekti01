package fi.vaatteet.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import fi.vaatteet.demo.web.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity //enables Spring Security web security support
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {
	  @Autowired
	    private UserDetailServiceImpl userDetailsService;	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable() //Post metods did not work before this
			.authorizeRequests()
				.antMatchers("/",
						"/h2-console/**",
						"/api/**",
						"/css/**",
						"/images/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().ignoringAntMatchers("/h2-console/**")
				.and()
				.headers().frameOptions().sameOrigin()
				.and()
			.formLogin()
				.defaultSuccessUrl("/products", true)
				.permitAll()
				.and()
			.logout()
				//.logoutSuccessUrl("/")
				.permitAll()
				.and()
			.httpBasic();
			return http.build();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}

package example.config;

import example.model.*;
import example.config.MySimpleUrlAuthenticationSuccessHandler;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;


    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  		http
  			.authorizeRequests()
        .antMatchers("/user").access("hasRole('USER') or hasRole('SUPER')")
        .antMatchers("/admin").access("hasRole('ADMIN') or hasRole('SUPER')")
        .antMatchers("/super").access("hasRole('SUPER')")
  			.antMatchers("/login", "/register", "/reset")
          .permitAll()
  				.anyRequest().authenticated()
  				.and()
  			.formLogin()
  				.loginPage("/login")
          .loginProcessingUrl("/login")
          .successHandler(myAuthenticationSuccessHandler())
  				.permitAll();

    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
  	public PasswordEncoder passwordEncoder()
  	{
  		return new org.springframework.security.crypto.scrypt.SCryptPasswordEncoder();
  	}

    @Bean
  	@Override
  	public UserDetailsService userDetailsService() {

      final String psw = passwordEncoder().encode("pass");

      List<example.model.User> userDetailsList = new ArrayList<>();

      final example.model.Challenge c = new example.model.Challenge("0", "1900");

      userDetailsList.add(new example.model.User("admin", psw, createAuthorities("ADMIN"), c));
      userDetailsList.add(new example.model.User("user", psw, createAuthorities("USER"), c));
      userDetailsList.add(new example.model.User("super", psw, createAuthorities("SUPER"), c));

      InMemoryManager details = new InMemoryManager();

      details.addUserList(userDetailsList);
      return details;
  	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private ArrayList<GrantedAuthority> createAuthorities(String role)
    {
      ArrayList<GrantedAuthority> grantedAuthoritiesList= new ArrayList<>();
      grantedAuthoritiesList.add(new SimpleGrantedAuthority("ROLE_" + role));
      return grantedAuthoritiesList;
    }

}

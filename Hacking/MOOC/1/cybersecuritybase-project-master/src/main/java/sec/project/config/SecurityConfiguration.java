package sec.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private SignupRepository signupRepository;
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String usernameParameter= "username";
        String passwordParameter= "password";

        // ================================================ REMOVE disable() to block brute force
        http.csrf().disable();
        
        http
            
            .authorizeRequests()
                .antMatchers("/", "/form").permitAll()
                .anyRequest().authenticated()
                .and()            
            .formLogin()
                .loginPage("/login")                 
                .permitAll()
            .successHandler(myAuthenticationSuccessHandler());
            
        
//        http
//            .logout()      
//            .logoutUrl("/")                                                 
//            .invalidateHttpSession(true)                                            
//            .and();
    }
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
    
    
    //Editer ici pour ajouter les user + ajouter les nouveaux à la volée.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //=========================================================        Security Misconfiguration: remove this automatic confifuration line, or think about changing admin pass later
        auth.inMemoryAuthentication()
            .withUser("admin").password("admin").roles("ADMIN");
        
        for (Signup signups : signupRepository.findAll()){
            auth.inMemoryAuthentication()
            .withUser(signups.getName()).password(signups.getPassword()).roles(signups.getsRole());
        }
        
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    

                //    
                //    The line loginPage("/login") instructs Spring Security
                //
                //when authentication is required, redirect the browser to /login
                //
                //we are in charge of rendering the login page when /login is requested
                //
                //when authentication attempt fails, redirect the browser to /login?error (since we have not specified otherwise)
                //
                //we are in charge of rendering a failure page when /login?error is requested
                //
                //when we successfully logout, redirect the browser to /login?logout (since we have not specified otherwise)
                //
                //we are in charge of rendering a logout confirmation page when /login?logout is requested

                // ...
    
    
}

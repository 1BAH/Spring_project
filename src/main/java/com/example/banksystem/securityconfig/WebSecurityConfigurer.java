package com.example.banksystem.securityconfig;

import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Spring Security configuration
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Set restrictions to pages
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/registration", "/registration/form", "/authors", "/faq", "/start",
                        "/test", "/try").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.addFilterAfter(new Filter(), BasicAuthenticationFilter.class);
    }

    /**
     * Add users from database and crease two basic users: "user"-"pass" and "root'-"root"
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("pass"))
                .authorities("ROLE_ADMIN")
                .and()
                .withUser("root")
                .password("root")
                .authorities("ROLE_USER");


        for (Client client: clientRepository.findAll()) {
            auth.inMemoryAuthentication()
                    .withUser(client.getPassport())
                    .password(passwordEncoder().encode(client.getSurname()))
                    .authorities("ROLE_USER");
        }
    }

    /**
     * Encoding for passwords
     * @return Encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

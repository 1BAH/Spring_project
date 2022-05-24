package com.example.banksystem.securityconfig;

import com.example.banksystem.models.Bank;
import com.example.banksystem.models.BankOfficer;
import com.example.banksystem.models.Client;
import com.example.banksystem.repositories.BankOfficerRepository;
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

    @Autowired
    private BankOfficerRepository bankOfficerRepository;

    /**
     * Set restrictions to pages
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/registration", "/registration/form", "/bo/registration", "/bo/registration/form", "/authors", "/faq", "/start").permitAll()
                .antMatchers("/adm/**").hasRole("ADMIN")
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
     * Add users from database and create admin "root"-"root"
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(passwordEncoder().encode("root"))
                .authorities("ROLE_ADMIN");


        for (Client client: clientRepository.findAll()) {
            auth.inMemoryAuthentication()
                    .withUser(client.getPassport())
                    .password(passwordEncoder().encode(client.getSurname()))
                    .authorities("ROLE_USER");
        }

        for (BankOfficer bankOfficer: bankOfficerRepository.findAll()) {
            auth.inMemoryAuthentication()
                    .withUser(bankOfficer.getUsername())
                    .password(passwordEncoder().encode(bankOfficer.getPassword()))
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

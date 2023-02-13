package com.siit.hospital_manager.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers("/", "/public"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/", "/public", "/api-docs/**", "/swagger-ui/**", "/actuator/**", "/error/**", "/favicon.ico", "/mvc/patient/create", "/mvc/patient/submitCreatePatientForm").permitAll()
                .requestMatchers("/**", "/error/**").hasRole("ADMIN")
                        "/specialisation/viewAll", "/mvc/doctor/viewDoctorsBySpecialisationForPatient/{specialisation}", "/specialisation/viewAllForPatient", "/entityExistsError.html",
                        "/mvc/doctor/viewDoctorsBySpecialisation/**", "/mvc/doctor/viewDoctorProfile/**", "/appointment/create/**", "/appointment/submit/**", "/appointment/**").hasAnyRole("PATIENT", "DOCTOR", "ADMIN")
                .requestMatchers("/**").hasRole("ADMIN")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .formLogin()
                .and()
                .csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
package upt.albaproj.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import upt.albaproj.entities.User;
import upt.albaproj.repos.UserRepository;

import java.util.ArrayList;


@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/login", "/register", "/h2-console/**", "/css/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict admin endpoints
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/profile", true)
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .csrf(csrf -> csrf
                        .disable()
                )
                .headers(headers -> headers
                        .frameOptions().disable()
                )
                .rememberMe(rememberMe ->
                        rememberMe
                                .userDetailsService(userDetailsService())
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByEmailOrPhoneNumber(username, username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            if (user.isAdmin()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        };
    }
}

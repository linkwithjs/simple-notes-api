package com.linkwithjs.simplenotesapi.config;

import com.linkwithjs.simplenotesapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   @Autowired
    private UserService userService;
   @Autowired
    private JWTAuthFilter jwtAuthFilter;

   private final LogoutHandler logoutHandler;

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(request->request.requestMatchers("/auth/**","/public/**").permitAll()
                       .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                       .requestMatchers("/user/**").hasAnyAuthority("USER")
                       .requestMatchers("/adminuser/**").hasAnyAuthority("ADMIN","USER")
                       .requestMatchers("/auth/logout").hasAnyAuthority("ADMIN","USER")
                       .anyRequest().authenticated())
               .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
               .logout(manager->manager
                       .logoutUrl("/auth/logout")
                       .addLogoutHandler(logoutHandler)
                       .logoutSuccessHandler(
                               (request, response, authentication) ->
                                       SecurityContextHolder.clearContext()
                       )
                       .invalidateHttpSession(true));
   return httpSecurity.build();
   }

   @Bean
    public AuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       daoAuthenticationProvider.setUserDetailsService(userService);
       daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthenticationProvider;
   }

   @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
   }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration ) {
       try {
           return authenticationConfiguration.getAuthenticationManager();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }
}


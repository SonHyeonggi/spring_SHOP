package project.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SimplePasswordEncoder();
    }


    @Bean
    public SecurityFilterChain FilterChain(final HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auto -> auto
                .requestMatchers("/member/login", "/member/join", "/join_proc", "login_proc", "/main/home", "/main/home", "/css/**", "/image/**").permitAll()
                .requestMatchers("/member/modify", "/modify_proc", "/member/delete", "/item/delete", "/item/modify").hasRole("admin")
                .requestMatchers("/member/list", "/member/view", "/member/logout").hasAnyRole("admin", "member")
                .anyRequest().authenticated()
        );

        http.formLogin(Login -> Login
                .loginPage("/member/login")
                .defaultSuccessUrl("/member/list", true)
                .failureUrl("/member/login")
                .loginProcessingUrl("/login_proc")
                .usernameParameter("userid")
                .passwordParameter("pwd")
                .permitAll()
        );

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable);

        http.logout(Customizer.withDefaults());

        return http.build();
    }
}

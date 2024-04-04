/*
package project.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userid = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails user = userDetailsService.loadUserByUsername(userid);

        UsernamePasswordAuthenticationToken authenticationToken = null;

        if(!userid.equals(user.getUsername())) {
            authenticationToken = new UsernamePasswordAuthenticationToken(null, null);

            return authenticationToken;
        } else {
            authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());

            return authenticationToken;
        }

    }
}
*/

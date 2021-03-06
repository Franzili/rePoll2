package gpse.repoll.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter is a filter bean used to grant users authorization to access secured endpoints.
 *
 * I.e. this bean has to be used to get an authorization token *before* accessing secured endpoints.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SecurityConstants securityConstants;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   final SecurityConstants securityConstants) {
        this.authenticationManager = authenticationManager;
        this.securityConstants = securityConstants;

        setFilterProcessesUrl(this.securityConstants.getAuthLoginUrl());
    }

    /**
     * Attempts to authenticate a {@link gpse.repoll.domain.poll.User}.
     * @param request The request the user sent
     * @param response The response to send to the user
     * @return An {@link Authentication}
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * On successful authentication generates a JWT token.
     * @param request The {@link HttpServletRequest}
     * @param response The {@link HttpServletResponse}
     * @param filterChain The {@link FilterChain}
     * @param authentication The {@link Authentication}
     */
    @SuppressWarnings("checkstyle:MagicNumber") // Todo: move expiration to SecurityConstants
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        List<String> roles = user.getAuthorities()
                                 .stream()
                                 .map(GrantedAuthority::getAuthority)
                                 .collect(Collectors.toList());

        byte[] signingKey = securityConstants.getJwtSecret().getBytes();

        String token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", securityConstants.getTokenType())
            .setIssuer(securityConstants.getTokenIssuer())
            .setAudience(securityConstants.getTokenAudience())
            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 864000000)) // + 10 Tage
            .claim("rol", roles)
            .compact();

        response.addHeader(securityConstants.getTokenHeader(), securityConstants.getTokenPrefix() + " " + token);
    }
}

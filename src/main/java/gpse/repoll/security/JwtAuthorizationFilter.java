package gpse.repoll.security;

import gpse.repoll.domain.poll.User;
import gpse.repoll.domain.exceptions.UnauthorizedException;
import gpse.repoll.domain.repositories.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtAuthorizationFilter is a OncePerRequestFilter that checks if the user has the authorization to make
 * a request.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final  SecurityConstants securityConstants;

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  final SecurityConstants securityConstants,
                                  UserRepository userRepository) {
        super(authenticationManager);
        this.securityConstants = securityConstants;
        this.userRepository = userRepository;
    }

    /**
     * Check the request for authorization.
     * @param request The Request.
     * @param response The Response.
     * @param filterChain The FilterChain this Filter is a part of.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * Parse the JWT token and grant authorization.
     * @param request The request containing the JWT token
     * @return The parsed Authorization
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityConstants.getTokenHeader());
        if (token != null && !token.equals("") && token.startsWith(securityConstants.getTokenPrefix())) {
            try {
                byte[] signingKey = securityConstants.getJwtSecret().getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

                String username = parsedToken.getBody().getSubject();

                // Todo get authorities from user object instead of the token
                List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
                    .get("rol")).stream()
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toList());

                if (username != null && !username.equals("")) {
                    User user = userRepository.findByUsername(username).orElseThrow(UnauthorizedException::new);
                    return new UsernamePasswordAuthenticationToken(user, null, authorities);
                }
            } catch (ExpiredJwtException exception) {
                LOG.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                LOG.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                LOG.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                LOG.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                LOG.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }
        }

        return null;
    }
}

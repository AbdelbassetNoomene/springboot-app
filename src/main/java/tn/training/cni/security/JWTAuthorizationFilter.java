package tn.training.cni.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            String jwtToken = request.getHeader("Authorization");
            System.out.println("Token=" + jwtToken);
            if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("cni-tunisie")).build();
            String jwt = jwtToken.substring(7);
            DecodedJWT decodedJWT = verifier.verify(jwt);
            System.out.println("JWT=" + jwt);
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
            System.out.println("username=" + username);
            System.out.println("roles=" + roles);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(rn -> {
                authorities.add(new SimpleGrantedAuthority(rn));
            });
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            addUpdatedAuthTokenToResponse(username, response,roles);
            filterChain.doFilter(request, response);
        }
    }

    private void addUpdatedAuthTokenToResponse(String jsonUserDetails, HttpServletResponse response, List<String> roles) {
        String jwt= JWT.create()
            .withIssuer("/auth")
            .withSubject(jsonUserDetails)
            .withArrayClaim("roles",roles.toArray(new String[roles.size()]))
            .withExpiresAt(new Date(System.currentTimeMillis()+604800))
            .sign(Algorithm.HMAC256("cni-tunisie"));
        response.addHeader("Authorization",jwt);
    }
}

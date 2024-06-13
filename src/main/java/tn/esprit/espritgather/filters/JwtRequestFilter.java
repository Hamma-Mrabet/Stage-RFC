package tn.esprit.espritgather.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.espritgather.service.LoginServiceImpl;
import tn.esprit.espritgather.utils.JWTUtil;

import java.io.IOException;

@Component
//@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private  final LoginServiceImpl loginService;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil ;
    @Autowired

    public JwtRequestFilter(LoginServiceImpl loginService, UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.loginService = loginService;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain)
                                   throws ServletException, IOException {
        String authHeader = request.getHeader(" Authorization ");
        String token = null  ;
        String username = null ;

        if (authHeader != null && authHeader.startsWith("Bearer ") ){
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }
       /* if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
            return;
        }
        token = authHeader.substring(7);
        username = jwtUtil.extractUsername(token);
        */


        if (username != null && SecurityContextHolder.getContext().getAuthentication()== null){

            UserDetails userDetails = loginService.loadUserByUsername(username);
           // UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);


            if (jwtUtil.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }


        }

        System.out.println(request);
        System.out.println(response);
       filterChain.doFilter(request,response);



    }
}

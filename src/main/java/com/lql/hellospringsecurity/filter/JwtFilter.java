package com.lql.hellospringsecurity.filter;

import com.lql.hellospringsecurity.auth.CustomUser;
import com.lql.hellospringsecurity.repository.UserRepository;
import com.lql.hellospringsecurity.service.JwtService;
import com.lql.hellospringsecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        String token = "";
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        Optional<Cookie>  = Arrays.stream(cookies)
//                .filter(cookie -> cookie.getName().equals("token"))
//                .findFirst();
//        if (.isPresent()) {
//            token = .get().getValue();
//        } else {
//            filterChain.doFilter(request, response);
//            return;
//        }

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Cookie[] cookies = request.getCookies();
//        if (cookies == null) {
//            response.addCookie(new Cookie("token", jwtService.generateToken(userDetails)));
//            filterChain.doFilter(request, response);
//            return;
//        }
//        Cookie cookie1 = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token"))
//                .findFirst().orElse(null);
//        String token = cookie1.getValue();
//
//        if (jwtService.isTokenValid(token, userDetails)){
////            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////                    userDetails, null, userDetails.getAuthorities()
////            );
////            authenticationToken.setDetails(
////                    new WebAuthenticationDetailsSource().buildDetails(request)
////            );
////            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        throw new MyUsernameNotFoundException();

//        String token = "";
//        Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("token")).findFirst();
//        if (tokenCookie.isPresent()) {
//            token = tokenCookie.get().getValue();
//        } else {
//            filterChain.doFilter(request, response);
//            return;
//        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);

        UserDetails user = userService.loadUserByUsername(jwtService.extractUserName(token));
//        CustomUser user = userRepository.getReferenceById(jwtService.extractUserId(token));

        if(!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }
}

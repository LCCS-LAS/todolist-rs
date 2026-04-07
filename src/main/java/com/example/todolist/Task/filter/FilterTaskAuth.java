package com.example.todolist.Task.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todolist.User.repository.UserRepostirory;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private final UserRepostirory userRepostirory;

    public FilterTaskAuth(UserRepostirory userRepostirory) {
        this.userRepostirory = userRepostirory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (servletPath.startsWith("/tasks")) {
            var authorization = request.getHeader("Authorization");

            var user_authentic = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(user_authentic);

            var authString = new String(authDecode);
            String[] credential = authString.split(":");
            String username = credential[0];
            String password = credential[1];

            var user = userRepostirory.findByUsername(username);
            if (user == null) {
                response.sendError(401);
            } else {
                var password_verifier = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (password_verifier.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }
}

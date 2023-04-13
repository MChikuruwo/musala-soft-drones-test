package com.musala.musalatest.handler;

import com.google.gson.Gson;
import com.musala.musalatest.Exceptions.InvalidTokenException;
import com.musala.musalatest.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//
//        } catch (InvalidTokenException e) {
//            setErrorResponse(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage(), httpServletResponse);
//            e.printStackTrace();
//
//        } catch (RuntimeException e) {
//            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), httpServletResponse);
//
//        }
//
//    }

    private void setErrorResponse(HttpStatus status, String message, HttpServletResponse response) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ApiResponse apiResponse = new ApiResponse(status.value(), message);
        Gson gson = new Gson();
        String json = gson.toJson(apiResponse);
        System.out.println(json);
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        try {
            filterChain.doFilter(request, response);

        } catch (InvalidTokenException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage(), (HttpServletResponse) response);
            e.printStackTrace();

        } catch (RuntimeException e) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage(), (HttpServletResponse) response);

        }

    }
}


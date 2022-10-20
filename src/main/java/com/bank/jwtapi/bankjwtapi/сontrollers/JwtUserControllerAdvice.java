package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;

@ControllerAdvice
@Slf4j
public class JwtUserControllerAdvice {

    @ModelAttribute
    public JwtUser tokenUtilPerRequest(WebRequest request, Principal principal) {
        if (request == null) return null;
        if (!(principal instanceof Authentication)) return null;
        Authentication authentication = (Authentication) principal;

        return (JwtUser) authentication.getPrincipal();
    }
}

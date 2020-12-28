package com.fortalezasec.firewarning.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fortalezasec.firewarning.security.jwt.JwtUtils;
import com.fortalezasec.firewarning.security.payload.request.LoginRequest;
import com.fortalezasec.firewarning.security.payload.response.JwtResponse;
import com.fortalezasec.firewarning.security.payload.response.MessageResponse;
import com.fortalezasec.firewarning.security.services.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
  }

  @PostMapping("/logout")
  public ResponseEntity<MessageResponse> logoutUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(null);
    return ResponseEntity.ok(new MessageResponse("logout successful"));
  }
}

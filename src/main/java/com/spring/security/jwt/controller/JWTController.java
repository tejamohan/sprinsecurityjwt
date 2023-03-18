package com.spring.security.jwt.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.service.MyUserDetailsService;
import com.spring.security.jwt.util.AppUtil;
import com.spring.security.jwt.util.JwtWebToken;
import com.spring.security.jwt.util.Jwtutil;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JWTController {
	
	private static final Logger logger= LoggerFactory.getLogger(JWTController.class);
	

	private MyUserDetailsService userDetailsService;
	

	private AuthenticationManager auth;
	

	private JwtWebToken webToken;
	
	
	
	@PostMapping("/auth")
	public ResponseEntity<String> getToken(@RequestBody Jwtutil request) throws Exception{
		
		try {
			logger.info("authenticate  the credentials");
	       auth.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
	       logger.info("credentilas verified");
	       throw new Exception("Invalid Credentials");
		}catch(Exception e) {
			logger.error("Exception -"+AppUtil.getLogReport(e)); 
			
			
			
			//e.printStackTrace();
		   
		}
		final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
		final String token=webToken.generateToken(userDetails);
		return new ResponseEntity<String>(token,HttpStatus.OK);
	}
	
	
	

}

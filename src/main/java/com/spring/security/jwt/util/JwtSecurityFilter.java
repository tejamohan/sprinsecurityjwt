package com.spring.security.jwt.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.security.jwt.service.MyUserDetailsService;
import com.spring.security.jwt.util.JwtWebToken;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JwtWebToken utils;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt=null;
		String username=null;
	   
		String authorization=request.getHeader("Authorization");
		
		if(authorization!=null && authorization.startsWith("Bearer ")) {
			
			jwt=authorization.substring(7);
			username=utils.getUsernameFromToken(jwt);
		}
	 if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
		 UserDetails user=this.userDetailsService.loadUserByUsername(username);
		 if(utils.validateToken(jwt, user)) {
			 UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
			 token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			 SecurityContextHolder.getContext().setAuthentication(token);
		 }
		 
	 }
	
			
			
		filterChain.doFilter(request, response);	
		
		
	}

}

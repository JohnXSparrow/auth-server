package br.com.AuthServer.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class LoginController {

	@RequestMapping("/login")
    public String login() {    	    	
        return "/login";
    }
	
	@RequestMapping("/")
    public void init(HttpServletResponse response) {    	    	
		response.setHeader("Location", "/auth/login");
	    response.setStatus(302);
    }
	
	@GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
	
	@ApiOperation(hidden = true, value = "")
	@GetMapping("/swagger")
	public void swaggerRedirect(HttpServletResponse response) {
	    response.setHeader("Location", "/auth/swagger-ui.html");
	    response.setStatus(302);
	}
	
}

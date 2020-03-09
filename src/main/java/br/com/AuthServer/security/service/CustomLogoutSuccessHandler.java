package br.com.AuthServer.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
	
//	@Autowired
//	OauthAccessTokenRepository oauthAccessTokenRepository;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
//		if (authentication != null) {
//			OauthAccessToken oat = oauthAccessTokenRepository.findByUsername(authentication.getName());
//
//			if (oat.getClient_id() == "app") {
//				response.sendRedirect("url");
//			} else {
//				response.sendRedirect("/");
//			}
//
//		} else {
//			response.sendRedirect("/");
//		}

		response.sendRedirect("/");

		super.onLogoutSuccess(request, response, authentication);
	}
}
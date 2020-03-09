package br.com.AuthServer.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	private boolean returnn;

	public boolean hasAccess(Authentication authentication, String authority) {
		authentication.getAuthorities().forEach(x -> {
			if (x.getAuthority().contains("ADMIN") || x.getAuthority().contains(authority)) {
				returnn = true;
			}
		});
		return returnn;
	}

}
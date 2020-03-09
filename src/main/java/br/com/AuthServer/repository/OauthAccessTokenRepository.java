package br.com.AuthServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.AuthServer.security.service.OauthAccessToken;

public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, Long> {
	
	@Query(value = "Select u from OauthAccessToken u where u.user_name=:pusername")
	public OauthAccessToken findByUsername(@Param("pusername") String username);

}

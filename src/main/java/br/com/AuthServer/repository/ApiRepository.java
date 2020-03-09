package br.com.AuthServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.AuthServer.model.Api;

public interface ApiRepository extends JpaRepository<Api, Long> {

}

package br.com.AuthServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.AuthServer.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

}
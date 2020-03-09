package br.com.AuthServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.AuthServer.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Privilege findByName(String name);

	@Override
	void delete(Privilege privilege);

}

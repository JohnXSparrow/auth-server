package br.com.AuthServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.AuthServer.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	@Query(value = "Select u from Company u where u.companyName=:pcompanyName")
	public Company findByName(@Param("pcompanyName") String companyName);
	
	@Query(value = "Select u from Company u where u.tradingName=:ptradingName")
	public Company findByTradingName(@Param("ptradingName") String tradingName);

}

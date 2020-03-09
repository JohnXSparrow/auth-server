package br.com.AuthServer.model.vo;

import java.util.Set;

import javax.validation.constraints.NotNull;

public class RoleAddVO {

	@NotNull
	private String name;
	
	@NotNull
	private Set<Long> privileges_id;
	
	@NotNull
	private Long company_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getPrivileges_id() {
		return privileges_id;
	}

	public void setPrivileges_id(Set<Long> privileges_id) {
		this.privileges_id = privileges_id;
	}

	public Long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	
}

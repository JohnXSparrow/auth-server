package br.com.AuthServer.model.vo;

import javax.validation.constraints.NotNull;

public class PrivilegeAddVO {

	@NotNull
	private String name;

	@NotNull
	private Long apiId;

	@NotNull
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getApiId() {
		return apiId;
	}

	public void setApiId(Long apiId) {
		this.apiId = apiId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
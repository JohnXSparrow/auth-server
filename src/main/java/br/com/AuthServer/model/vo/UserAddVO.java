package br.com.AuthServer.model.vo;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserAddVO {
  
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @NotNull
    private String occupation;
    
    @NotNull
    private String phone;
    
    @NotNull
    @Pattern(message = "Provide a valid email address. Ex: user@company.com", regexp = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}.[a-z]{0,2}$")
    private String email;
    
    @NotNull
    @Size(min = 6, message = "The password must contain 6 or more characters.")
    private String password;

	@NotNull
    private boolean enabled;

	@NotNull
    private Set<Long> roles_id;
    
    @NotNull
	private Long company_id;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Long> getRoles_id() {
		return roles_id;
	}

	public void setRoles_id(Set<Long> roles_id) {
		this.roles_id = roles_id;
	}

	public Long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
   
}
package br.com.AuthServer.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.AuthServer.exception.GenericReturnMessage;
import br.com.AuthServer.model.Company;
import br.com.AuthServer.model.Privilege;
import br.com.AuthServer.model.Role;
import br.com.AuthServer.model.vo.RoleAddVO;
import br.com.AuthServer.repository.CompanyRepository;
import br.com.AuthServer.repository.PrivilegeRepository;
import br.com.AuthServer.repository.RoleRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/adm/role")
public class RoleController {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@ApiOperation(value = "Add new role", response = GenericReturnMessage.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_ROLE_CREATE')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	ResponseEntity<Object> addRole(@RequestBody @Valid RoleAddVO roleAddVO, BindingResult bResult) {
		
		if (bResult.hasErrors()) {
			return new ResponseEntity<>(bResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
		}
		
		Company company = companyRepository.findById(roleAddVO.getCompany_id()).orElse(null);		
		if (company == null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-ROLE-1", "Company with id "+ roleAddVO.getCompany_id() + " not found."), HttpStatus.BAD_REQUEST);
		}
		
		roleAddVO.setName(company.getTradingName().trim().replaceAll("[^a-zA-Z0-9]", "").toUpperCase() + "_" + roleAddVO.getName().toUpperCase());
		
		if (roleRepository.findByName(roleAddVO.getName()) != null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-ROLE-2", "Role with this name has already been registered."), HttpStatus.BAD_REQUEST);
		}
		
		Privilege privilege = new Privilege();
		Set<Privilege> privileges = new HashSet<Privilege>();
		
		for (Long privilege_id : roleAddVO.getPrivileges_id()) {
			privilege = privilegeRepository.findById(privilege_id).orElse(null);
			if (privilege == null) {
				return new ResponseEntity<>(new GenericReturnMessage("ADD-ROLE-3", "Privilege with id "+ privilege_id + " not found."), HttpStatus.BAD_REQUEST);
			} else {
				privileges.add(privilege);
			}
		}
		
		Role role = new Role();
		role.setCompany(company);
		role.setName(roleAddVO.getName());
		role.setPrivileges(privileges);
		
		roleRepository.saveAndFlush(role);
		
		return new ResponseEntity<>(new GenericReturnMessage("200", "Saved successfully."), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a list of roles", response = Role.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_ROLE_READ')")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> listRoles() {
				
		Collection<Role> roles = roleRepository.findAll();
	
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

}

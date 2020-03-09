package br.com.AuthServer.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.AuthServer.exception.GenericReturnMessage;
import br.com.AuthServer.model.Company;
import br.com.AuthServer.model.Role;
import br.com.AuthServer.model.User;
import br.com.AuthServer.model.vo.UserAddVO;
import br.com.AuthServer.repository.CompanyRepository;
import br.com.AuthServer.repository.RoleRepository;
import br.com.AuthServer.repository.UserRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/adm/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
		
	@ApiOperation(value = "Add new user", response = GenericReturnMessage.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_USER_CREATE')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	ResponseEntity<Object> addUser(@RequestBody @Valid UserAddVO userAddVO, BindingResult bResult) {
		
		if (bResult.hasErrors()) {
			return new ResponseEntity<>(bResult.getFieldError(), HttpStatus.BAD_REQUEST);
		}
		
		if (userRepository.findByEmail(userAddVO.getEmail()) != null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-USER-1", "Email already registered."), HttpStatus.BAD_REQUEST);
		}
		
		Role role = new Role();
		Set<Role> roles = new HashSet<Role>();
		
		for (Long role_id : userAddVO.getRoles_id()) {
			role = roleRepository.findById(role_id).orElse(null);
			if (role == null) {
				return new ResponseEntity<>(new GenericReturnMessage("ADD-USER-2", "Role with id "+ role_id + " not found."), HttpStatus.BAD_REQUEST);
			} else {
				roles.add(role);
			}
		}
		
		Company company = companyRepository.findById(userAddVO.getCompany_id()).orElse(null);
		if (company == null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-USER-3", "Company with id "+ userAddVO.getCompany_id() + " not found."), HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setFirstName(userAddVO.getFirstName());
		user.setLastName(userAddVO.getLastName());
		user.setCompany(company);
		user.setOccupation(userAddVO.getOccupation());
		user.setPhone(userAddVO.getPhone());
		user.setEmail(userAddVO.getEmail());
		user.setPassword(passwordEncoder.encode(userAddVO.getPassword()));
		user.setEnabled(userAddVO.isEnabled());
		user.setRoles(roles);
		userRepository.saveAndFlush(user);
		
		return new ResponseEntity<>(new GenericReturnMessage("200", "Saved successfully."), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a list of users", response = User.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_USER_READ')")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> listUsers() {	
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get unique user details by email", response = User.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_SERVER')")
	@RequestMapping(value = "/get/email", method = RequestMethod.GET, produces = "application/json")
	ResponseEntity<Object> getUser(@RequestHeader(value = "email", required = true) String email) {
		return new ResponseEntity<>(userRepository.findByEmail(email), HttpStatus.OK);		
	}

}

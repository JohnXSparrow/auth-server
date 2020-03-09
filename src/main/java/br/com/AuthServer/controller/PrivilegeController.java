package br.com.AuthServer.controller;

import java.util.Collection;

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
import br.com.AuthServer.model.Privilege;
import br.com.AuthServer.model.vo.PrivilegeAddVO;
import br.com.AuthServer.repository.ApiRepository;
import br.com.AuthServer.repository.PrivilegeRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/adm/privilege")
public class PrivilegeController {	
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	ApiRepository apiRepository;
	
	@ApiOperation(value = "Add new privilege", response = GenericReturnMessage.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_PRIVILEGE_CREATE')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	ResponseEntity<Object> addPrivilege(@RequestBody @Valid PrivilegeAddVO privilegeAddVO, BindingResult bResult) {
		
		if (bResult.hasErrors()) {
			return new ResponseEntity<>(bResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
		}
		
		if (privilegeRepository.findByName(privilegeAddVO.getName()) != null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-PRIVILEGE-1", "Privilege with this name has already been registered."), HttpStatus.BAD_REQUEST);
		}
		
		if (apiRepository.findById(privilegeAddVO.getApiId()).orElse(null) == null) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-PRIVILEGE-2", "API with id " + privilegeAddVO.getApiId() + " not found."), HttpStatus.BAD_REQUEST);
		}
		
		Privilege privilege = new Privilege();
		privilege.setName(privilegeAddVO.getName());
		privilege.setApiId(privilegeAddVO.getApiId());
		privilege.setDescription(privilegeAddVO.getDescription());
		privilegeRepository.saveAndFlush(privilege);
		
		return new ResponseEntity<>(new GenericReturnMessage("200", "Saved successfully."), HttpStatus.OK);
	}

	@ApiOperation(value = "Get a list of privileges", response = Privilege.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_PRIVILEGE_READ')")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> listPrivileges() {
				
		Collection<Privilege> privileges = privilegeRepository.findAll();
	
		return new ResponseEntity<>(privileges, HttpStatus.OK);
	}
	
}

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
import br.com.AuthServer.model.Company;
import br.com.AuthServer.model.vo.CompanyAddVO;
import br.com.AuthServer.repository.CompanyRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/adm/company")
public class CompanyController {
	
	@Autowired
	CompanyRepository companyRepository;
	
	@ApiOperation(value = "Add new company", response = GenericReturnMessage.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_COMPANY_CREATE')")
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	ResponseEntity<Object> addCompany(@RequestBody @Valid CompanyAddVO companyAddVO, BindingResult bResult) {	
		
		if (bResult.hasErrors()) {
			return new ResponseEntity<>(bResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
		}
		
		if (companyRepository.findByName(companyAddVO.getCompanyName()) != null ) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-COMPANY-1", "CompanyName has already been registered."), HttpStatus.BAD_REQUEST);
		} else if (companyRepository.findByTradingName(companyAddVO.getTradingName()) != null ) {
			return new ResponseEntity<>(new GenericReturnMessage("ADD-COMPANY-2", "TradingName has already been registered."), HttpStatus.BAD_REQUEST);
		}
		
		Company company = new Company();
		company.setCnpj(companyAddVO.getCnpj());
		company.setCompanyName(companyAddVO.getCompanyName());
		company.setTradingName(companyAddVO.getTradingName());		
		companyRepository.saveAndFlush(company);
		
		return new ResponseEntity<>(new GenericReturnMessage("200", "Saved successfully."), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get a list of companies", response = Company.class)
	@PreAuthorize("@securityService.hasAccess(authentication, 'AUTHSERVER_COMPANY_READ')")
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> listCompanies() {
				
		Collection<Company> companies = companyRepository.findAll();
	
		return new ResponseEntity<>(companies, HttpStatus.OK);
	}

}

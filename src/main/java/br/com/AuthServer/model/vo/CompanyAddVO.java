package br.com.AuthServer.model.vo;

import javax.validation.constraints.NotNull;

public class CompanyAddVO {
    
    @NotNull
    private String cnpj;
    
    @NotNull
    private String companyName;
    
    @NotNull
    private String tradingName;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}
    
}

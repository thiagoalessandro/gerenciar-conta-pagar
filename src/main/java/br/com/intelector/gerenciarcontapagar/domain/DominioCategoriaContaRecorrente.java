package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioCategoriaContaRecorrente {

	ASSINATURA("ASSINATURA"),
	DESPESA_CASA("DESPESA_CASA");

	private String description;

	DominioCategoriaContaRecorrente(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	
}

package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioTipoGasto {

	CARTAO("CARTAO"),
	DESPESA_CASA("DESPESA_CASA");

	private String description;

	DominioTipoGasto(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}

package br.com.intelector.gerenciarcontapagar.domain;

public enum DominioCategoriaLancamento {

	COMPRA_PARCELADA("COMPRA_PARCELADA"),
	ASSINATURA("ASSINATURA"),
	COMPRA_PARCELA_UNICA("COMPRA_PARCELA_UNICA");

	private String description;

	DominioCategoriaLancamento(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}

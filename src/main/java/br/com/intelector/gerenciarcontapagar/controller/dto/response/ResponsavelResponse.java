package br.com.intelector.gerenciarcontapagar.controller.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponsavelResponse implements Serializable {

    private String nome;

    public ResponsavelResponse(String nome) {
        this.nome = nome;
    }
}

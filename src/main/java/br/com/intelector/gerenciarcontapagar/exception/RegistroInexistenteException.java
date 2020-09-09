package br.com.intelector.gerenciarcontapagar.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistroInexistenteException extends Exception {
    public RegistroInexistenteException(Exception e) {
        super(e);
    }
    public RegistroInexistenteException(String messagem) {
        super(messagem);
    }
}

package com.github.daniel.resumob3.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class ExtratoNegociacao {
    private final List<Negociacao> negociacoes;

    public ExtratoNegociacao(List<Negociacao> negociacoes) {
        this.negociacoes = negociacoes;
    }
}

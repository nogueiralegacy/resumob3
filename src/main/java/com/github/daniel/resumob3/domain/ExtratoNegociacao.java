package com.github.daniel.resumob3.domain;

import lombok.Data;

import java.util.List;

@Data
public class ExtratoNegociacao {
    private List<Negociacao> negociacoes;

    public ExtratoNegociacao(List<Negociacao> negociacoes) {
        this.negociacoes = negociacoes;
    }
}

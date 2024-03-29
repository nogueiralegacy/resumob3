package com.github.daniel.resumob3.domain.negociacao;

public enum TipoDeMovimentacao {
    COMPRA("Compra"),
    VENDA("Venda");

    final String tipoDeMovimentacao;
    TipoDeMovimentacao(String tipoDeMovimentacao) {
        this.tipoDeMovimentacao = tipoDeMovimentacao;
    }

    public String getTipoDeMovimentacao() {
        return tipoDeMovimentacao;
    }
}

package com.github.daniel.resumob3.domain;

public enum TipoDeMovimentacao {
    COMPRA,
    VENDA;


    public boolean isCompra() {
        return this == COMPRA;
    }

    public boolean isVenda() {
        return this == VENDA;
    }
}

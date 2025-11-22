package com.github.daniel.resumob3.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class Negociacao {
    private Date dataDoNegocio;
    private TipoDeMovimentacao tipoDeMovimentacao;
    private String mercado;
    private Date vencimento;
    private String instituicao;
    private String codigoDeNegociacao;
    private int quantidade;
    private double preco;
    private double valor;
}

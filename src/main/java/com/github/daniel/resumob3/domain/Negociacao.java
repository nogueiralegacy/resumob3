package com.github.daniel.resumob3.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
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

package com.github.daniel.resumob3.domain;

import lombok.Getter;

@Getter
public class ResumoAtivo {
    private final String codigo;
    private int quantidadeComprada;
    private int quantidadeVendida;
    private double precoMedio;
    private double capitalInvestidoBruto;
    private double capitalResgatadoBruto;

    public ResumoAtivo(String codigo) {
        this.codigo = codigo;
        this.quantidadeComprada = 0;
        this.quantidadeVendida = 0;
        this.precoMedio = 0;
        this.capitalInvestidoBruto = 0;
        this.capitalResgatadoBruto = 0;
    }

    public void contabilizarCompra(int quantidade, double preco) {
        this.precoMedio = (this.precoMedio * this.quantidadeComprada + preco * quantidade) / (this.quantidadeComprada + quantidade);
        this.capitalInvestidoBruto += quantidade * preco;
        this.quantidadeComprada += quantidade;

    }

    public void contabilizarVenda(int quantidade, double preco) {
        this.capitalResgatadoBruto += quantidade * preco;
        this.quantidadeVendida += quantidade;
    }

    public double calcularCapitalInvestidoLiquido() {
        return capitalInvestidoBruto - capitalResgatadoBruto;
    }

    public double calcularMontanteMovimentado() {
        return capitalInvestidoBruto + capitalResgatadoBruto;
    }

}

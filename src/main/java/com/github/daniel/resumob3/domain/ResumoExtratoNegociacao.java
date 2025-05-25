package com.github.daniel.resumob3.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
public class ResumoExtratoNegociacao {
    private final Map<String, ResumoAtivo> resumoAtivos;
    private double capitalInvestidoBruto;
    private double capitalResgatadoBruto;
    private double quantidadeComprada;
    private double quantidadeVendida;

    public ResumoExtratoNegociacao(Map<String, ResumoAtivo> resumoAtivos) {
        if (resumoAtivos == null) {
            log.error("Erro ao criar {}", getClass().getSimpleName());
            throw new IllegalStateException();
        }

        this.resumoAtivos = resumoAtivos;

        for (ResumoAtivo resumoAtivo : resumoAtivos.values()) {
            this.capitalInvestidoBruto += resumoAtivo.getCapitalInvestidoBruto();
            this.capitalResgatadoBruto += resumoAtivo.getCapitalResgatadoBruto();
            this.quantidadeComprada += resumoAtivo.getQuantidadeComprada();
            this.quantidadeVendida += resumoAtivo.getQuantidadeVendida();
        }
    }

    public double calcularCapitalInvestidoLiquido() {
        return capitalInvestidoBruto - capitalResgatadoBruto;
    }

    public double calcularMontanteMovimentado() {
        return capitalInvestidoBruto + capitalResgatadoBruto;
    }
}


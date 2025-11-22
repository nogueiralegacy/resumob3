package com.github.daniel.resumob3.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Slf4j
@Getter
public class ResumoExtratoNegociacao {
    private final Map<String, ResumoAtivo> resumoAtivos;
    private final Date dataInicio;
    private final Date dataFim;

    public ResumoExtratoNegociacao(Map<String, ResumoAtivo> resumoAtivos, Date inicio, Date fim) {
        if (resumoAtivos == null) {
            log.error("Erro ao criar {}", getClass().getSimpleName());
            throw new IllegalStateException();
        }

        this.resumoAtivos = resumoAtivos;
        this.dataInicio = inicio;
        this.dataFim = fim;
    }
}


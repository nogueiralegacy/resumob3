package com.github.daniel.resumob3.service;

import com.github.daniel.resumob3.domain.ResumoAtivo;
import com.github.daniel.resumob3.io.XLSXExtratoNegociacaoRepository;
import com.github.daniel.resumob3.repository.ExtratoNegociacaoRepository;
import com.github.daniel.resumob3.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtratoNegociacaoServiceTest {
    private ExtratoNegociacaoRepository extratoNegociacaoRepository;

    @BeforeEach
    void setUp() {
        String resourceName = "negociacoes.xlsx";

        this.extratoNegociacaoRepository =
                new XLSXExtratoNegociacaoRepository(new Utils().getInputStream(resourceName));
    }

    @Test
    void contabilizaNegociacoesSucesso() {
        Map<String, ResumoAtivo> resumoAtivos = new ExtratoNegociacaoService(extratoNegociacaoRepository)
                .contabilizarNegociacoes();

        assertEquals(3, resumoAtivos.size());
        assertEquals(3, resumoAtivos.get("BTLG11").getQuantidadeComprada());
        assertEquals(0.0, resumoAtivos.get("BTLG11").getQuantidadeVendida());
        assertEquals(102.51, Math.round(resumoAtivos.get("BTLG11").getPrecoMedio() * 100.0) / 100.0);

        assertEquals(16, resumoAtivos.get("KNCR11").getQuantidadeComprada());
    }
}
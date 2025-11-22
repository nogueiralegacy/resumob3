package com.github.daniel.resumob3.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResumoAtivoTest {

    private ResumoAtivo resumoAtivo;

    @BeforeEach
    void setUp() {
        resumoAtivo = new ResumoAtivo("PETR4");
    }

    @Test
    void testPrecoMedioComUmaCompra() {
        // Compra de 100 ações a R$ 30,00
        resumoAtivo.contabilizarCompra(100, 30.0);

        assertEquals(100, resumoAtivo.getQuantidadeComprada());
        assertEquals(0, resumoAtivo.getQuantidadeVendida());
        assertEquals(30.0, resumoAtivo.getPrecoMedio(), 0.01);
        assertEquals(100, resumoAtivo.getQuantidadeAtualNaCarteira());
    }

    @Test
    void testPrecoMedioComMultiplasCompras() {
        // Compra de 100 ações a R$ 30,00
        resumoAtivo.contabilizarCompra(100, 30.0);
        // Compra de 200 ações a R$ 15,00
        resumoAtivo.contabilizarCompra(200, 15.0);

        // Preço médio esperado: (100*30 + 200*15) / 300 = 6000/300 = 20.00
        assertEquals(300, resumoAtivo.getQuantidadeComprada());
        assertEquals(20.0, resumoAtivo.getPrecoMedio(), 0.01);
    }

    @Test
    void testPrecoMedioNaoMudaComVenda() {
        // Compra de 100 ações a R$ 30,00
        resumoAtivo.contabilizarCompra(100, 30.0);
        // Compra de 200 ações a R$ 15,00
        resumoAtivo.contabilizarCompra(200, 15.0);

        double precoMedioAntesVenda = resumoAtivo.getPrecoMedio();

        // Venda de 50 ações a R$ 25,00
        resumoAtivo.contabilizarVenda(50);

        // O preço médio NÃO deve mudar após a venda
        assertEquals(precoMedioAntesVenda, resumoAtivo.getPrecoMedio(), 0.01);
        assertEquals(20.0, resumoAtivo.getPrecoMedio(), 0.01);
        assertEquals(250, resumoAtivo.getQuantidadeAtualNaCarteira());
    }
}


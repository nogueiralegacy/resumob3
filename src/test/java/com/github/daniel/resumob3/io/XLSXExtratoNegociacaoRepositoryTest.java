package com.github.daniel.resumob3.io;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.repository.ExtratoNegociacaoRepository;
import com.github.daniel.resumob3.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XLSXExtratoNegociacaoRepositoryTest {
    @Test
    void testLoad() {
        String resourceName = "negociacoes.xlsx";

        ExtratoNegociacaoRepository extratoNegociacaoRepository =
                new XLSXExtratoNegociacaoRepository(new Utils().getInputStream(resourceName));

        ExtratoNegociacao extratoNegociacao = extratoNegociacaoRepository.load();
        assertEquals(17, extratoNegociacao.getNegociacoes().size());
    }
}
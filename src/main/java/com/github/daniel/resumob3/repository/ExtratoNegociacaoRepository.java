package com.github.daniel.resumob3.repository;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.domain.ResumoExtratoNegociacao;

import java.io.IOException;
import java.nio.file.Path;

public interface ExtratoNegociacaoRepository {

    ExtratoNegociacao load();

    /**
     * Exporta os dados do resumo de negociação para um arquivo CSV.
     *
     * @param resumoExtratoNegociacao O resumo contendo os dados a serem exportados.
     * @param diretorio O diretório onde o arquivo CSV será salvo.
     * @return O caminho do arquivo gerado.
     * @throws IOException Se ocorrer algum erro durante o processo de exportação.
     */
    Path exportar(ResumoExtratoNegociacao resumoExtratoNegociacao, String diretorio) throws IOException;

}

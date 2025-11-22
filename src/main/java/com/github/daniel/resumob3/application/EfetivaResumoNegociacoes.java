package com.github.daniel.resumob3.application;

import com.github.daniel.resumob3.domain.ResumoExtratoNegociacao;
import com.github.daniel.resumob3.io.CSVExtratoNegociacaoRepository;
import com.github.daniel.resumob3.io.XLSXExtratoNegociacaoRepository;
import com.github.daniel.resumob3.service.ResumeExtratoNegociacaoService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

@Component
public class EfetivaResumoNegociacoes {

    public static void efetivaOperacao() {
        ExploradorDeArquivosSwing explorador = new ExploradorDeArquivosSwing();
        String titulo = "Selecione o arquivo com as negociações";
        String descricao = "Arquivo XLSX";
        String extensao = "xlsx";
        File file = explorador.selecionarArquivo(titulo, descricao, extensao);

        // Verifica se o usuário cancelou a seleção
        if (file == null) {
            System.out.println("Operação cancelada pelo usuário.");
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {

            // Carregar negociações a partir do arquivo XLSX
            XLSXExtratoNegociacaoRepository xslxRepository = new XLSXExtratoNegociacaoRepository(fileInputStream);


            ResumoExtratoNegociacao resumo = ResumeExtratoNegociacaoService.contabilizarNegociacoes(xslxRepository.load());

            String pathParent = file.getParent();
            // Exportar o resumo gerado para CSV usando CSVExtratoNegociacaoRepository
            Path pathCSV = new CSVExtratoNegociacaoRepository().exportar(
                    resumo, pathParent
            );

            System.out.println("Resumo gerado com sucesso!");
            System.out.println("Arquivo salvo em: " + pathCSV.toAbsolutePath());

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Arquivo não encontrado: " + file.getAbsolutePath(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar o arquivo.", e);
        }
    }
}
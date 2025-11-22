package com.github.daniel.resumob3.io;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.domain.ResumoAtivo;
import com.github.daniel.resumob3.domain.ResumoExtratoNegociacao;
import com.github.daniel.resumob3.repository.ExtratoNegociacaoRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CSVExtratoNegociacaoRepository implements ExtratoNegociacaoRepository {

    @Override
    public Path exportar(ResumoExtratoNegociacao resumoExtratoNegociacao, String diretorio) throws IOException {
        // Cria o diretório se ele não existir
        Path dir = Paths.get(diretorio);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        // Define o nome do arquivo com um timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path arquivoPath = Paths.get(diretorio, "resumo_negociacoes_" + timestamp + ".csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoPath.toFile()))) {
            // Escreve o cabeçalho e os dados na seção de ativos
            writer.write("RESUMO POR ATIVO");
            writer.newLine();
            escreverSecaoAtivos(resumoExtratoNegociacao, writer);

            // Linha em branco para separação
            writer.newLine();

            // Escreve o cabeçalho e os dados do resumo geral
            writer.write("RESUMO GERAL");
            writer.newLine();
            escreverResumoGeral(resumoExtratoNegociacao, writer);
        }

        return arquivoPath;
    }

    private void escreverSecaoAtivos(ResumoExtratoNegociacao resumoExtratoNegociacao, BufferedWriter writer) throws IOException {
        // Cabeçalho
        writer.write("Código,Quantidade Comprada,Quantidade Vendida,Quantidade Atual na Carteira,Preço Médio");
        writer.newLine();

        // Dados por ativo
        for (ResumoAtivo ativo : resumoExtratoNegociacao.getResumoAtivos().values()) {
            writer.write(String.format("%s,%d,%d,%d,%.2f",
                    escapeCsv(ativo.getCodigo()),
                    ativo.getQuantidadeComprada(),
                    ativo.getQuantidadeVendida(),
                    ativo.getQuantidadeAtualNaCarteira(),
                    ativo.getPrecoMedio()));
            writer.newLine();
        }
    }

    private void escreverResumoGeral(ResumoExtratoNegociacao resumoExtratoNegociacao, BufferedWriter writer) throws IOException {
        // Cabeçalho
        writer.write("Data Início,Data Fim");
        writer.newLine();

        // Formata as datas
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio = resumoExtratoNegociacao.getDataInicio() != null 
                ? dateFormat.format(resumoExtratoNegociacao.getDataInicio()) 
                : "";
        String dataFim = resumoExtratoNegociacao.getDataFim() != null 
                ? dateFormat.format(resumoExtratoNegociacao.getDataFim()) 
                : "";

        // Dados do resumo geral
        writer.write(String.format("%s,%s", dataInicio, dataFim));
        writer.newLine();
    }

    /**
     * Escapa valores que contêm caracteres problemáticos para o formato CSV.
     */
    private String escapeCsv(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    @Override
    public ExtratoNegociacao load() {
        return null;
    }
}

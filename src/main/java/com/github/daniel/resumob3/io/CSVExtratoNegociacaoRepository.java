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
        writer.write("Código,Quantidade Comprada,Quantidade Vendida,Preço Médio," +
                "Capital Investido Bruto,Capital Resgatado Bruto,Capital Investido Líquido,Montante Movimentado");
        writer.newLine();

        // Dados por ativo
        for (ResumoAtivo ativo : resumoExtratoNegociacao.getResumoAtivos().values()) {
            writer.write(String.format("%s,%d,%d,%.2f,%.2f,%.2f,%.2f,%.2f",
                    escapeCsv(ativo.getCodigo()),
                    ativo.getQuantidadeComprada(),
                    ativo.getQuantidadeVendida(),
                    ativo.getPrecoMedio(),
                    ativo.getCapitalInvestidoBruto(),
                    ativo.getCapitalResgatadoBruto(),
                    ativo.calcularCapitalInvestidoLiquido(),
                    ativo.calcularMontanteMovimentado()));
            writer.newLine();
        }
    }

    private void escreverResumoGeral(ResumoExtratoNegociacao resumoExtratoNegociacao, BufferedWriter writer) throws IOException {
        // Cabeçalho
        writer.write("Capital Investido Bruto,Capital Resgatado Bruto,Capital Investido Líquido," +
                "Montante Movimentado,Quantidade Comprada,Quantidade Vendida");
        writer.newLine();

        // Dados do resumo geral
        writer.write(String.format("%.2f,%.2f,%.2f,%.2f,%.0f,%.0f",
                resumoExtratoNegociacao.getCapitalInvestidoBruto(),
                resumoExtratoNegociacao.getCapitalResgatadoBruto(),
                resumoExtratoNegociacao.calcularCapitalInvestidoLiquido(),
                resumoExtratoNegociacao.calcularMontanteMovimentado(),
                resumoExtratoNegociacao.getQuantidadeComprada(),
                resumoExtratoNegociacao.getQuantidadeVendida()));
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

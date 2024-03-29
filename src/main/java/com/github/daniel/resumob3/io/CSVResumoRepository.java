package com.github.daniel.resumob3.io;

import com.github.daniel.resumob3.domain.negociacao.Negociacao;
import com.github.daniel.resumob3.domain.negociacao.Resumo;
import com.github.daniel.resumob3.domain.negociacao.ResumoRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class CSVResumoRepository implements ResumoRepository {
    // Formatação dos valores monetátios para o padrão nacional.
    DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    public void save(Resumo resumo, OutputStream output) {

        String formatoCSV = toCSV(resumo);
        byte[] arquivoCSVByte = formatoCSV.getBytes();

        try {
            output.write(arquivoCSVByte);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toCSV(Resumo resumo) {
        StringBuilder sb = new StringBuilder();

        sb.append("Tipo de Movimentação");
        sb.append(";");
        sb.append("Ativo");
        sb.append(";");
        sb.append("Preço Médio");
        sb.append(";");
        sb.append("Quantidade Negociada");
        sb.append(";");
        sb.append("Valor Total Negociado");
        sb.append("\n");

        double valorTotalNegociado = 0;
        for (Negociacao resumoNegociacao : resumo.getResumoNegociacoes()) {
            sb.append(resumoNegociacao.getTipoDeMovimentacao()
                    .getTipoDeMovimentacao());
            sb.append(";");
            sb.append(resumoNegociacao.getAtivo());
            sb.append(";");
            sb.append("R$ " + df.format(resumoNegociacao.getPrecoNegociado()));
            sb.append(";");
            sb.append(resumoNegociacao.getQuantidadeNegociada());
            sb.append(";");
            sb.append("R$ " + df.format(resumoNegociacao.getValorTotalNegociado()));
            sb.append("\n");
            valorTotalNegociado += resumoNegociacao.getValorTotalNegociado();
        }
        sb.append("Valor Total Negociado: " + "R$ " + df.format(valorTotalNegociado));
        return sb.toString();
    }
}
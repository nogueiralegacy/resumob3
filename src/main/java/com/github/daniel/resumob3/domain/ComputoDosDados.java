/*
package com.github.daniel.resumob3.domain;

import java.util.ArrayList;
import java.util.List;

public class ComputoDosDados {

    public static Negociacao resumoNegocicoes(List<Negociacao> todas, String ativo){
        Negociacao resumoNegociacoesCompra = resumoNegocicoes(todas, ativo, TipoDeMovimentacao.COMPRA);
        Negociacao resumoNegociacoesVenda = resumoNegocicoes(todas, ativo, TipoDeMovimentacao.VENDA);

        int quantidadeNegociada = (resumoNegociacoesCompra.getQuantidade()
                - resumoNegociacoesVenda.getQuantidade());
        double valorTotalNegociado = resumoNegociacoesCompra.getValor()
                - resumoNegociacoesVenda.getValor();
        double precoMedio = 0.0;
        try {
            precoMedio = valorTotalNegociado / quantidadeNegociada;
        } catch (ArithmeticException e) {
            valorTotalNegociado = 0.0;
        }
        return new Negociacao(TipoDeMovimentacao.COMPRA, ativo, quantidadeNegociada, precoMedio,
                valorTotalNegociado);
        }

        public static Negociacao resumoNegocicoes(List<Negociacao> todas, String ativo, TipoDeMovimentacao tipoDeMovimentacao) {
            int quantidadeNegociada = 0;
            double valorTotalNegociado = 0.0;
            double precoMedio;

            List<Negociacao> negociacoesDeUmAtivo = negociacoesDeUmAtivo(todas, ativo);

            for (Negociacao negociacao : negociacoesDeUmAtivo) {
                if (negociacao.getTipoDeMovimentacao().equals(tipoDeMovimentacao)) {
                    quantidadeNegociada += negociacao.getQuantidade();
                    valorTotalNegociado += negociacao.getValor();
                }
            }

            precoMedio = valorTotalNegociado / quantidadeNegociada;

            return new Negociacao(tipoDeMovimentacao, ativo, quantidadeNegociada, precoMedio, valorTotalNegociado);
        }

    public static List<Negociacao> negociacoesDeUmAtivo(List<Negociacao> todas, String ativo) {
    List<Negociacao> negociacoesDeUmAtivo = new ArrayList<>();
    for (Negociacao negociacao : todas) {
        if (negociacao.getCodigoDeNegociacao().equals(ativo)) {
            negociacoesDeUmAtivo.add(negociacao);
        }
    }
    return negociacoesDeUmAtivo;
    }

    public static List<String> getAtivos(List<Negociacao> todas) {
        List<String> ativos = new ArrayList<>();

        for (Negociacao negociacao : todas) {
            if (!ativos.contains(negociacao.getCodigoDeNegociacao())) {
                ativos.add(negociacao.getCodigoDeNegociacao());
            }
        }

        return ativos;
    }
}
*/

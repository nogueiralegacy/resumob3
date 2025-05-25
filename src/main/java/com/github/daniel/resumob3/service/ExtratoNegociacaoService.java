package com.github.daniel.resumob3.service;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.domain.ResumoAtivo;
import com.github.daniel.resumob3.domain.TipoDeMovimentacao;
import com.github.daniel.resumob3.repository.ExtratoNegociacaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExtratoNegociacaoService {
    private final ExtratoNegociacao extratoNegociacao;
    
    public ExtratoNegociacaoService(ExtratoNegociacaoRepository extratoNegociacaoRepository) {
        this.extratoNegociacao = extratoNegociacaoRepository.load();
    }

    public Map<String, ResumoAtivo> contabilizarNegociacoes() {
        Map<String, ResumoAtivo> mapaDeAtivos = new HashMap<>();

        extratoNegociacao.getNegociacoes().forEach(negociacao -> {
            String codigo = negociacao.getCodigoDeNegociacao();

            // Obtém o resumo existente ou cria um novo se não existir
            ResumoAtivo resumo = mapaDeAtivos.computeIfAbsent(codigo, ResumoAtivo::new);

            if (negociacao.getTipoDeMovimentacao() == TipoDeMovimentacao.COMPRA) {
                resumo.contabilizarCompra(negociacao.getQuantidade(), negociacao.getPreco());
            } else if (negociacao.getTipoDeMovimentacao() == TipoDeMovimentacao.VENDA) {
                resumo.contabilizarVenda(negociacao.getQuantidade(), negociacao.getPreco());
            }
            // Outros tipos de movimentação podem ser adicionados aqui
        });

        return mapaDeAtivos;
    }
}

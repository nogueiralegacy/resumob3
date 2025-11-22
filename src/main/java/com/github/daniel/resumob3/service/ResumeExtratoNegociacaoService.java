package com.github.daniel.resumob3.service;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.domain.ResumoAtivo;
import com.github.daniel.resumob3.domain.ResumoExtratoNegociacao;
import com.github.daniel.resumob3.domain.TipoDeMovimentacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ResumeExtratoNegociacaoService {

    public static ResumoExtratoNegociacao contabilizarNegociacoes(ExtratoNegociacao extratoNegociacao) {
        Map<String, ResumoAtivo> mapaDeAtivos = new HashMap<>();
        Date menorData = null;
        Date maiorData = null;

        for (var negociacao : extratoNegociacao.getNegociacoes()) {
            if (menorData == null || negociacao.getDataDoNegocio().before(menorData)) {
                menorData = negociacao.getDataDoNegocio();
            }

            if (maiorData == null || negociacao.getDataDoNegocio().after(maiorData)) {
                maiorData = negociacao.getDataDoNegocio();
            }

            String codigo = negociacao.getCodigoDeNegociacao().replaceFirst("F$", "");

            // Exclui direitos de subscrição: na B3, códigos terminados em "12" representam
            // direitos de subscrição de ações preferenciais, que não devem ser contabilizados
            // no relatório de negociações, pois são instrumentos temporários de exercício de direito.
            if (codigo.endsWith("12")) {
                continue;
            }

            // Obtém o resumo existente ou cria um novo se não existir
            ResumoAtivo resumo = mapaDeAtivos.computeIfAbsent(codigo, ResumoAtivo::new);

            if (negociacao.getTipoDeMovimentacao() == TipoDeMovimentacao.COMPRA) {
                resumo.contabilizarCompra(negociacao.getQuantidade(), negociacao.getPreco());
            } else if (negociacao.getTipoDeMovimentacao() == TipoDeMovimentacao.VENDA) {
                resumo.contabilizarVenda(negociacao.getQuantidade());
            }
            // Outros tipos de movimentação podem ser adicionados aqui
        }

        if (menorData == null) {
            log.error("Datas de negociação não encontradas no extrato.");
            throw new IllegalStateException("Não foi possível determinar o período analisado.");
        }

        return new ResumoExtratoNegociacao(mapaDeAtivos, menorData, maiorData);
    }
}

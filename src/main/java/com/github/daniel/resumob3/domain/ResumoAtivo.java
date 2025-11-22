package com.github.daniel.resumob3.domain;

import lombok.Getter;

@Getter
public class ResumoAtivo {
    private final String codigo;
    private int quantidadeComprada;
    private int quantidadeVendida;
    private int quantidadeAtualNaCarteira;
    private double precoMedio; // Preço médio de aquisição das ações

    public ResumoAtivo(String codigo) {
        this.codigo = codigo;
        this.quantidadeComprada = 0;
        this.quantidadeVendida = 0;
        this.quantidadeAtualNaCarteira = 0;
        this.precoMedio = 0;
    }

    /**
     * Contabiliza uma compra de ações.
     * O preço médio é calculado considerando APENAS as compras realizadas,
     * usando média ponderada. As vendas não afetam o preço médio de aquisição.
     * 
     * <p>Este cálculo segue as regras oficiais da Receita Federal para Imposto de Renda:
     * Preço Médio = Soma de (quantidade × preço) de todas as compras / Soma de todas as quantidades compradas
     * 
     * <p>Exemplo oficial (Receita Federal):
     * <ul>
     *   <li>Janeiro: 50 ações a R$ 30,00 = R$ 1.500,00</li>
     *   <li>Agosto: 50 ações a R$ 32,00 = R$ 1.600,00</li>
     *   <li>Preço Médio = (1.500 + 1.600) / 100 = R$ 31,00</li>
     * </ul>
     * 
     * <p>Referência: https://economia.uol.com.br/imposto-de-renda/noticias/redacao/2023/03/24/imposto-de-renda-2023-como-calcular-o-preco-medio-das-acoes.htm
     * 
     * @param quantidade Quantidade de ações compradas
     * @param preco Preço unitário da compra
     */
    public void contabilizarCompra(int quantidade, double preco) {
        if (this.quantidadeComprada == 0) {
            // Primeira compra: o preço médio é o preço da compra atual
            this.precoMedio = preco;
        } else {
            // Compra subsequente: calcula média ponderada considerando todas as compras
            this.precoMedio = (this.precoMedio * this.quantidadeComprada + preco * quantidade) 
                    / (this.quantidadeComprada + quantidade);
        }
        this.quantidadeComprada += quantidade;
        quantidadeAtualNaCarteira += quantidade;
    }

    /**
     * Contabiliza uma venda de ações.
     * IMPORTANTE: O preço médio NÃO é alterado por vendas, independentemente do valor
     * recebido na venda. O preço médio representa o custo médio de aquisição de todas
     * as ações compradas e permanece inalterado.
     * 
     * <p>Conforme as regras da Receita Federal, as vendas não alteram o preço médio
     * das ações remanescentes na carteira. O preço médio permanece o mesmo que era
     * antes da venda, mesmo que o valor recebido na venda seja maior que o valor investido.
     * 
     * <p>Exemplo:
     * <ul>
     *   <li>Compra: 100 ações por R$ 1.000,00 → preço médio = R$ 10,00</li>
     *   <li>Venda: 50 ações por R$ 1.000,00 (preço unitário = R$ 20,00)</li>
     *   <li>Resultado: Preço médio permanece R$ 10,00 (NÃO vira R$ 20,00)</li>
     * </ul>
     * 
     * <p>O valor recebido na venda (parâmetro 'preco') é usado apenas para calcular
     * o capital resgatado e eventual lucro/prejuízo, mas não afeta o preço médio
     * de aquisição das ações remanescentes.
     * 
     * @param quantidade Quantidade de ações vendidas
     */
    public void contabilizarVenda(int quantidade) {
        // O preço médio não muda quando há venda, pois representa o custo médio
        // de aquisição de todas as ações compradas (padrão do mercado brasileiro).
        // O valor recebido na venda não afeta o preço médio de aquisição.
        this.quantidadeVendida += quantidade;
        quantidadeAtualNaCarteira -= quantidade;
    }
    
    /**
     * Retorna a quantidade atual de ações na carteira.
     * Se houver mais vendas que compras, retorna 0 (não permite saldo negativo).
     * 
     * @return Quantidade comprada menos quantidade vendida (mínimo 0)
     */
    public int getQuantidadeAtualNaCarteira() {
        return Math.max(0, quantidadeAtualNaCarteira);
    }
}

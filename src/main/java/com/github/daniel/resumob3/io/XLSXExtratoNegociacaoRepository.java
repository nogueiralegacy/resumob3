package com.github.daniel.resumob3.io;

import com.github.daniel.resumob3.domain.ExtratoNegociacao;
import com.github.daniel.resumob3.domain.Negociacao;
import com.github.daniel.resumob3.domain.ResumoExtratoNegociacao;
import com.github.daniel.resumob3.domain.TipoDeMovimentacao;
import com.github.daniel.resumob3.repository.ExtratoNegociacaoRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.github.daniel.resumob3.utils.Utils.convertStringToDate;

@Setter
@Slf4j
@Repository
public class XLSXExtratoNegociacaoRepository implements ExtratoNegociacaoRepository {
   private final InputStream input;

    public XLSXExtratoNegociacaoRepository(InputStream input) {
       this.input = input;
   }

    @Override
    public ExtratoNegociacao load() {
        List<Negociacao> todas = new ArrayList<>();
        try {
            // create an XSSF Workbook object for our XLSX Execel file
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            // get the first sheet
            int SHEET_INDEX = 0;
            XSSFSheet firstSheet = workbook.getSheetAt(SHEET_INDEX);
            // iterator on rows, sheet is a collection of rows
            Iterator<Row> rowIterator = firstSheet.iterator();

            // Primeira linha é cabeçalho (removida)
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.iterator();

                String dataDoNegocioString = cellIterator.next().getStringCellValue();
                Date dataDoNegocio = convertStringToDate(dataDoNegocioString);
                TipoDeMovimentacao tipoDeMovimentacao = TipoDeMovimentacao.valueOf(
                                cellIterator.next().getStringCellValue().toUpperCase()
                );
                String mercado =  cellIterator.next().getStringCellValue();
                String vencimentoString = cellIterator.next().getStringCellValue();
                // Negócio pode não ter vencimento.
                // Representação de não ter vencimento = "-"
                Date vencimento = vencimentoString.equals("-") ?
                        null : convertStringToDate(vencimentoString);
                String instituicao = cellIterator.next().getStringCellValue();
                String codigoDeNegociacao = cellIterator.next().getStringCellValue();
                double quantidade = cellIterator.next().getNumericCellValue();
                double preco = cellIterator.next().getNumericCellValue();
                double valor = cellIterator.next().getNumericCellValue();

                todas.add(Negociacao.builder()
                        .dataDoNegocio(dataDoNegocio)
                        .tipoDeMovimentacao(tipoDeMovimentacao)
                        .mercado(mercado)
                        .vencimento(vencimento)
                        .instituicao(instituicao)
                        .codigoDeNegociacao(codigoDeNegociacao)
                        .quantidade((int)quantidade)
                        .preco(preco)
                        .valor(valor)
                        .build()
                );

            }

        } catch (ParseException | IOException e) {
            log.error("Erro ao ler arquivo XLSX: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return new ExtratoNegociacao(todas);
    }

    @Override
    public Path exportar(ResumoExtratoNegociacao resumoExtratoNegociacao, String diretorio) throws IOException {
        return null;
    }
}

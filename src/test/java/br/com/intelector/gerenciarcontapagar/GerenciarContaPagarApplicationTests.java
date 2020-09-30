package br.com.intelector.gerenciarcontapagar;

import br.com.intelector.gerenciarcontapagar.service.ArquivoService;
import br.com.intelector.gerenciarcontapagar.service.GastoService;
import br.com.intelector.gerenciarcontapagar.service.LancamentoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class GerenciarContaPagarApplicationTests {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private GastoService gastoService;

    @Test
    void importarArquivo() {
        try {
            arquivoService.processarArquivo();
        } catch (Exception e) {
            fail("Erro ao importar arquivo", e);
        }
    }

    @Test
    void reconstruirHashAndDataCompra() {
        try {
            lancamentoService.reconstruirHashAndDataCompra();
        } catch (Exception e) {
            fail("Erro ao reconstruir hash e data de compra", e);
        }
    }

    @Test
    void consolidarGasto() {
        final String periodo = "2020-08";
        try {
            gastoService.consolidarGasto(periodo);
        } catch (Exception e) {
            fail("Ocorreu um erro ao consolidar gastos", e);
        }
    }

}

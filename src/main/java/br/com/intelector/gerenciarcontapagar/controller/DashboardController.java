package br.com.intelector.gerenciarcontapagar.controller;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.*;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.domain.DominioCategoriaContaRecorrente;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.exception.RegistroInexistenteException;
import br.com.intelector.gerenciarcontapagar.service.ContaRecorrenteService;
import br.com.intelector.gerenciarcontapagar.service.GastoService;
import br.com.intelector.gerenciarcontapagar.service.LembreteService;
import br.com.intelector.gerenciarcontapagar.service.ReceitaService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final GastoService gastoService;
    private final LembreteService lembreteService;
    private final ContaRecorrenteService contaRecorrenteService;
    private final ReceitaService receitaService;

    public DashboardController(GastoService gastoService,
                               LembreteService lembreteService,
                               ContaRecorrenteService contaRecorrenteService,
                               ReceitaService receitaService) {
        this.gastoService = gastoService;
        this.lembreteService = lembreteService;
        this.contaRecorrenteService = contaRecorrenteService;
        this.receitaService = receitaService;
    }

    @GetMapping(value = "/gastos/consolidado/{responsavel}")
    public ResponseEntity<Response<List<GastoPeriodoResponse>>> listGastoByResponsavel(@PathVariable(value = "responsavel") DominioResponsavel responsavel) {
        Response<List<GastoPeriodoResponse>> response = new Response<>();
        List<GastoPeriodoResponse> gastoPeriodoResponseList;
        gastoPeriodoResponseList = gastoService.listByResponsavel(responsavel);
        response.setData(gastoPeriodoResponseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/resumoCartao/{responsavel}/{cartao}")
    public ResponseEntity<Response<ResumoCartaoResponse>> resumoCartao(@PathVariable(value = "responsavel") DominioResponsavel responsavel,
                                                                       @PathVariable(value = "cartao") DominioCartao cartao) {
        Response<ResumoCartaoResponse> response = new Response<>();
        ResumoCartaoResponse resumoCartaoResponse;
        resumoCartaoResponse = gastoService.resumoCartao(cartao, responsavel);
        response.setData(resumoCartaoResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/resumoLembrete/{responsavel}/{processado}")
    public ResponseEntity<Response<ResumoLembreteResponse>> resumoLembrete(@PathVariable(value = "responsavel") DominioResponsavel responsavel,
                                                                           @PathVariable(value = "processado") String processado) {
        Response<ResumoLembreteResponse> response = new Response<>();
        ResumoLembreteResponse resumoLembreteResponse;
        resumoLembreteResponse = lembreteService.resumoLembrete(responsavel, BooleanUtils.toBoolean(processado));
        response.setData(resumoLembreteResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/resumoDespesa/{responsavel}/{categoria}")
    public ResponseEntity<Response<ResumoDespesaResponse>> resumoLembrete(@PathVariable(value = "responsavel") DominioResponsavel responsavel,
                                                                          @PathVariable(value = "categoria") DominioCategoriaContaRecorrente categoriaContaRecorrente) {
        Response<ResumoDespesaResponse> response = new Response<>();
        ResumoDespesaResponse resumoDespesaResponse;
        resumoDespesaResponse = contaRecorrenteService.resumoContaRecorrente(responsavel, categoriaContaRecorrente);
        response.setData(resumoDespesaResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/totalGasto/{responsavel}")
    public ResponseEntity<Response<GastoTotalResponse>> totalGasto(@PathVariable(value = "responsavel") DominioResponsavel responsavel) {
        Response<GastoTotalResponse> response = new Response<>();
        GastoTotalResponse gastoTotalResponse;
        gastoTotalResponse = gastoService.totalGasto(responsavel);
        response.setData(gastoTotalResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/receita/{responsavel}")
    public ResponseEntity<Response<ReceitaResponse>> receita(@PathVariable(value = "responsavel") DominioResponsavel responsavel) {
        Response<ReceitaResponse> response = new Response<>();
        ReceitaResponse receitaResponse;
        try {
            receitaResponse = receitaService.search(responsavel);
            response.setData(receitaResponse);
        } catch (RegistroInexistenteException e) {
            response.getErrors().add("Receita não encontrada para "+responsavel.getDescription());
        }
        return ResponseEntity.ok(response);
    }

}

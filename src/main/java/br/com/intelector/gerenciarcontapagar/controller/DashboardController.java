package br.com.intelector.gerenciarcontapagar.controller;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.GastoPeriodoResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.Response;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResumoCartaoResponse;
import br.com.intelector.gerenciarcontapagar.domain.DominioCartao;
import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import br.com.intelector.gerenciarcontapagar.service.GastoService;
import br.com.intelector.gerenciarcontapagar.service.LancamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final GastoService gastoService;
    private final LancamentoService lancamentoService;

    public DashboardController(GastoService gastoService,
                               LancamentoService lancamentoService) {
        this.gastoService = gastoService;
        this.lancamentoService = lancamentoService;
    }

    @GetMapping(value = "/gastos/consolidado/{responsavel}")
    public ResponseEntity<Response<List<GastoPeriodoResponse>>> listGastoByResponsavel(@PathVariable(value = "responsavel") DominioResponsavel responsavel) {
        Response<List<GastoPeriodoResponse>> response = new Response<>();
        List<GastoPeriodoResponse> gastoPeriodoResponseList;
        gastoPeriodoResponseList = gastoService.listByResponsavel(responsavel);
        response.setData(gastoPeriodoResponseList);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/resumoCartao/{cartao}/{responsavel}")
    public ResponseEntity<Response<ResumoCartaoResponse>> resumoCartaoByTipoAndResponsavel(@PathVariable(value = "cartao") DominioCartao cartao,
                                                                                           @PathVariable(value = "responsavel") DominioResponsavel responsavel) {
        Response<ResumoCartaoResponse> response = new Response<>();
        ResumoCartaoResponse resumoCartaoResponse;
        resumoCartaoResponse = gastoService.resumoCartaoByTipoAndResponsavel(cartao, responsavel);
        response.setData(resumoCartaoResponse);
        return ResponseEntity.ok(response);
    }


}

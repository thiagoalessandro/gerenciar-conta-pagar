package br.com.intelector.gerenciarcontapagar.controller;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.LancamentoResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.Response;
import br.com.intelector.gerenciarcontapagar.service.LancamentoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoService lancamentoService;

    public LancamentoController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @GetMapping(value = "/{page}/{size}/{search}")
    public ResponseEntity<Response<Page<LancamentoResponse>>> listBySearch(@PathVariable(value = "search") String search,
                                                                           @PathVariable(value = "page") Integer page,
                                                                           @PathVariable(value = "size") Integer size
    ) {
        Response<Page<LancamentoResponse>> response = new Response<>();
        Page<LancamentoResponse> lancamentoResponse;
        lancamentoResponse = lancamentoService.search(search, page, size);
        response.setData(lancamentoResponse);
        return ResponseEntity.ok(response);
    }

}

package br.com.intelector.gerenciarcontapagar.controller;

import br.com.intelector.gerenciarcontapagar.controller.dto.response.ResponsavelResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.Response;
import br.com.intelector.gerenciarcontapagar.service.ResponsavelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/responsaveis")
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    public ResponsavelController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @GetMapping
    public ResponseEntity<Response<List<ResponsavelResponse>>> listBySearch() {
        Response<List<ResponsavelResponse>> response = new Response<>();
        List<ResponsavelResponse> responsavelResponseList;
        responsavelResponseList = responsavelService.list();
        response.setData(responsavelResponseList);
        return ResponseEntity.ok(response);
    }


}

package br.com.intelector.gerenciarcontapagar.controller;

import br.com.intelector.gerenciarcontapagar.controller.dto.request.LembreteRequest;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.LembreteResponse;
import br.com.intelector.gerenciarcontapagar.controller.dto.response.Response;
import br.com.intelector.gerenciarcontapagar.exception.RegistroInexistenteException;
import br.com.intelector.gerenciarcontapagar.service.LembreteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/lembretes")
public class LembreteController {

    private final LembreteService lembreteService;

    public LembreteController(LembreteService lembreteService) {
        this.lembreteService = lembreteService;
    }

    @PostMapping
    public ResponseEntity<Response<LembreteResponse>> register(@RequestBody LembreteRequest lembreteRequest) {
        Response<LembreteResponse> response = new Response<>();
        LembreteResponse lembreteResponse = lembreteService.register(lembreteRequest);
        response.setData(lembreteResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Response<LembreteResponse>> update(@RequestBody LembreteRequest lembreteRequest) {
        Response<LembreteResponse> response = new Response<>();
        LembreteResponse lembreteResponse;
        try {
            lembreteResponse = lembreteService.update(lembreteRequest);
            response.setData(lembreteResponse);
        } catch (RegistroInexistenteException e) {
            response.getErrors().add("Registro inexistente");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<String>> findById(@PathVariable("id") Long id) {
        Response<String> response = new Response<>();
        try {
            lembreteService.delete(id);
            response.setData("Excluído com sucesso");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.getErrors().add("Ocorreu um erro ao recuperar registro");
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Response<String> response = new Response<>();
        try {
            lembreteService.delete(id);
            response.setData("Excluído com sucesso");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.getErrors().add("Ocorreu um erro ao excluir");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping(value = "/{page}/{size}/{search}")
    public ResponseEntity<Response<Page<LembreteResponse>>> listBySearch(@PathVariable(value = "search") String search,
                                                                         @PathVariable(value = "page") Integer page,
                                                                         @PathVariable(value = "size") Integer size
    ) {
        Response<Page<LembreteResponse>> response = new Response<>();
        Page<LembreteResponse> lembreteResponseList;
        lembreteResponseList = lembreteService.list(search, page, size);
        response.setData(lembreteResponseList);
        return ResponseEntity.ok(response);
    }


}

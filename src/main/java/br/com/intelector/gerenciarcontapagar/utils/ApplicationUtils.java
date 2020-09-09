package br.com.intelector.gerenciarcontapagar.utils;

import br.com.intelector.gerenciarcontapagar.domain.DominioResponsavel;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;

@Component
public class ApplicationUtils {

    private final ServletContext context;

    public ApplicationUtils(ServletContext context) {
        this.context = context;
    }

    public String linkApp() {
        StringBuilder link = new StringBuilder();
        ServletUriComponentsBuilder currentRequestUri = ServletUriComponentsBuilder.fromCurrentRequestUri();
        String[] path = currentRequestUri.toUriString().split(context.getContextPath());
        link.append(path[0]);
        link.append(context.getContextPath());
        return link.toString();
    }

    public String urlImagemResponsavel(DominioResponsavel responsavel) {
        StringBuilder urlImgResponsavel = new StringBuilder(linkApp());
        urlImgResponsavel.append("images/");
        urlImgResponsavel.append("icons/");
        urlImgResponsavel.append(responsavel.getDescription().toLowerCase());
        urlImgResponsavel.append(".png");
        return urlImgResponsavel.toString();
    }


}

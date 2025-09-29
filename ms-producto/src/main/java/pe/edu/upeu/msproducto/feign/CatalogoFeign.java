package pe.edu.upeu.msproducto.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upeu.msproducto.dto.CatagoriaDto;

import java.util.Optional;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    public CatagoriaDto buscarPorId(@PathVariable Integer id);
}

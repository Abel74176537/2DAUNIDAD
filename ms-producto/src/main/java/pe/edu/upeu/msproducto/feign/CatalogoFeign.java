package pe.edu.upeu.msproducto.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.upeu.msproducto.dto.CatagoriaDto;

import java.util.Optional;

@FeignClient(name = "ms-catalogo", path = "/categoria")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "categoriaListarPorIdCB", fallbackMethod = "fallbackCategoria")
    public CatagoriaDto buscarPorId(@PathVariable Integer id);

    default CatagoriaDto fallbackCategoria (Integer id, Exception e) {
        CatagoriaDto categoriaDto = new CatagoriaDto();
        categoriaDto.setId(9000000);
        categoriaDto.setNombre("Servicio Invalido");
        return categoriaDto;
    }
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "categoriaEliminarPorIdCB", fallbackMethod = "fallbackEliminarCategoria")
    public void eliminar(@PathVariable Integer id);

    default void fallbackEliminarCategoria(Integer id, Exception e) {
        System.out.println("No se pudo eliminar la categoría con id " + id + ". Servicio temporalmente no disponible.");
        // aquí podrías registrar en logs o manejar la lógica alternativa
    }




}

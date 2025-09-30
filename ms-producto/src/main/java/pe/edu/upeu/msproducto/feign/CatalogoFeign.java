package pe.edu.upeu.msproducto.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


    @PostMapping
    @CircuitBreaker(name = "categoriaGuardarCB", fallbackMethod = "fallbackGuardarCategoria")
    ResponseEntity<CatagoriaDto> guardar(@RequestBody CatagoriaDto categoria);

    default ResponseEntity<CatagoriaDto> fallbackGuardarCategoria(CatagoriaDto categoria, Exception e) {
        CatagoriaDto fallbackCat = new CatagoriaDto();
        fallbackCat.setId(999999);
        fallbackCat.setNombre("Servicio temporalmente no disponible");
        fallbackCat.setDescripcion("No se pudo guardar la categoría");
        return ResponseEntity.status(503).body(fallbackCat);
    }





}

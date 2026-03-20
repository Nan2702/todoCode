package com.todocode.PruebaTecSupermercado.controller;

import com.todocode.PruebaTecSupermercado.dto.VentaDTO;
import com.todocode.PruebaTecSupermercado.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> traerVentas(){
        return ResponseEntity.ok(ventaService.trearVentas());
    }

    /*
    Crea una venta usando directamente VentaDTO en la request (opción simple, sin request separada)
    se espera que el dto traiga la información
     */
    @PostMapping
    public ResponseEntity<VentaDTO> create(@RequestBody VentaDTO dto){
         VentaDTO created = ventaService.crearVentas(dto);
         return ResponseEntity.created(URI.create(("/api/ventas/" + created.getId()))).body(created);
    }

    @PutMapping("/{id}")
    public VentaDTO actualizar(@PathVariable Long id, @RequestBody VentaDTO dto){
        //Actualizar fecha, estado, idSucursal, total y remplazar el detalle
        return ventaService.actualizarVenta(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }


}

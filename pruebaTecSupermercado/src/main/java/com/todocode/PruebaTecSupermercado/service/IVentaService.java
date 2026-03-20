package com.todocode.PruebaTecSupermercado.service;

import com.todocode.PruebaTecSupermercado.dto.VentaDTO;

import java.util.List;

public interface IVentaService {
    List<VentaDTO> trearVentas();
    VentaDTO crearVentas(VentaDTO ventaDTO);
    VentaDTO actualizarVenta (Long id, VentaDTO ventaDTO);
    void  eliminarVenta(Long id);
}

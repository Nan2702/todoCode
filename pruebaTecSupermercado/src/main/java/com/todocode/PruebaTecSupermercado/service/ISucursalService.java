package com.todocode.PruebaTecSupermercado.service;

import com.todocode.PruebaTecSupermercado.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {
    List<SucursalDTO> traerSucursales();
    SucursalDTO crearSucursal (SucursalDTO sucursalDto);
    SucursalDTO actualizarSucursal (Long id, SucursalDTO sucursalDTO);
    void eliminarSucursal(Long id);

}

package com.todocode.PruebaTecSupermercado.service;

import com.todocode.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.todocode.PruebaTecSupermercado.dto.VentaDTO;
import com.todocode.PruebaTecSupermercado.exception.NotFoundException;
import com.todocode.PruebaTecSupermercado.mapper.Mapper;
import com.todocode.PruebaTecSupermercado.model.DetalleVenta;
import com.todocode.PruebaTecSupermercado.model.Producto;
import com.todocode.PruebaTecSupermercado.model.Sucursal;
import com.todocode.PruebaTecSupermercado.model.Venta;
import com.todocode.PruebaTecSupermercado.repository.ProductoRepository;
import com.todocode.PruebaTecSupermercado.repository.SucursalRepository;
import com.todocode.PruebaTecSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService{

    @Autowired
    private VentaRepository ventaRepo;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private SucursalRepository sucursalRepo;


    @Override
    public List<VentaDTO> trearVentas() {

        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();

        VentaDTO dto;
        for (Venta v : ventas){
            dto = Mapper.toDTO(v);
            ventasDto.add(dto);
        }
        return ventasDto;
    }

    @Override
    public VentaDTO crearVentas(VentaDTO ventaDto) {

        if (ventaDto == null) throw new RuntimeException("Venta DTO es null");
        if (ventaDto.getIdSucursal() == null) throw new RuntimeException("Debe indicar la sucursal");
        if (ventaDto.getDetalle() == null || ventaDto.getDetalle().isEmpty())
            throw new RuntimeException("Debe contener al menos un producto");

        //Buscar la Sucursal
        Sucursal suc = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);
        if (suc == null) {
            throw new NotFoundException("Sucursal no encontrada");
        }

        //Crear la venta
        Venta vent = new Venta();
        vent.setFecha(ventaDto.getFecha());
        vent.setEstado(ventaDto.getEstado());
        vent.setSucursal(suc);
        vent.setTotal(ventaDto.getTotal());

        //La lista de detalles
        //--> acá están los productos
        List<DetalleVenta> detalles = new ArrayList<>();
        Double totalCalculado = 0.0;

        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()) {
            //Buscar producto por ID (tu detDTO usa id como id de producto)
            Producto p = productoRepo.findByNombre(detDTO.getNombreProd()).orElse(null);
                    if (p == null){
                        throw new RuntimeException("Producto no encontrado: " + detDTO.getNombreProd());}

        //Crear detalle
            DetalleVenta detalleVent = new DetalleVenta();
                    detalleVent.setProd(p);
                    detalleVent.setPrecio(detDTO.getPrecio());
                    detalleVent.setCantProd(detDTO.getCantProd());
                    detalleVent.setVenta(vent);

                    detalles.add(detalleVent);
                    totalCalculado = totalCalculado+(detDTO.getPrecio()*detDTO.getCantProd());

        }
        //Seteamos la lista de detalle venta
        vent.setDetalle(detalles);

        //Guardamos en la BD
        vent = ventaRepo.save(vent);

        //Mapeo de salida
        VentaDTO ventaSalida = Mapper.toDTO(vent);

        return ventaSalida;

    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        //Buscar si la venta existe para actualizarla
        Venta v = ventaRepo.findById(id).orElse(null);
        if (v == null) throw new RuntimeException("Venta no encontrada");

        if (ventaDTO.getFecha()!=null){
            v.setFecha(ventaDTO.getFecha());

        }
        if (ventaDTO.getEstado()!=null){
            v.setEstado(ventaDTO.getEstado());
        }
        if (ventaDTO.getTotal()!=null){
            v.setTotal(ventaDTO.getTotal());
        }
        if (ventaDTO.getIdSucursal()!=null){
            Sucursal suc = sucursalRepo.findById(ventaDTO.getIdSucursal()).orElse(null);
            if (suc == null) throw new NotFoundException("Sucursal no encontrada");
            v.setSucursal(suc);
        }
        ventaRepo.save(v);

        VentaDTO ventaSalida = Mapper.toDTO(v);
        return ventaSalida;
    }

    @Override
    public void eliminarVenta(Long id) {

        Venta v = ventaRepo.findById(id).orElse(null);
        if (v == null) throw new RuntimeException("Venta no encontrada");
        ventaRepo.delete(v);


    }
}

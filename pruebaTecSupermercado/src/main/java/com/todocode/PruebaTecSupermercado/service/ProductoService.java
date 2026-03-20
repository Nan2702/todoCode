package com.todocode.PruebaTecSupermercado.service;

import com.todocode.PruebaTecSupermercado.dto.ProductoDTO;
import com.todocode.PruebaTecSupermercado.exception.NotFoundException;
import com.todocode.PruebaTecSupermercado.mapper.Mapper;
import com.todocode.PruebaTecSupermercado.model.Producto;
import com.todocode.PruebaTecSupermercado.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        var prod = Producto.builder()
                .nombre(productoDTO.getNombre())
                .categoria(productoDTO.getCategoria())
                .precio(productoDTO.getPrecio())
                .cantidad(productoDTO.getCantidad())
                .build();
        return Mapper.toDTO(repo.save(prod));

    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDTO) {
        //Vamos a buscar si existe este producto
        Producto prod = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Producto NO encontrado"));

        prod.setNombre(productoDTO.getNombre());
        prod.setCategoria(productoDTO.getCategoria());
        prod.setCantidad(productoDTO.getCantidad());
        prod.setPrecio(productoDTO.getPrecio());

        return Mapper.toDTO(repo.save(prod));

    }

    @Override
    public void eliminarProducto(Long id) {
        if (! repo.existsById(id)){
            throw new NotFoundException("Producto no encontrado para eliminar");
        }
        repo.deleteById(id);

    }
}

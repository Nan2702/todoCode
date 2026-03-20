package com.todocode.PruebaTecSupermercado.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;

    //datos de la sucursal
    private Long idSucursal;
    //lista de detalles
    private List<DetalleVentaDTO> detalle;

    private Double total;
}

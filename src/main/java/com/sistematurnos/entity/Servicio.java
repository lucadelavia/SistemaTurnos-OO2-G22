package com.sistematurnos.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Servicio {

    private int id;
    private String nombreServicio;
    private int duracion;

}

package org.example.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "animal")
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String especie;

    private Integer edad;

    private String descripcionPerdida;

    private String estado;
}

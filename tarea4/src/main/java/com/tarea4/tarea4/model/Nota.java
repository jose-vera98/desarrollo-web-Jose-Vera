package com.tarea4.tarea4.model;

import jakarta.persistence.*;

@Entity
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int valor;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

	// Constructor vacio
    public Nota() {}

	// Constructor con parametros
    public Nota(int valor, Actividad actividad) {
        this.valor = valor;
        this.actividad = actividad;
    }

	// Getters y setters
    public Long getId() { return id; }
    public int getValor() { return valor; }
    public Actividad getActividad() { return actividad; }

    public void setId(Long id) { this.id = id; }
    public void setValor(int valor) { this.valor = valor; }
    public void setActividad(Actividad actividad) { this.actividad = actividad; }
}

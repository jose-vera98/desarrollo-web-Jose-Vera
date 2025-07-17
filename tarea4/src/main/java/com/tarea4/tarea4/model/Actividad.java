package com.tarea4.tarea4.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	// Atributos
	private LocalDate fechaInicio;
    private String sector;
	private String nombre;
    private String tema;

	// Constructor vacio
    public Actividad() {}

	// Constructor que recibe los parametros
    public Actividad(LocalDate fechaInicio, String sector, String nombre, String tema) {
        this.fechaInicio = fechaInicio;
		this.sector = sector;
		this.nombre = nombre;
		this.tema = tema;
    }

    // Getters y setters

    public Long getId() { return id; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public String getSector() { return sector; }
    public String getNombre() { return nombre; }
    public String getTema() { return tema; }

    public void setId(Long id) { this.id = id; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setSector(String sector) { this.sector = sector; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTema(String tema) { this.tema = tema; }
}

package com.tarea4.tarea4.model;

import jakarta.persistence.*;

@Entity
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    public Foto() {}

    public Foto(String nombreArchivo, Actividad actividad) {
        this.nombreArchivo = nombreArchivo;
        this.actividad = actividad;
    }

    public Long getId() { return id; }

    public String getNombreArchivo() { return nombreArchivo; }
    public Actividad getActividad() { return actividad; }

	
    public void setActividad(Actividad actividad) { this.actividad = actividad; }
	public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
}

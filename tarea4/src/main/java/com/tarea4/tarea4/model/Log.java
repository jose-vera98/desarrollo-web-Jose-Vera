package com.tarea4.tarea4.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private String mensaje;

    public Log() {}

    public Log(String mensaje) {
        this.fecha = LocalDateTime.now();
        this.mensaje = mensaje;
    }

    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public String getMensaje() { return mensaje; }

    public void setId(Long id) { this.id = id; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

package com.tarea4.tarea4.controller;

import com.tarea4.tarea4.model.Actividad;
import com.tarea4.tarea4.model.Nota;
import com.tarea4.tarea4.repository.ActividadRepository;
import com.tarea4.tarea4.repository.NotaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
public class EvaluacionController {

    private final ActividadRepository actividadRepository;
    private final NotaRepository notaRepository;

    public EvaluacionController(ActividadRepository actividadRepository, NotaRepository notaRepository) {
        this.actividadRepository = actividadRepository;
        this.notaRepository = notaRepository;
    }

    @PostMapping("/evaluar/{id}")
    public ResponseEntity<String> evaluarActividad(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        Optional<Actividad> optActividad = actividadRepository.findById(id);

        if (optActividad.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int notaValor = body.getOrDefault("nota", -1);
        if (notaValor < 1 || notaValor > 7) {
            return ResponseEntity.badRequest().body("Nota inválida");
        }

        Nota nota = new Nota(notaValor, optActividad.get());
        notaRepository.save(nota);

        return ResponseEntity.ok("Nota registrada");
    }
}

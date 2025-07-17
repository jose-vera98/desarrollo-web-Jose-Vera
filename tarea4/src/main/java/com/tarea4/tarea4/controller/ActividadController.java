package com.tarea4.tarea4.controller;

import com.tarea4.tarea4.model.Actividad;
import com.tarea4.tarea4.model.Nota;
import com.tarea4.tarea4.repository.ActividadRepository;
import com.tarea4.tarea4.repository.NotaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class ActividadController {

    private final ActividadRepository actividadRepository;
    private final NotaRepository notaRepository;

    public ActividadController(ActividadRepository actividadRepository, NotaRepository notaRepository) {
        this.actividadRepository = actividadRepository;
        this.notaRepository = notaRepository;
    }

    @GetMapping("/actividades")
    public String mostrarActividades(Model model) {
        List<Actividad> actividades = actividadRepository.findAll();

        Map<Long, Float> promedios = new HashMap<>();
        for (Actividad act : actividades) {
            List<Nota> notas = notaRepository.findByActividadId(act.getId());
            if (!notas.isEmpty()) {
                double avg = notas.stream().mapToInt(Nota::getValor).average().orElse(0);
                promedios.put(act.getId(), (float) avg);
            }
        }

        model.addAttribute("actividades", actividades);
        model.addAttribute("promedios", promedios);

        return "actividades";
    }
}

package com.tarea4.tarea4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tarea4.tarea4.model.Actividad;
import com.tarea4.tarea4.model.Nota;
import com.tarea4.tarea4.repository.ActividadRepository;
import com.tarea4.tarea4.repository.NotaRepository;

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
                double avg = notas.stream()
                                  .mapToInt(Nota::getValor)
                                  .average()
                                  .orElse(0);

                // Redondeo a 1 decimal
                float rounded = Math.round(avg * 10f) / 10f;

                promedios.put(act.getId(), rounded);
            }
        }

        model.addAttribute("actividades", actividades);
        model.addAttribute("promedios", promedios);

        return "actividades";
    }

    @GetMapping("/")
    public String redirigirAActividades() {
        return "redirect:/actividades";
    }
}


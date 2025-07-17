package com.tarea4.tarea4.config;

import com.tarea4.tarea4.model.Actividad;
import com.tarea4.tarea4.model.Nota;
import com.tarea4.tarea4.repository.ActividadRepository;
import com.tarea4.tarea4.repository.NotaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ActividadRepository actividadRepository;
    private final NotaRepository notaRepository;

    public DataLoader(ActividadRepository actividadRepository, NotaRepository notaRepository) {
        this.actividadRepository = actividadRepository;
        this.notaRepository = notaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (actividadRepository.count() == 0) {
            Actividad a1 = new Actividad(LocalDate.of(2025, 6, 1), "Plaza Central", "Clase de Yoga", "salud");
            Actividad a2 = new Actividad(LocalDate.of(2025, 6, 10), "Cancha Norte", "Partido amistoso", "deporte");
            Actividad a3 = new Actividad(LocalDate.of(2025, 6, 5), "Parque Cultural", "Taller de pintura", "arte");

            actividadRepository.saveAll(List.of(a1, a2, a3));

            notaRepository.saveAll(List.of(
                new Nota(5, a1),
                new Nota(6, a1),
                new Nota(7, a1),
                new Nota(4, a2),
                new Nota(6, a2)
            ));

            System.out.println("Datos de prueba cargados.");
        } else {
            System.out.println("Datos ya existentes. No se cargó nada nuevo.");
        }
    }
}

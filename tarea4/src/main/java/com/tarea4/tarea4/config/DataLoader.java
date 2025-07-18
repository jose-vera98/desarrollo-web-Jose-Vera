package com.tarea4.tarea4.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tarea4.tarea4.model.Actividad;
import com.tarea4.tarea4.model.Foto;
import com.tarea4.tarea4.model.Nota;
import com.tarea4.tarea4.repository.ActividadRepository;
import com.tarea4.tarea4.repository.FotoRepository;
import com.tarea4.tarea4.repository.NotaRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ActividadRepository actividadRepository;
    private final NotaRepository notaRepository;
    private final FotoRepository fotoRepository;

    public DataLoader(ActividadRepository actividadRepository, NotaRepository notaRepository, FotoRepository fotoRepository) {
        this.actividadRepository = actividadRepository;
        this.notaRepository = notaRepository;
        this.fotoRepository = fotoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (actividadRepository.count() == 0) {
            Actividad a1 = new Actividad(LocalDate.of(2025, 6, 1), "Sala aerobica -3", "Clase de Zumba", "salud y actividad fisica", "contacto@adefa.cl");
            Actividad a2 = new Actividad(LocalDate.of(2025, 6, 10), "Cancha 851", "Pichangas DCC", "deporte", "deportes@cdanakena.cl");
            Actividad a3 = new Actividad(LocalDate.of(2025, 9, 1), "Polideportivo JGM", "Semana Olimpica", "deporte", "cde@uchile.cl");
            Actividad a4 = new Actividad(LocalDate.of(2025, 6, 5), "Auditorio D'Etigny", "Taller para emprendedores", "Emprendimiento", "redesbeauchef@uchile.cl");


            actividadRepository.saveAll(List.of(a1, a2, a3, a4));

            notaRepository.saveAll(List.of(
                new Nota(5, a1),
                new Nota(6, a1),
                new Nota(7, a1),
                new Nota(4, a2),
                new Nota(6, a2),
                new Nota(4, a3)
            ));

            fotoRepository.saveAll(List.of(
                new Foto("foto1.jpg", a1),
                new Foto("foto3.png", a2),
                new Foto("foto4.jpeg", a1),
                new Foto("foto2.jpg", a4)
            ));

            System.out.println("Datos de prueba cargados.");
        } else {
            System.out.println("Datos ya existentes. No se cargó nada nuevo.");
        }
    }
}

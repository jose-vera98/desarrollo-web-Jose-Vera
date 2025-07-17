package com.tarea4.tarea4.repository;

import com.tarea4.tarea4.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByActividadId(Long actividadId);
}

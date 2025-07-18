package com.tarea4.tarea4.repository;

import com.tarea4.tarea4.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findAllByOrderByIdDesc(); // como los id se van creando de forma incremental, este método devuelve las fotos ordenadas de más reciente a más antigua
}

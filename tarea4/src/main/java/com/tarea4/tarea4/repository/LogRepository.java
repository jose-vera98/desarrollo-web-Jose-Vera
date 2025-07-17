package com.tarea4.tarea4.repository;

import com.tarea4.tarea4.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByOrderByFechaDesc(); // para mostrar los logs del más reciente al más antiguo
}

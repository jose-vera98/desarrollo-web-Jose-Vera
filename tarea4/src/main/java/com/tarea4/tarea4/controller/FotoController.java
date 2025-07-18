package com.tarea4.tarea4.controller;

import com.tarea4.tarea4.model.Foto;
import com.tarea4.tarea4.model.Log;
import com.tarea4.tarea4.repository.FotoRepository;
import com.tarea4.tarea4.repository.LogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class FotoController {

    private final FotoRepository fotoRepository;
    private final LogRepository logRepository;

    public FotoController(FotoRepository fotoRepository, LogRepository logRepository) {
        this.fotoRepository = fotoRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/admin-fotos")
    public String mostrarAdminFotos(Model model) {
        List<Foto> fotos = fotoRepository.findAllByOrderByIdDesc();
        model.addAttribute("fotos", fotos);
        return "admin-fotos";
    }

    @ResponseBody
    @PostMapping("/eliminar-foto/{id}")
    public ResponseEntity<String> eliminarFoto(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<Foto> optFoto = fotoRepository.findById(id);

        if (optFoto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String motivo = body.get("motivo");
        if (motivo == null || motivo.trim().length() < 5 || motivo.length() > 200) {
            return ResponseEntity.badRequest().body("Motivo inválido");
        }

        Foto foto = optFoto.get();
        fotoRepository.delete(foto);

        String mensaje = "La foto " + foto.getId() + " fue eliminada por usuario admin, motivo: " + motivo;
        logRepository.save(new Log(mensaje));

        return ResponseEntity.ok("Foto eliminada");
    }
}

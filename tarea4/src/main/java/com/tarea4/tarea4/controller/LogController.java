package com.tarea4.tarea4.controller;

import com.tarea4.tarea4.model.Log;
import com.tarea4.tarea4.repository.LogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/log")
    public String verLog(Model model) {
        List<Log> logs = logRepository.findAllByOrderByFechaDesc();
        model.addAttribute("logs", logs);
        return "log";
    }
}

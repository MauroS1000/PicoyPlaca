package com.mauro.picoyplaca.controller;

import com.mauro.picoyplaca.dto.ResultadoValidacion;
import com.mauro.picoyplaca.service.PicoPlacaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class PicoPlacaController {

    @Autowired
    private PicoPlacaService picoPlacaService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/validar")
    public ResponseEntity<ResultadoValidacion> validarPicoPlaca(
            @RequestParam String placa,
            @RequestParam String fechaHora) {

        LocalDateTime fechaHoraValidar = parsearFechaHora(fechaHora);
        validarFechaNoPasado(fechaHoraValidar);
        
        return ResponseEntity.ok(
            picoPlacaService.validar(placa, fechaHoraValidar)
        );
    }


    private LocalDateTime parsearFechaHora(String fechaHora) {
        try {
            return LocalDateTime.parse(fechaHora, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha/hora inválido. Use: yyyy-MM-ddTHH:mm");
        }
    }

    private void validarFechaNoPasado(LocalDateTime fechaHora) {
        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha/hora no puede ser anterior al momento actual");
        }
    }
}

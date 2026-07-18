package com.mauro.picoyplaca.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class PicoPlacaServiceTest {

    private final PicoPlacaService service = new PicoPlacaService();

    @Test
    void restringeCirculacionEnInicioDelHorarioManana() {
        var resultado = service.validar("PBC-1231", LocalDateTime.of(2026, 7, 20, 6, 0));

        assertFalse(resultado.isPermitido());
    }

    @Test
    void restringeCirculacionEnFinDelHorarioManana() {
        var resultado = service.validar("PBC-1231", LocalDateTime.of(2026, 7, 20, 9, 30));

        assertFalse(resultado.isPermitido());
    }

    @Test
    void permiteCirculacionEnFinDeSemana() {
        var resultado = service.validar("PBC-1231", LocalDateTime.of(2026, 7, 18, 8, 0));

        assertTrue(resultado.isPermitido());
    }

    @Test
    void rechazaFormatoDePlacaInvalido() {
        assertThrows(IllegalArgumentException.class,
            () -> service.validar("123-ABC", LocalDateTime.of(2026, 7, 20, 8, 0)));
    }
}

package com.mauro.picoyplaca.service;

import com.mauro.picoyplaca.dto.ResultadoValidacion;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.stereotype.Service;

@Service
public class PicoPlacaService {

    public ResultadoValidacion validar(String placa, LocalDateTime fechaHora) {
        validarFormatoPlaca(placa);
        
        DayOfWeek diaSemana = fechaHora.getDayOfWeek();
        LocalTime hora = fechaHora.toLocalTime();

        if (esFinDeSemana(diaSemana)) {
            return new ResultadoValidacion(placa, true, "No hay pico y placa los fines de semana");
        }

        int ultimoDigito = obtenerUltimoDigito(placa);
        int[] digitosProhibidos = obtenerDigitosProhibidos(diaSemana);

        boolean tieneRestriccion = tieneRestriccion(ultimoDigito, digitosProhibidos);
        boolean enHorarioPico = estaEnHorarioPico(hora);

        return generarResultado(placa, diaSemana, ultimoDigito, tieneRestriccion, enHorarioPico);
    }

    private void validarFormatoPlaca(String placa) {
        // Regex: 3 letras (primera es provincia) + guión opcional + 4 dígitos
        String regexProvincias = "[ABCEGHIJKLMNOPQRSTUVWXYZ]"; // Letras válidas para provincia
        String regexPlaca = "^" + regexProvincias + "[A-Z]{2}-?\\d{4}$";
    
        if (!placa.matches(regexPlaca)) {
            throw new IllegalArgumentException(
                "Formato de placa inválido. Debe ser: 3 letras (primera es provincia) + 4 dígitos. Ej: PBC-1234"
            );
        }
    }

    private boolean esFinDeSemana(DayOfWeek dia) {
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }

    private int obtenerUltimoDigito(String placa) {
        // Eliminar guiones para placas como "PBC-1234"
        String placaLimpia = placa.replace("-", "");
        char ultimoDigitoChar = placaLimpia.charAt(placaLimpia.length() - 1);
        return Character.getNumericValue(ultimoDigitoChar);
    }

    private int[] obtenerDigitosProhibidos(DayOfWeek dia) {
        switch (dia) {
            case MONDAY: return new int[]{1, 2};
            case TUESDAY: return new int[]{3, 4};
            case WEDNESDAY: return new int[]{5, 6};
            case THURSDAY: return new int[]{7, 8};
            case FRIDAY: return new int[]{9, 0};
            default: return new int[]{};
        }
    }

    private boolean tieneRestriccion(int ultimoDigito, int[] prohibidos) {
        for (int d : prohibidos) {
            if (d == ultimoDigito) return true;
        }
        return false;
    }

    private boolean estaEnHorarioPico(LocalTime hora) {
        LocalTime inicioManana = LocalTime.of(6, 0);
        LocalTime finManana = LocalTime.of(9, 30);
        LocalTime inicioTarde = LocalTime.of(16, 0);
        LocalTime finTarde = LocalTime.of(20, 30);

        return (hora.isAfter(inicioManana) && hora.isBefore(finManana)) ||
               (hora.isAfter(inicioTarde) && hora.isBefore(finTarde));
    }

    private ResultadoValidacion generarResultado(
        String placa, 
        DayOfWeek dia, 
        int ultimoDigito, 
        boolean tieneRestriccion, 
        boolean enHorarioPico
    ) {
        if (tieneRestriccion && enHorarioPico) {
            String nombreDia = switch (dia) {
                case MONDAY -> "lunes";
                case TUESDAY -> "martes";
                case WEDNESDAY -> "miércoles";
                case THURSDAY -> "jueves";
                case FRIDAY -> "viernes";
                case SATURDAY -> "sábado";
                case SUNDAY -> "domingo";
            };
            
            String mensaje = String.format(
                "NO puedes circular. Hoy es %s y tu placa termina en %d. Horarios restringidos: 06:00-09:30 y 16:00-20:30",
                nombreDia, ultimoDigito
            );
            return new ResultadoValidacion(placa, false, mensaje);
        }
        return new ResultadoValidacion(placa, true, "Puedes circular sin restricciones");
    }
}
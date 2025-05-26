package com.mauro.picoyplaca.dto;

public class ResultadoValidacion {
    private String placa;
    private boolean permitido;
    private String mensaje;

    public ResultadoValidacion(String placa, boolean permitido, String mensaje) {
        this.placa = placa;
        this.permitido = permitido;
        this.mensaje = mensaje;
    }

    // Getters (necesarios para la serialización JSON)
    public String getPlaca() { return placa; }
    public boolean isPermitido() { return permitido; }
    public String getMensaje() { return mensaje; }
}
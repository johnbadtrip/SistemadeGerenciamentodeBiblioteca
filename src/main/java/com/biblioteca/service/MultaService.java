package com.biblioteca.service;

public class MultaService {
    public double calcularMulta(int diasAtraso) {
        double valorPorDia = 5.0;
        return diasAtraso > 0 ? diasAtraso * valorPorDia : 0.0;
    }
}
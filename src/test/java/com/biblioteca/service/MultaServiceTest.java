package com.biblioteca.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MultaServiceTest {
    @Test
    void calculaMultaCorretamente() {
        MultaService multaService = new MultaService();
        double multa = multaService.calcularMulta(5); // 5 dias de atraso
        assertEquals(25.0, multa, 0.01); // Exemplo: R$5 por dia
    }

    @Test
    void naoCalculaMultaSeDevolucaoEmDia() {
        MultaService multaService = new MultaService();
        double multa = multaService.calcularMulta(0); // 0 dias de atraso
        assertEquals(0.0, multa, 0.01);
    }

    @Test
    void calculaMultaProporcionalAoAtraso() {
        MultaService multaService = new MultaService();
        double multa = multaService.calcularMulta(3); // 3 dias de atraso
        assertEquals(15.0, multa, 0.01); // Exemplo: R$5 por dia
    }
}
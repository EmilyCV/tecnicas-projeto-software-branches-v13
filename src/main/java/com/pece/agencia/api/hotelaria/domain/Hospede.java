package com.pece.agencia.api.hotelaria.domain;

import java.time.LocalDate;

public record Hospede(String nome, String email, LocalDate dataNascimento) {
}

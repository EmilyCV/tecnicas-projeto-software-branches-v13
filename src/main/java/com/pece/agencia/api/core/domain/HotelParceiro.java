package com.pece.agencia.api.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "HOTEL_PARCEIRO")
@Data
@EqualsAndHashCode(callSuper = true)
public class HotelParceiro extends Parceiro {
    @ManyToOne
    @JoinColumn(name = "ENDERECO_ID")
    private Endereco endereco;

}


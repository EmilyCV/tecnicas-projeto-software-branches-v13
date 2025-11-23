package com.pece.agencia.api.hotelaria.repository;

import com.pece.agencia.api.hotelaria.domain.OfertaHospedagemPlataformaMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfertaHospedagemPlataformaMappingRepository extends JpaRepository<OfertaHospedagemPlataformaMapping, UUID> {
}
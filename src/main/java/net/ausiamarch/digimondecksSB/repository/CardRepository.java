package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.CardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    
}

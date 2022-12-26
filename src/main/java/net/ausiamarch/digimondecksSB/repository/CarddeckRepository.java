package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.CardDeckEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarddeckRepository extends JpaRepository<CardDeckEntity, Long>{
    
    Page<CardDeckEntity> findByDeckId(Long id_deck, Pageable oPageable);

    Page<CardDeckEntity> findByCardId(Long id_card, Pageable oPageable);

    Page<CardDeckEntity> findByCardIdAndDeckId(Long id_card, Long id_deck, Pageable oPageable);
}

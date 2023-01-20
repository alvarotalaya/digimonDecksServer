package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.DeckEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<DeckEntity, Long>{
    
    Page<DeckEntity> findByPlayerId(Long id_player, Pageable oPageable);

    Page<DeckEntity> findByNameIgnoreCaseContaining(String strFilterName, Pageable oPageable);

    Page<DeckEntity> findByNameIgnoreCaseContainingAndPlayerId(String strFilterName, Long id_player, Pageable oPageable);
}
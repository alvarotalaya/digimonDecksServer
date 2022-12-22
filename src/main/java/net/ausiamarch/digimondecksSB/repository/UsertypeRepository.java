package net.ausiamarch.digimondecksSB.repository;

import net.ausiamarch.digimondecksSB.entity.UsertypeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsertypeRepository extends JpaRepository<UsertypeEntity, Long> {

    boolean existsByType(String type);

    Page<UsertypeEntity> findByType(String type, Pageable oPageable);
}
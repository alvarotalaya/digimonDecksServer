package net.ausiamarch.digimondecksSB.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.entity.DeckEntity;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.DeckRepository;
import net.ausiamarch.digimondecksSB.repository.PlayerRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class DeckService {

    @Autowired
    DeckRepository oDeckRepository;

    @Autowired
    PlayerRepository oPlayerRepository;
    
    @Autowired
    AuthService oAuthService;

    private final List<String> names = List.of("Imperial", "Security Control", "Bloomlord", "Blue Flare", "Armor rush", "Trival Terrier", 
        "Eosmon", "Minerva", "MetalGaruru", "Alphamon", "Xros", "BlackWar");

    public void validate(DeckEntity oDeckEntity) {
        ValidationHelper.validateStringLength(oDeckEntity.getName(), 2, 50, "campo name de Deck(el campo debe tener longitud de 2 a 50 caracteres)");
        ValidationHelper.validateStringLength(oDeckEntity.getDescription(), 0, 100, "campo name de Deck(el campo debe tener longitud de 2 a 50 caracteres)");
    }

    public void validate(Long id) {
        if (!oDeckRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public DeckEntity get(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        return oDeckRepository.getById(id);
    }

    public Page<DeckEntity> getPage(Pageable oPageable, String strFilter, Long player) {
        oAuthService.OnlyAdmins();
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (strFilter == null || strFilter.length() == 0) {
            if (player == null) {
                return oDeckRepository.findAll(oPageable);
            } else {
                return oDeckRepository.findByPlayerId(player, oPageable);
            }
        } else {
            if (player == null) {
                return oDeckRepository.findByNameIgnoreCaseContaining(strFilter, oPageable);
            } else {
                return oDeckRepository.findByNameIgnoreCaseContainingAndPlayerId(strFilter, player, oPageable);
            }
        }
     }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oDeckRepository.count();
    }

    public Long update(DeckEntity oDeckEntity) {
        validate(oDeckEntity.getId());
        oAuthService.OnlyAdmins();
        DeckEntity oOldDeckEntity=oDeckRepository.getById(oDeckEntity.getId());
        oDeckEntity.setPlayer(oOldDeckEntity.getPlayer());
        Date date = new Date();
        oDeckEntity.setLastUpdate(date);
        return oDeckRepository.save(oDeckEntity).getId();
    }

    public Long create(DeckEntity oNewDeckEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewDeckEntity);
        Date date = new Date();
        oNewDeckEntity.setLastUpdate(date);
        oNewDeckEntity.setId(0L);

        return oDeckRepository.save(oNewDeckEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oDeckRepository.deleteById(id);
        if (oDeckRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }

    private DeckEntity generateDeck() {
        DeckEntity oDeckEntity = new DeckEntity();

        oDeckEntity.setName(names.get(RandomHelper.getRandomInt(0, names.size() - 1)));
        oDeckEntity.setDescription(null);
        oDeckEntity.setLastUpdate(RandomHelper.getRadomDate());
        
        int totalPlayers = (int) oPlayerRepository.count();
        int randomUserTypeId = RandomHelper.getRandomInt(1, totalPlayers);
        oPlayerRepository.findById((long) randomUserTypeId)
        .ifPresent(oDeckEntity::setPlayer);

        return oDeckEntity;
    }

    public DeckEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oDeckRepository.save(generateDeck());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<DeckEntity> DeckToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            DeckToSave.add(generateDeck());
        }
        oDeckRepository.saveAll(DeckToSave);
        return oDeckRepository.count();
    }
}

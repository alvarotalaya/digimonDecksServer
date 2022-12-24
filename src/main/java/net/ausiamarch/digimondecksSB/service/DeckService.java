package net.ausiamarch.digimondecksSB.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import net.ausiamarch.digimondecksSB.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.entity.DeckEntity;
import net.ausiamarch.digimondecksSB.exception.CannotPerformOperationException;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.DeckRepository;
import net.ausiamarch.digimondecksSB.repository.PlayerRepository;
import net.ausiamarch.digimondecksSB.repository.UsertypeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class DeckService {

    @Autowired
    DeckRepository oDeckRepository;

    @Autowired
    PlayerRepository oPlayerRepository;
    
    @Autowired
    AuthService oAuthService;

    String DIGIMON_DEFAULT_PASSWORD = "DIGIMON_DECKS";
    private final List<String> names = List.of("Ainhoa", "Kevin", "Estefania", "Cristina",
    "Jose Maria", "Lucas Ezequiel", "Carlos", "Elliot", "Alexis", "Ruben", "Luis Fernando", "Karim", "Luis",
    "Jose David", "Nerea", "Ximo", "Iris", "Alvaro", "Mario", "Raimon");

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
                return oDeckRepository.findByNameIgnoreCase(strFilter, oPageable);
            } else {
                return oDeckRepository.findByNameIgnoreCaseAndPlayerId(strFilter, player, oPageable);
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
        oDeckEntity.setLastUpdate(LocalDateTime.now());
        return oDeckRepository.save(oDeckEntity).getId();
    }

    public Long create(DeckEntity oNewDeckEntity) {
        oAuthService.OnlyAdmins();
        validate(oNewDeckEntity);
        oNewDeckEntity.setLastUpdate(LocalDateTime.now());
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
        oDeckEntity.setLastUpdate(null);
        oDeckEntity.setLastUpdate(LocalDateTime.now());
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

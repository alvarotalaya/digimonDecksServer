package net.ausiamarch.digimondecksSB.service;

import java.util.ArrayList;
import java.util.List;
import net.ausiamarch.digimondecksSB.exception.ResourceNotFoundException;
import net.ausiamarch.digimondecksSB.exception.ResourceNotModifiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiamarch.digimondecksSB.entity.CardDeckEntity;
import net.ausiamarch.digimondecksSB.helper.RandomHelper;
import net.ausiamarch.digimondecksSB.helper.ValidationHelper;
import net.ausiamarch.digimondecksSB.repository.CardRepository;
import net.ausiamarch.digimondecksSB.repository.CarddeckRepository;
import net.ausiamarch.digimondecksSB.repository.DeckRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CarddeckService {

    @Autowired
    CarddeckRepository oCarddeckRepository;

    @Autowired
    CardRepository oCardRepository;

    @Autowired
    DeckRepository oDeckRepository;

    @Autowired
    CardService oCardService;

    @Autowired
    DeckService oDeckService;

    @Autowired
    AuthService oAuthService;
    
    public void validate(CardDeckEntity oCardDeckEntity) {
    }

    public void validate(Long id) {
        if (!oCarddeckRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public CardDeckEntity get(Long id) {
        oAuthService.OnlyAdmins();//?????
        validate(id);
        return oCarddeckRepository.getById(id);
    }

    public Page<CardDeckEntity> getPage(Pageable oPageable, Long card, Long deck) {
        //oAuthService.OnlyAdmins();//????
        ValidationHelper.validateRPP(oPageable.getPageSize());
        if (card == null) {
            if (deck == null) {
                return oCarddeckRepository.findAll(oPageable);
            } else {
                return oCarddeckRepository.findByDeckId(deck, oPageable);
            }
        } else {
            if(card != null){
                if(deck != null){
                    return oCarddeckRepository.findByCardIdAndDeckId(card, deck, oPageable);
                }
            }
            return oCarddeckRepository.findByCardId(card, oPageable);
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oCarddeckRepository.count();
    }

    public Long update(CardDeckEntity oCardDeckEntity) {
        validate(oCardDeckEntity.getId());
        //oAuthService.OnlyAdmins();//?????
        return oCarddeckRepository.save(oCardDeckEntity).getId();
    }

    public Long create(CardDeckEntity oNewCardDeckEntity) {
        oAuthService.OnlyAdmins();//?????
        validate(oNewCardDeckEntity);
        oNewCardDeckEntity.setId(0L);

        return oCarddeckRepository.save(oNewCardDeckEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();//??????
        validate(id);
        oCarddeckRepository.deleteById(id);
        if (oCarddeckRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }

    private CardDeckEntity generateCarddeck() {
        CardDeckEntity oCardDeckEntity = new CardDeckEntity();

        oCardDeckEntity.setCopies(RandomHelper.getRandomInt(1, 4));
        oCardDeckEntity.setDeck(oDeckService.getOneRandom());
        oCardDeckEntity.setCard(oCardService.getOneRandom());

        return oCardDeckEntity;
    }

    public CardDeckEntity generateOne() {
        oAuthService.OnlyAdmins();
        return oCarddeckRepository.save(generateCarddeck());
    }

    public Long generateSome(Long amount) {
        oAuthService.OnlyAdmins();
        List<CardDeckEntity> CarddeckToSave = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            CarddeckToSave.add(generateCarddeck());
        }
        oCarddeckRepository.saveAll(CarddeckToSave);
        return oCarddeckRepository.count();
    }
}

package net.ausiamarch.digimondecksSB.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiamarch.digimondecksSB.entity.CardDeckEntity;
import net.ausiamarch.digimondecksSB.service.CarddeckService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/carddeck")
public class CarddeckController {
    @Autowired
    CarddeckService oCarddeckService;

    @GetMapping("/{id}")
    public ResponseEntity<CardDeckEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CardDeckEntity>(oCarddeckService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CardDeckEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(value = "card", required = false) Long id_card,
            @RequestParam(value = "deck", required = false) Long id_deck) {
        return new ResponseEntity<>(oCarddeckService.getPage(oPageable, id_card, id_deck), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCarddeckService.count(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CardDeckEntity oCardDeckEntity) {
        return new ResponseEntity<Long>(oCarddeckService.update(oCardDeckEntity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CardDeckEntity oNewCardDeckEntity) {
        return new ResponseEntity<Long>(oCarddeckService.create(oNewCardDeckEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCarddeckService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<CardDeckEntity> generateOne() {
        return new ResponseEntity<>(oCarddeckService.generateOne(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable Long amount) {
        return new ResponseEntity<>(oCarddeckService.generateSome(amount), HttpStatus.OK);
    }
}


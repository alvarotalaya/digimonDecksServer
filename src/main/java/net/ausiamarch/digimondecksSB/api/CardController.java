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
import net.ausiamarch.digimondecksSB.entity.CardEntity;
import net.ausiamarch.digimondecksSB.service.CardService;
import net.minidev.json.parser.ParseException;
import java.io.IOException;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService oCardService;

    @GetMapping("/getcards")
    public ResponseEntity<Long> getAllCards() throws IOException, ParseException {
        return new ResponseEntity<Long>(oCardService.getAllCards(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<CardEntity>(oCardService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CardEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter) {
        return new ResponseEntity<>(oCardService.getPage(oPageable, strFilter), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCardService.count(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody CardEntity oCardEntity) {
        return new ResponseEntity<Long>(oCardService.update(oCardEntity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CardEntity oNewCardEntity) {
        return new ResponseEntity<Long>(oCardService.create(oNewCardEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oCardService.delete(id), HttpStatus.OK);
    }

}

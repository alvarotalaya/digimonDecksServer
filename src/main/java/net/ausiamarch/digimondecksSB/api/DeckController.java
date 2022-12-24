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
import net.ausiamarch.digimondecksSB.entity.DeckEntity;
import net.ausiamarch.digimondecksSB.service.DeckService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/deck")
public class DeckController {
    
    @Autowired
    DeckService oDeckService;

    @GetMapping("/{id}")
    public ResponseEntity<DeckEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<DeckEntity>(oDeckService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DeckEntity>> getPage(
            @ParameterObject @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(value = "player", required = false) Long id_player) {
        return new ResponseEntity<>(oDeckService.getPage(oPageable, strFilter, id_player), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oDeckService.count(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody DeckEntity oDeckEntity) {
        return new ResponseEntity<Long>(oDeckService.update(oDeckEntity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DeckEntity oNewDeckEntity) {
        return new ResponseEntity<Long>(oDeckService.create(oNewDeckEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oDeckService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<DeckEntity> generateOne() {
        return new ResponseEntity<>(oDeckService.generateOne(), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable Long amount) {
        return new ResponseEntity<>(oDeckService.generateSome(amount), HttpStatus.OK);
    }
}

package net.ausiamarch.digimondecksSB.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "card")
@JsonIgnoreProperties({ "hibernateLazyInitialize", "handler" })
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String name;
    String type;
    String color;
    String stage;
    String digitype;
    String attribute;
    Long level;
    Long playcost;
    Long evolutioncost;
    Long dp;
    String cardnumber;
    String maineffect;
    String sourceeffect;
    String image;

    @OneToMany(mappedBy = "card",  fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CardDeckEntity> carddecks;

    public CardEntity() {
        this.carddecks = new ArrayList<>();
    }

    public CardEntity(Long id) {
        this.carddecks = new ArrayList<>();
        this.id = id;
    }

    public int getCarddecks() {
        return carddecks.size();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getStage() {
        return stage;
    }
    public void setStage(String stage) {
        this.stage = stage;
    }
    public String getDigitype() {
        return digitype;
    }
    public void setDigitype(String digitype) {
        this.digitype = digitype;
    }
    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public Long getLevel() {
        return level;
    }
    public void setLevel(Long level) {
        this.level = level;
    }
    public Long getPlaycost() {
        return playcost;
    }
    public void setPlaycost(Long playcost) {
        this.playcost = playcost;
    }
    public Long getEvolutioncost() {
        return evolutioncost;
    }
    public void setEvolutioncost(Long evolutioncost) {
        this.evolutioncost = evolutioncost;
    }
    public Long getDp() {
        return dp;
    }
    public void setDp(Long dp) {
        this.dp = dp;
    }
    public String getCardnumber() {
        return cardnumber;
    }
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }
    public String getMaineffect() {
        return maineffect;
    }
    public void setMaineffect(String maineffect) {
        this.maineffect = maineffect;
    }
    public String getSourceeffect() {
        return sourceeffect;
    }
    public void setSourceeffect(String sourceeffect) {
        this.sourceeffect = sourceeffect;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }  
}


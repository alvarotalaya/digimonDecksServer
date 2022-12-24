package net.ausiamarch.digimondecksSB.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    String level;
    String playcost;
    String evolutioncost;
    String dp;
    String cardnumber;
    String maineffect;
    String sourceeffect;
    String image;

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
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getPlaycost() {
        return playcost;
    }
    public void setPlaycost(String playcost) {
        this.playcost = playcost;
    }
    public String getEvolutioncost() {
        return evolutioncost;
    }
    public void setEvolutioncost(String evolutioncost) {
        this.evolutioncost = evolutioncost;
    }
    public String getDp() {
        return dp;
    }
    public void setDp(String dp) {
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

/* {"name":"Canoweissmon","type":"Digimon","color":"Red","stage":"Ultimate","digi_type":"Sky Dragon"
,"attribute":"Vaccine","level":5,"play_cost":8,"evolution_cost":4,"cardrarity":"Rare","artist":"Nakano Haito",
"dp":8000,"cardnumber":"BT10-011",
"maineffect":"[Your Turn] (Once Per Turn) When one of your Tamers becomes suspended, this Digimon gets +2000 DP for the turn. Then, if this Digimon has 12000 DP or more, it gains &lt;Security Attack +1&gt; (This Digimon checks 1 additional security card) for the turn. [All Turns] This Digimon gains all effects of cards with [Gammamon] in their names in this Digimon's digivolution cards.",
"soureeffect":"[All Turns] This Digimon gains all effects of cards with [Gammamon] in their names in this Digimon's digivolution cards.",
"set_name":"Xros Encounter Pre-Release Pack",
"card_sets":["Xros Encounter Pre-Release Pack","BT-10: Booster Xros Encounter","BT-10: Booster Xros Encounter"],
"image_url":"https://images.digimoncard.io/images/cards/BT10-011.jpg"} */

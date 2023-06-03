package net.ausiamarch.digimondecksSB.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "deck")
@JsonIgnoreProperties({ "hibernateLazyInitialize", "handler" })
public class DeckEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idplayer")
    private PlayerEntity player;

    String name;
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date lastupdate;

    @OneToMany(mappedBy = "deck", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CardDeckEntity> carddecks;

    public DeckEntity() {
        this.carddecks = new ArrayList<>();
    }

    public DeckEntity(Long id) {
        this.carddecks = new ArrayList<>();
        this.id = id;
    }

    public int getCarddecks() {
        return carddecks.size();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastupdate() {
        return lastupdate;
    }

    public void setLastUpdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }
}

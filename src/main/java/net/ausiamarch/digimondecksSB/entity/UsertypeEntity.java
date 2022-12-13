package net.ausiamarch.digimondecksSB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usertype")
@JsonIgnoreProperties({ "hibernateLazyInitialize", "handler" })
public class UsertypeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @OneToMany(mappedBy = "usertype", fetch = FetchType.LAZY)
    private final List<PlayerEntity> players;

    public UsertypeEntity() {
        this.players = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UsertypeEntity(Long id) {
        this.players = new ArrayList<>();
        this.id = id;
    }

    public int getPlayers() {
        return players.size();
    }
}

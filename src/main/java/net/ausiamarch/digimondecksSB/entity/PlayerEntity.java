package net.ausiamarch.digimondecksSB.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "player")
@JsonIgnoreProperties({ "hibernateLazyInitialize", "handler" })
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;
    String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idusertype")
    private UsertypeEntity usertype;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsertypeEntity getUsertype() {
        return usertype;
    }

    public void setUsertype(UsertypeEntity usertype) {
        this.usertype = usertype;
    }
}

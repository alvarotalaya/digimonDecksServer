package net.ausiamarch.digimondecksSB.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public class PlayerBean {
    @Schema(example = "admin")
    private String name = "";
    @Schema(example = "4298f843f830fb3cc13ecdfe1b2cf10f51f929df056d644d1bca73228c5e8f64")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package org.jgrades.data.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataSourceDetails {
    private String url;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String connectionUrl() {
        return "jdbc:postgresql://" + getUrl();
    }
}

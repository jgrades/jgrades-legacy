package org.jgrades.data.api.entities;

import org.hibernate.annotations.Type;
import org.jgrades.data.api.utils.CustomType;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    private Long id;
    private String login;
    private String password;

    private String name;
    private String surname;
    private String email;

    private boolean active;

    private DateTime lastVisit;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    @Column
    @Type(type = CustomType.JODA_DATE_TIME)
    public DateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(DateTime lastVisit) {
        this.lastVisit = lastVisit;
    }


}

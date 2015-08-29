package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_SUBJECT")
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

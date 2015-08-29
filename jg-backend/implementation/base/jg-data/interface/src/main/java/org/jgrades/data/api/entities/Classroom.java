package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_CLASSROOM")
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String building;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID", nullable = false)
    private School school;
}

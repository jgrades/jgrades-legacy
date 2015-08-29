package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "JG_DATA_SUBGROUP")
@Data
public class SubGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIVISION_ID", nullable = false)
    private Division division;
}

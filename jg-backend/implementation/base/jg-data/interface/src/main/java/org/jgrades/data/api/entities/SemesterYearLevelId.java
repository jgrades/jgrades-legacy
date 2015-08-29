package org.jgrades.data.api.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class SemesterYearLevelId implements Serializable {
    @ManyToOne
    private Semester semester;

    @ManyToOne
    private YearLevel yearLevel;
}

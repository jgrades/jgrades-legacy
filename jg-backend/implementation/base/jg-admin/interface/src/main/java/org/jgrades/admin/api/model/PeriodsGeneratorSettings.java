package org.jgrades.admin.api.model;

import lombok.Data;
import org.joda.time.LocalTime;

import java.util.List;

@Data
public class PeriodsGeneratorSettings {
    private LocalTime firstLessonTime;

    private Integer lessonDurationMinutes;

    private List<Integer> breakDurations;
}

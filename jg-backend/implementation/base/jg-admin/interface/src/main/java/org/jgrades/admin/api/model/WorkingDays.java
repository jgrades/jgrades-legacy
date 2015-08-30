package org.jgrades.admin.api.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.time.DayOfWeek;
import java.util.Set;

public class WorkingDays {
    private Set<DayOfWeek> daysSet = Sets.newHashSet();

    public void addDay(DayOfWeek dayOfWeek) {
        daysSet.add(dayOfWeek);
    }

    public void removeDay(DayOfWeek dayOfWeek) {
        daysSet.remove(dayOfWeek);
    }

    public Set<DayOfWeek> getDays() {
        return ImmutableSet.copyOf(daysSet);
    }
}

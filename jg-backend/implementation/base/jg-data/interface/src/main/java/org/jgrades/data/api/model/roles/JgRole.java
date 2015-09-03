package org.jgrades.data.api.model.roles;

public enum JgRole {
    ADMINISTRATOR(5), MANAGER(4), TEACHER(3), PARENT(2), STUDENT(1);

    private final Integer priority;

    JgRole(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}

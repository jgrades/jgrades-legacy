package org.jgrades.data.api.model.roles;

public enum JgRole {
    ADMINISTRATOR(5){
        public String toString(){
            return "ROLE_ADMINISTRATOR";
        }
    },

    MANAGER(4){
        public String toString(){
            return "ROLE_MANAGER";
        }
    },

    TEACHER(3){
        public String toString(){
            return "ROLE_TEACHER";
        }
    },

    PARENT(2){
        public String toString(){
            return "ROLE_PARENT";
        }
    },

    STUDENT(1){
        public String toString(){
            return "ROLE_STUDENT";
        }
    };

    private final Integer priority;

    JgRole(Integer priority) {
        this.priority = priority;
    }

    public Integer getPriority() {
        return priority;
    }
}

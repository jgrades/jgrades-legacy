package org.jgrades.data.api.roles;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

public class Roles {
    private Map<RoleName, RoleDetails> roleMap = Maps.newEnumMap(RoleName.class);

    public void addRole(RoleName name, RoleDetails details) {
        roleMap.put(name, details);
    }

    public void removeRole(RoleName name) {
        roleMap.remove(name);
    }

    public Map<RoleName, RoleDetails> getRoleMap() {
        return ImmutableMap.copyOf(roleMap);
    }

    public enum RoleName {
        ADMINISTRATOR, MANAGER, TEACHER, STUDENT, PARENT
    }
}

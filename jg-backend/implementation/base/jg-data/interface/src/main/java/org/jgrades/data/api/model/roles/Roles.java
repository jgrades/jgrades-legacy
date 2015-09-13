package org.jgrades.data.api.model.roles;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

@Data
public class Roles {
    private Map<JgRole, RoleDetails> roleMap = Maps.newEnumMap(JgRole.class);

    public void addRole(JgRole name, RoleDetails details) {
        roleMap.put(name, details);
    }

    public void removeRole(JgRole name) {
        roleMap.remove(name);
    }

    public Map<JgRole, RoleDetails> getRoleMap() {
        return ImmutableMap.copyOf(roleMap);
    }
}

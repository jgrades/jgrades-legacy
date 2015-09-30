package org.jgrades.admin.api.common;

import java.util.List;

public interface Manager<T, ID> {
    void saveOrUpdate(T obj);

    void remove(T obj);

    void remove(List<T> objs);

    void removeId(ID id);

    void removeIds(List<ID> ids);
}

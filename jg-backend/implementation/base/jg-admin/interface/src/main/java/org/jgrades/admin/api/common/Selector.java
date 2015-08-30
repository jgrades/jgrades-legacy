package org.jgrades.admin.api.common;

import java.util.List;

public interface Selector<T, ID> {
    List<T> getAll();

    T getWithId(ID id);
}

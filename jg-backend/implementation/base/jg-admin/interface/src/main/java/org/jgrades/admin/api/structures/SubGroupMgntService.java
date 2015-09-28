package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.SubGroup;

public interface SubGroupMgntService extends Manager<SubGroup>, PagingSelector<SubGroup, Long> {
}

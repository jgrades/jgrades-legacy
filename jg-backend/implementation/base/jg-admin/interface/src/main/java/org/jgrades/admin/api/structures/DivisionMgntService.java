package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.Division;

public interface DivisionMgntService extends Manager<Division>, PagingSelector<Division, Long> {
}

package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.YearLevel;

public interface YearLevelMgntService extends Manager<YearLevel>, PagingSelector<YearLevel, Long> {
}

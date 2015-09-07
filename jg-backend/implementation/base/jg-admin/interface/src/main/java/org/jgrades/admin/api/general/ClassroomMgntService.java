package org.jgrades.admin.api.general;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.Classroom;

public interface ClassroomMgntService extends Manager<Classroom>, PagingSelector<Classroom, Long> {

}

package org.jgrades.admin.api.general;

import org.jgrades.admin.api.common.Manager;
import org.jgrades.admin.api.common.PagingSelector;
import org.jgrades.data.api.entities.Subject;

public interface SubjectsMgntService extends Manager<Subject, Long>, PagingSelector<Subject, Long> {

}

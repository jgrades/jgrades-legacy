package org.jgrades.admin.api.structures;

import org.jgrades.admin.api.common.CrudPagingService;
import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.Division;
import org.jgrades.data.api.entities.Student;

import java.util.List;
import java.util.Set;

public interface ClassGroupMgntService extends CrudPagingService<ClassGroup, Long> {
    List<Division> getDivisions(ClassGroup classGroup);

    Set<Student> getStudents(ClassGroup classGroup);

    void addStudent(ClassGroup classGroup, Student student);
}

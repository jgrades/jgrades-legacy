/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.admin.structures;

import org.jgrades.admin.api.structures.ClassGroupMgntService;
import org.jgrades.admin.api.structures.DivisionMgntService;
import org.jgrades.admin.api.structures.SubGroupMgntService;
import org.jgrades.data.api.dao.structures.ClassGroupRepository;
import org.jgrades.data.api.entities.ClassGroup;
import org.jgrades.data.api.entities.Division;
import org.jgrades.data.api.entities.SubGroup;
import org.jgrades.data.api.entities.roles.StudentDetails;
import org.jgrades.data.api.service.crud.AbstractPagingMgntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ClassGroupMgntServiceImpl extends AbstractPagingMgntService<ClassGroup, Long, ClassGroupRepository> implements ClassGroupMgntService {
    @Autowired
    private DivisionMgntService divisionMgntService;

    @Autowired
    private SubGroupMgntService subGroupMgntService;

    @Autowired
    public ClassGroupMgntServiceImpl(ClassGroupRepository repository) {
        super(repository);
    }

    @Override
    @Transactional("mainTransactionManager")
    public void saveOrUpdate(ClassGroup classGroup) {
        Division division = new Division();
        division.setName(Division.FULL_CLASSGROUP_DIVISION_NAME);
        division.setClassGroup(classGroup);
        classGroup.getDivisions().add(division);

        SubGroup subGroup = new SubGroup();
        subGroup.setName(SubGroup.FULL_CLASSGROUP_SUBGROUP_NAME);
        subGroup.setDivision(division);
        division.getSubGroups().add(subGroup);

        super.saveOrUpdate(classGroup);
    }

    @Override
    public List<ClassGroup> getFromActiveSemester() {
        return repository.findInActiveSemester();
    }

    @Override
    @Transactional("mainTransactionManager")
    public List<Division> getDivisions(ClassGroup classGroup) {
        return getWithId(classGroup.getId()).getDivisions();
    }

    @Override
    public Set<StudentDetails> getStudents(ClassGroup classGroup) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStudents(ClassGroup classGroup, Set<StudentDetails> students) {
        throw new UnsupportedOperationException();
    }
}

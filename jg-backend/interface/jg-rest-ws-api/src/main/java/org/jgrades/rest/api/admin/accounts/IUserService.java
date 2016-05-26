/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.api.admin.accounts;

import org.jgrades.admin.api.model.MassAccountCreatorResultRecord;
import org.jgrades.data.api.entities.User;
import org.jgrades.rest.api.common.RestCrudPagingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IUserService extends RestCrudPagingService<User, Long> {
     String I_NAME = "test";

    @Qualifier(I_NAME)
    List<User> getSearchResults(String phrase, String login, String name, String surname, String email,
                                String roles, Boolean active, LocalDateTime lastVisitFrom, LocalDateTime lastVisitTo);

    Page<User> getSearchResultsPage(Integer number, Integer size,
                                    String phrase, String login, String name, String surname, String email,
                                    String roles, Boolean active, LocalDateTime lastVisitFrom, LocalDateTime lastVisitTo);

    Set<MassAccountCreatorResultRecord> massStudentsCreator(MassCreatorDTO massCreatorDTO);
}

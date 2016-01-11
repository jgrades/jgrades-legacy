/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.config.service;

import org.apache.commons.collections4.CollectionUtils;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.config.api.exception.UserPreferencesException;
import org.jgrades.config.api.model.UserData;
import org.jgrades.data.api.entities.User;
import org.jgrades.data.api.entities.roles.RoleDetails;
import org.jgrades.data.api.model.JgRole;
import org.jgrades.data.api.model.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.valid4j.Validation.otherwiseThrowing;
import static org.valid4j.Validation.validate;

@Component
public class UserPreferencesUpdater implements UserUpdater {
    @Autowired
    private UserMgntService userMgntService;

    private static void mapNewValues(Class clazz, Object updatedObject, Object actualObject)
            throws IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(UserSetting.class)) {
                field.setAccessible(true);
                field.set(actualObject, field.get(updatedObject));
                field.setAccessible(false);
            }
        }
    }

    @Override
    public void update(UserData userData) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        validate(userDetails, notNullValue(), otherwiseThrowing(UserPreferencesException.class));
        User persistUser = userMgntService.getWithLogin(userDetails.getUsername());

        try {
            updateGeneralData(userData.getUser(), persistUser);
            updateRoleData(userData.getUser().getRoles(), persistUser.getRoles());
        } catch (IllegalAccessException e) {
            throw new UserPreferencesException("Problem with updating user details", e);
        }
        userMgntService.saveOrUpdate(persistUser);
    }

    private void updateGeneralData(User updatedUser, User actualUser) throws IllegalAccessException {
        mapNewValues(User.class, updatedUser, actualUser);
    }

    private void updateRoleData(EnumMap<JgRole, RoleDetails> updatedRoles,
                                EnumMap<JgRole, RoleDetails> persistRoles) throws IllegalAccessException {
        if (!persistRoles.containsKey(JgRole.ADMINISTRATOR) && !CollectionUtils.isEqualCollection(updatedRoles.keySet(), persistRoles.keySet())) {
            throw new UserPreferencesException("Roles cannot be modified by user itself");
        }
        Set<Map.Entry<JgRole, RoleDetails>> entries = updatedRoles.entrySet();
        for (Map.Entry<JgRole, RoleDetails> entry : entries) {
            mapNewValues(entry.getValue().getClass(), updatedRoles.get(entry.getKey()), entry.getValue());
        }

    }
}

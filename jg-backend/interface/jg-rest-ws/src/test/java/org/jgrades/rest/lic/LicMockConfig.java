/*
 * Copyright (C) 2016 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package org.jgrades.rest.lic;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jgrades.admin.api.accounts.MassAccountCreatorService;
import org.jgrades.admin.api.accounts.UserMgntService;
import org.jgrades.admin.api.accounts.UserSpecifications;
import org.jgrades.admin.api.general.*;
import org.jgrades.admin.api.structures.*;
import org.jgrades.backup.api.service.BackupManagerService;
import org.jgrades.config.api.service.UserPreferencesService;
import org.jgrades.data.api.dao.*;
import org.jgrades.data.api.dao.accounts.UserRepository;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
import org.jgrades.monitor.api.service.SystemStateService;
import org.jgrades.security.api.service.PasswordMgntService;
import org.jgrades.security.api.service.PasswordPolicyService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class LicMockConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LicenceManagingService licenceManagingService() {
        return Mockito.mock(LicenceManagingService.class);
    }

    @Bean
    public LicenceCheckingService licenceCheckingService() {
        return Mockito.mock(LicenceCheckingService.class);
    }

    @Bean(name = "resolverMock")
    @Primary
    public IncomingFilesNameResolver incomingFilesNameResolver() {
        return Mockito.mock(IncomingFilesNameResolver.class);
    }

    @Bean
    public DataSourceService dataSourceService() {
        return Mockito.mock(DataSourceService.class);
    }

    @Bean(name = "userDetailsService")
    @Primary
    public UserDetailsService userDetailsService() {
        return Mockito.mock(UserDetailsService.class);
    }

    @Bean
    @Primary
    public org.apache.commons.configuration.Configuration appConfiguration() {
        return new PropertiesConfiguration();
    }

    @Bean
    @Primary
    public PasswordMgntService passwordMgntService() {
        return Mockito.mock(PasswordMgntService.class);
    }

    @Bean
    @Primary
    public PasswordPolicyService passwordPolicyService() {
        return Mockito.mock(PasswordPolicyService.class);
    }

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    @Primary
    public ClassroomMgntService classroomMgntService() {
        return Mockito.mock(ClassroomMgntService.class);
    }

    @Bean
    @Primary
    public ClassroomRepository classroomRepository() {
        return Mockito.mock(ClassroomRepository.class);
    }

    @Bean
    @Primary
    public GeneralDataService generalDataService() {
        return Mockito.mock(GeneralDataService.class);
    }

    @Bean
    @Primary
    public SchoolRepository schoolRepository() {
        return Mockito.mock(SchoolRepository.class);
    }

    @Bean
    @Primary
    public SubjectsMgntService subjectsMgntService() {
        return Mockito.mock(SubjectsMgntService.class);
    }

    @Bean
    @Primary
    public SubjectRepository subjectRepository() {
        return Mockito.mock(SubjectRepository.class);
    }

    @Bean
    @Primary
    public DaysMgntService daysMgntService() {
        return Mockito.mock(DaysMgntService.class);
    }

    @Bean
    @Primary
    public SchoolDayRepository schoolDayRepository() {
        return Mockito.mock(SchoolDayRepository.class);
    }

    @Bean
    @Primary
    public PeriodsMgntService periodsMgntService() {
        return Mockito.mock(PeriodsMgntService.class);
    }

    @Bean
    @Primary
    public SchoolDayPeriodRepository schoolDayPeriodRepository() {
        return Mockito.mock(SchoolDayPeriodRepository.class);
    }

    @Bean
    @Primary
    public UserSpecifications userSpecifications() {
        return Mockito.mock(UserSpecifications.class);
    }

    @Bean
    @Primary
    public AcademicYearMgntService academicYearMgntService() {
        return Mockito.mock(AcademicYearMgntService.class);
    }

    @Bean
    @Primary
    public SemesterMgntService semesterMgntService() {
        return Mockito.mock(SemesterMgntService.class);
    }

    @Bean
    @Primary
    public YearLevelMgntService yearLevelMgntService() {
        return Mockito.mock(YearLevelMgntService.class);
    }

    @Bean
    @Primary
    public ClassGroupMgntService classGroupMgntService() {
        return Mockito.mock(ClassGroupMgntService.class);
    }

    @Bean
    @Primary
    public DivisionMgntService divisionMgntService() {
        return Mockito.mock(DivisionMgntService.class);
    }

    @Bean
    @Primary
    public UserMgntService userMgntService() {
        return Mockito.mock(UserMgntService.class);
    }

    @Bean
    @Primary
    public SubGroupMgntService subGroupMgntService() {
        return Mockito.mock(SubGroupMgntService.class);
    }

    @Bean
    @Primary
    public SystemStateService systemStateService() {
        return Mockito.mock(SystemStateService.class);
    }

    @Bean
    @Primary
    public MassAccountCreatorService massAccountCreatorService() {
        return Mockito.mock(MassAccountCreatorService.class);
    }

    @Bean
    @Primary
    public UserPreferencesService userPreferencesService() {
        return Mockito.mock(UserPreferencesService.class);
    }

    @Bean
    @Primary
    public BackupManagerService backupManagerService() {
        return Mockito.mock(BackupManagerService.class);
    }
}

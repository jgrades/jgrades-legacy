package org.jgrades.rest.lic;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.jgrades.admin.api.accounts.*;
import org.jgrades.admin.api.general.*;
import org.jgrades.data.api.dao.*;
import org.jgrades.data.api.service.DataSourceService;
import org.jgrades.lic.api.service.LicenceCheckingService;
import org.jgrades.lic.api.service.LicenceManagingService;
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
    public RoleUserRepository roleUserRepository() {
        return Mockito.mock(RoleUserRepository.class);
    }

    @Bean
    @Primary
    public UserSpecifications userSpecifications() {
        return Mockito.mock(UserSpecifications.class);
    }

    @Bean
    @Primary
    public AdministratorMgntService administratorMgntService() {
        return Mockito.mock(AdministratorMgntService.class);
    }

    @Bean
    @Primary
    public ManagerMgntService managerMgntService() {
        return Mockito.mock(ManagerMgntService.class);
    }

    @Bean
    @Primary
    public TeacherMgntService teacherMgntService() {
        return Mockito.mock(TeacherMgntService.class);
    }

    @Bean
    @Primary
    public StudentMgntService studentMgntService() {
        return Mockito.mock(StudentMgntService.class);
    }

    @Bean
    @Primary
    public ParentMgntService parentMgntService() {
        return Mockito.mock(ParentMgntService.class);
    }
}

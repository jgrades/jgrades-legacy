package org.jgrades.monitor.api.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jgrades.monitor.api.exception.SystemDependencyException;
import org.jgrades.monitor.api.model.SystemDependency;
import org.jgrades.monitor.api.service.SystemDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Aspect
@Component
public class CompletenessAspect {
    @Autowired
    private SystemDependencyService systemDependencyService;

    @Pointcut(value = "execution(* *(..))")
    private void anyMethod() {
    }

    @Before("anyMethod() && @within(checkDependencies)")
    public void checkDependencies(CheckSystemDependencies checkDependencies) throws SystemDependencyException {
        SystemDependency[] dependencies = ArrayUtils.removeElements(SystemDependency.values(), checkDependencies.ignored());
        systemDependencyService.check(new HashSet<>(Arrays.asList(dependencies)));
    }
}


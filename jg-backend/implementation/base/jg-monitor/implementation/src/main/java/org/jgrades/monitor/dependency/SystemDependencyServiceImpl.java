package org.jgrades.monitor.dependency;

import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.jgrades.monitor.api.exception.SystemDependencyException;
import org.jgrades.monitor.api.model.SystemDependency;
import org.jgrades.monitor.api.service.SystemDependencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SystemDependencyServiceImpl implements SystemDependencyService {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(SystemDependencyServiceImpl.class);

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void check(Set<SystemDependency> systemDependencies) throws SystemDependencyException {
        for (SystemDependency dependency : systemDependencies) {
            checkOne(dependency);
        }
    }

    private void checkOne(SystemDependency dependency) {
        DependencyChecker checker = (DependencyChecker) appContext.getBean(dependency.getCheckerBeanName());
        if (!checker.check()) {
            LOGGER.error("Error during checking system dependency: {}", dependency);
            throw new SystemDependencyException(dependency);
        }

    }
}

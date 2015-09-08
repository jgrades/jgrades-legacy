package org.jgrades.monitor.api.service;

import org.jgrades.monitor.api.exception.SystemDependencyException;
import org.jgrades.monitor.api.model.SystemDependency;

import java.util.Set;

public interface SystemDependencyService {
    void check(Set<SystemDependency> systemDependencies) throws SystemDependencyException;
}

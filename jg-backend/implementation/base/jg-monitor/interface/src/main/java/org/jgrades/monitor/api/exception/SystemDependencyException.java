package org.jgrades.monitor.api.exception;

import org.jgrades.monitor.api.model.SystemDependency;

public class SystemDependencyException extends RuntimeException {
    public SystemDependencyException(SystemDependency dependency) {
        super("Checking system dependency failed: " + dependency.getDetails());
    }
}

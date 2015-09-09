package org.jgrades.monitor.api.aop;


import org.jgrades.monitor.api.model.SystemDependency;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface CheckSystemDependencies {
    SystemDependency[] ignored() default SystemDependency.NONE;
}

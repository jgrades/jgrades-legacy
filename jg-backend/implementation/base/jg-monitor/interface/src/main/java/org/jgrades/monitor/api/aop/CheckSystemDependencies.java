package org.jgrades.monitor.api.aop;


import org.jgrades.monitor.api.model.SystemDependency;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface CheckSystemDependencies {
    SystemDependency[] ignored() default SystemDependency.NONE;
}

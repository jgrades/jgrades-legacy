package org.jgrades.monitor.dependency;

public class DummyChecker implements DependencyChecker {
    @Override
    public boolean check() {
        return true;
    }
}

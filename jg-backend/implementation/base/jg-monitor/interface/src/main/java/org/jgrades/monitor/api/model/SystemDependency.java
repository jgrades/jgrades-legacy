package org.jgrades.monitor.api.model;

public enum SystemDependency {
    MAIN_DATA_SOURCE("mainDataSourceChecker", "Connection to database failed"),
    LIC_KEYSTORE("licKeystoreChecker", "Licensing data missing"),
    LIC_SECDATA("licSecDataChecker", "Licensing data missing"),
    LOGBACK_XML("logbackXmlChecker", "Logging data missing"),
    NONE("dummyChecker", "dummy");

    private final String checkerBeanName;
    private final String details;

    SystemDependency(String checkerBeanName, String details) {
        this.checkerBeanName = checkerBeanName;
        this.details = details;
    }

    public String getCheckerBeanName() {
        return checkerBeanName;
    }

    public String getDetails() {
        return details;
    }
}

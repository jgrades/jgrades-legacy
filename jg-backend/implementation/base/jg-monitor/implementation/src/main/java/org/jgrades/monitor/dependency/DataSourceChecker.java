package org.jgrades.monitor.dependency;

import org.jgrades.data.api.model.DataSourceDetails;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceChecker implements DependencyChecker {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DataSourceChecker.class);

    private DataSourceDetails dsInfo;
    private String driverName;

    public DataSourceChecker(DataSourceDetails dsInfo, String driverName) {
        this.dsInfo = dsInfo;
        this.driverName = driverName;
    }

    @Override
    public boolean check() {
        try {
            Class.forName(driverName).newInstance();
            DriverManager.getConnection(dsInfo.getUrl(), dsInfo.getUsername(), dsInfo.getPassword());
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.error("CONNECTION TO MAIN DATABASE FAILED. ACCESS TO MAIN DATABASE IS A MANDATORY NEED FOR JGRADES SYSTEM", e);
            LOGGER.error("CONNECTION DETAILS: driverName={}, url={}, username={}, password=(marked)", driverName, dsInfo.getUrl(), dsInfo.getUsername());
            return false;
        }
        return true;
    }
}

package org.jgrades.lic.utils;

import org.h2.tools.Server;
import org.jgrades.logging.JgLogger;
import org.jgrades.logging.JgLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DbCloser {
    private static final JgLogger LOGGER = JgLoggerFactory.getLogger(DbCloser.class);

    private DataSource ds;

    @Autowired
    public DbCloser(@Qualifier("licDataSource") DataSource ds) {
        this.ds = ds;
    }

    @PreDestroy
    public void close(){
        try {
            LOGGER.info("Shutting down licensing database started");
            Connection con = ds.getConnection();
            Statement stat = con.createStatement();
            stat.execute("SHUTDOWN");
            LOGGER.info("Shutting down licensing database done");

            LOGGER.info("Licensing DB server stopping started");
            Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
            LOGGER.info("Licensing DB server stopped correctly");

            LOGGER.info("Unloading JDBC H2 driver");
            org.h2.Driver.unload();
            LOGGER.info("Unloading JDBC H2 driver done");
        } catch (SQLException e) {
            LOGGER.error("Problem during shutting down licensing database", e);
        }
    }
}

package org.jgrades.data.api.service;

import org.jgrades.data.api.model.DataSourceDetails;

public interface DataSourceService {
    DataSourceDetails getDataSourceDetails();

    void setDataSourceDetails(DataSourceDetails dataSourceDetails);

    boolean testConnection();
}

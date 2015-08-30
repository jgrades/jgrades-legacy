package org.jgrades.admin.api.general;

import org.jgrades.admin.api.model.DataSourceDetails;

public interface DataSourceService {
    DataSourceDetails getDataSourceDetails();

    void setDataSourceDetails(DataSourceDetails dataSourceDetails);

    boolean testConnection();
}

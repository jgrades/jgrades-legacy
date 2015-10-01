package org.jgrades.monitor.dependency;

import lombok.Data;

@Data
public class DataSourceDTO {
    private String url;

    private String username;

    private String password;
}

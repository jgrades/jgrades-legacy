/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package com.vaadin.demo.dashboard.domain;

import java.util.Date;

public final class MovieRevenue {

    private Date timestamp;
    private String title;
    private Double revenue;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(final Double revenue) {
        this.revenue = revenue;
    }

}

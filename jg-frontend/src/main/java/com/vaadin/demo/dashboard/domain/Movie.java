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

public final class Movie {
    private long id;
    private String title;
    private String synopsis;
    private String thumbUrl;
    private String posterUrl;
    private int duration;
    private Date releaseDate;
    private int score;

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(final String synopsis) {
        this.synopsis = synopsis;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(final String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(final String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

}

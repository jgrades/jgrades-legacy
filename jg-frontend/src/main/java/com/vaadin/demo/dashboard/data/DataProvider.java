/*
 * Copyright (C) 2015 the original author or authors.
 *
 * This file is part of jGrades Application Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 */

package com.vaadin.demo.dashboard.data;

import com.vaadin.demo.dashboard.domain.*;

import java.util.Collection;
import java.util.Date;

/**
 * QuickTickets Dashboard backend API.
 */
public interface DataProvider {
    /**
     * @param count Number of transactions to fetch.
     * @return A Collection of most recent transactions.
     */
    Collection<Transaction> getRecentTransactions(int count);

    /**
     * @param id Movie identifier.
     * @return A Collection of daily revenues for the movie.
     */
    Collection<MovieRevenue> getDailyRevenuesByMovie(long id);

    /**
     * @return Total revenues for each listed movie.
     */
    Collection<MovieRevenue> getTotalMovieRevenues();

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password);

    /**
     * @return The number of unread notifications for the current user.
     */
    int getUnreadNotificationsCount();

    /**
     * @return Notifications for the current user.
     */
    Collection<DashboardNotification> getNotifications();

    /**
     * @return The total summed up revenue of sold movie tickets
     */
    double getTotalSum();

    /**
     * @return A Collection of movies.
     */
    Collection<Movie> getMovies();

    /**
     * @param movieId Movie's identifier
     * @return A Movie instance for the given id.
     */
    Movie getMovie(long movieId);

    /**
     * @param startDate
     * @param endDate
     * @return A Collection of Transactions between the given start and end
     * dates.
     */
    Collection<Transaction> getTransactionsBetween(Date startDate, Date endDate);
}

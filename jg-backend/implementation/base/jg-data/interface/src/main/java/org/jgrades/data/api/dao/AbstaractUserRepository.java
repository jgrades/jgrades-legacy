package org.jgrades.data.api.dao;

import org.jgrades.data.api.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbstaractUserRepository<U extends User> extends PagingAndSortingRepository<U, Long>, JpaSpecificationExecutor<U> {
    U findFirstByLogin(String login);
}

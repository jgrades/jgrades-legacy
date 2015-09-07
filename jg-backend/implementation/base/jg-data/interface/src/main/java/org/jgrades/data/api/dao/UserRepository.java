package org.jgrades.data.api.dao;

import org.jgrades.data.api.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findFirstByLogin(String login);
}

package com.varkovich.integration_api.repository;

import com.varkovich.integration_api.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByAccountIdAndCategory(Long accountId, String category);
}

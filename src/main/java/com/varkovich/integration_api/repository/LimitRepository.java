package com.varkovich.integration_api.repository;

import com.varkovich.integration_api.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query(value = "select * from limits where account_id = :account_id" +
            " and category = :category order by limit_timestamp desc limit 1", nativeQuery = true)
    Optional<Limit> findByAccountIdAndCategoryAndLatestLimitTimestamp(@Param("account_id") Long accountId, @Param("category") String category);
}

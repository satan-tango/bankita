package com.varkovich.integration_api.repository;

import com.varkovich.integration_api.model.ExceededTransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceededTransactionLimitRepository extends JpaRepository<ExceededTransactionLimit, Long> {
}

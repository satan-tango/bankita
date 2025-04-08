package com.varkovich.integration_api.model;

import com.varkovich.integration_api.utils.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity(name = "transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Account 'from' can not be null.")
    @Size(min = 10, max = 10, message = "Account 'from' number must consist of 10 characters.")
    private String accountFrom;

    @NotNull(message = "Account 'to' can not be null.")
    @Size(min = 10, max = 10, message = "Account 'to' number must consist of 10 characters.")
    private String accountTo;

    @NotNull(message = "Category can not be null.")
    @Pattern(regexp = Constants.REG_EXP_FOR_GOODS_AND_SERVICES, message = "Must be either 'goods' or 'services'")
    private String category;

    @NotNull(message = "Sum can not be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal sum;

    @NotNull(message = "Currency can not be null.")
    @Size(min = 3, max = 3, message = "Currency must consist of 3 characters.")
    private String currencyShortName;

    private Timestamp created_at;
}

package ru.info.country.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Table
public class SuccessResponseCount {

    @Id
    private UUID id;

    private String service;

    private Integer version;

    private Integer cachedSuccessResponseCount;

    private Integer paidSuccessResponseCount;

    private LocalDateTime createDate;

    private LocalDateTime modifiedDate;
}

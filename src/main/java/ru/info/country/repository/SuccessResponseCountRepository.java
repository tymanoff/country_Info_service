package ru.info.country.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.info.country.entity.SuccessResponseCount;

import java.util.UUID;

@Repository
public interface SuccessResponseCountRepository extends CrudRepository<SuccessResponseCount, UUID> {
    String SELECT_ROW_FOR_UPDATE = """
            SELECT id, cached_success_response_count, paid_success_response_count
            FROM success_response_count
            WHERE service = ?
            AND version = ?
            FOR UPDATE
            """;

    String INSERT_NEW_ROW = """
            INSERT INTO success_response_count ( 
            service, version, cached_success_response_count, 
            paid_success_response_count, create_date, modified_date)
            VALUES ( ?, ?, ?, ?, now(), now() ) 
            """;
    String UPDATE_INCREMENT_CASHED_ROW = """
            UPDATE success_response_count SET cached_success_response_count = ?, modified_date = now() WHERE id = ?
            """;

    String UPDATE_INCREMENT_PAID_ROW = """
            UPDATE success_response_count SET paid_success_response_count = ?, modified_date = now() WHERE id= ?
            """;
}

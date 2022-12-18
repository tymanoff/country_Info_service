package ru.info.country.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.info.country.entity.CachedRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CachedRequestRepository extends CrudRepository<CachedRequest, UUID> {

    Optional<CachedRequest> findTopByServiceAndVersionAndRequestBodyHash(String service,
                                                                         Integer version,
                                                                         String requestBodyHash);
}

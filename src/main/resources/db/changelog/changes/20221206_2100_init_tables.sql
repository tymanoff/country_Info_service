CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table cached_request
(
    id                uuid default uuid_generate_v4()
        constraint cached_request_id
            primary key,
    service           varchar(64),
    version           integer,
    request_body_hash varchar(128),
    request_body      text,
    response_body     text,
    create_date       timestamp,
    modified_date     timestamp,
    expired_date      timestamp
);

create unique index if not exists service_version_hash_cached_request_idx
    on cached_request (service, version, request_body_hash);

create table success_response_count
(
    id                            uuid default uuid_generate_v4()
        constraint success_response_count_id
            primary key,
    service                       varchar(64),
    version                       integer,
    cached_success_response_count integer,
    paid_success_response_count   integer,
    create_date                   timestamp,
    modified_date                 timestamp
);

create unique index if not exists service_version_success_response_count_idx
    on success_response_count (service, version);

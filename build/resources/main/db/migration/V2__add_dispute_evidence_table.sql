DROP TABLE IF EXISTS dispute_evidence CASCADE;

CREATE TABLE dispute_evidence (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL,
    required_evidence TEXT NOT NULL,
    defense_letter TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
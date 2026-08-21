CREATE TABLE IF NOT EXISTS ai_analysis (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL,
    analysis_content TEXT NOT NULL,
    win_probability INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
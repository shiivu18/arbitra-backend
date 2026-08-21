CREATE TABLE IF NOT EXISTS dispute_audits (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL UNIQUE,
    reason TEXT,
    ai_response TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'UNDER_REVIEW',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Safe guard in case the table already existed without the status column
DO $$ 
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                   WHERE table_name='dispute_audits' AND column_name='status') THEN
        ALTER TABLE dispute_audits ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'UNDER_REVIEW';
    END IF;
END $$;
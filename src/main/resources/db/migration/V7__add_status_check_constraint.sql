-- Ensure status column only accepts valid financial dispute workflows
ALTER TABLE dispute_audits 
ADD CONSTRAINT chk_dispute_status CHECK (status IN ('UNDER_REVIEW', 'WON', 'LOST'));

-- Create indexing for optimized lookup performance
CREATE INDEX IF NOT EXISTS idx_dispute_audits_status ON dispute_audits(status);
CREATE INDEX IF NOT EXISTS idx_dispute_history_dispute_id ON dispute_history(dispute_id);
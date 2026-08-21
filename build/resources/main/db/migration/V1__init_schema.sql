CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(128) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(32) NOT NULL DEFAULT 'OPS_REVIEWER',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE merchants (
    id BIGSERIAL PRIMARY KEY,
    razorpay_account_id VARCHAR(64) NOT NULL UNIQUE,
    business_name VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    razorpay_order_id VARCHAR(64) NOT NULL UNIQUE,
    razorpay_payment_id VARCHAR(64),
    total_amount_paise BIGINT NOT NULL,
    currency VARCHAR(8) NOT NULL DEFAULT 'INR',
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transfers (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    merchant_id BIGINT NOT NULL REFERENCES merchants(id) ON DELETE CASCADE,
    razorpay_transfer_id VARCHAR(64) NOT NULL UNIQUE,
    amount_paise BIGINT NOT NULL,
    on_hold BOOLEAN NOT NULL DEFAULT TRUE,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE disputes (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    transfer_id BIGINT NOT NULL REFERENCES transfers(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(32) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dispute_evidence (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL REFERENCES disputes(id) ON DELETE CASCADE,
    source_type VARCHAR(32) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ai_analyses (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL REFERENCES disputes(id) ON DELETE CASCADE,
    model_name VARCHAR(64) NOT NULL,
    fault_buyer_percentage INT NOT NULL,
    fault_seller_percentage INT NOT NULL,
    recommended_refund_paise BIGINT NOT NULL,
    recommended_release_paise BIGINT NOT NULL,
    confidence_score INT NOT NULL,
    reasoning_trace TEXT NOT NULL,
    analyzed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE settlement_audits (
    id BIGSERIAL PRIMARY KEY,
    dispute_id BIGINT NOT NULL REFERENCES disputes(id) ON DELETE CASCADE,
    razorpay_reversal_id VARCHAR(64),
    razorpay_refund_id VARCHAR(64),
    buyer_refund_amount_paise BIGINT NOT NULL,
    seller_release_amount_paise BIGINT NOT NULL,
    approved_by VARCHAR(64) NOT NULL,
    executed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE webhook_events (
    id BIGSERIAL PRIMARY KEY,
    razorpay_event_id VARCHAR(64) NOT NULL UNIQUE,
    event_type VARCHAR(64) NOT NULL,
    processing_status VARCHAR(32) NOT NULL,
    payload TEXT NOT NULL,
    received_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE cards
IF EXISTS;

CREATE TABLE cards (
  id                BIGINT IDENTITY PRIMARY KEY,
  pin               CHAR(4),
  pin_entered_count INTEGER DEFAULT 0,
  active            BOOLEAN DEFAULT TRUE NOT NULL
  --   ,created_at            TIMESTAMP DEFAULT NOW
);


DROP TABLE cards_accounting
IF EXISTS;

CREATE TABLE cards_accounting (
  id                    BIGINT IDENTITY PRIMARY KEY,
  card_id               BIGINT,
  amount_in_cents       BIGINT,
  operation_description VARCHAR(255)
  --   ,created_at            TIMESTAMP DEFAULT NOW
);
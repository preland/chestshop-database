CREATE TABLE Item
(
    item_code  VARCHAR(255) PRIMARY KEY,
    item_bytes VARBINARY(60000) NOT NULL,
    INDEX `idx_item_item_code` (item_code)
);

CREATE TABLE Shop
(
    world_uuid         BINARY(16)   NOT NULL,
    pos_x              INT          NOT NULL,
    pos_y              INT          NOT NULL,
    pos_z              INT          NOT NULL,
    item_code          VARCHAR(255) NOT NULL,
    owner_name         VARCHAR(255) NOT NULL,
    buy_price          DECIMAL(10, 2),
    sell_price         DECIMAL(10, 2),
    quantity           INT          NOT NULL,
    stock              INT          NOT NULL,
    estimated_capacity INT          NOT NULL,
    -- Use ON UPDATE to refresh timestamp without requiring triggers or SUPER privileges
    last_updated       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (world_uuid, pos_x, pos_y, pos_z),
    INDEX `idx_shop_item_code` (item_code)
);

-- MySQL older versions may not fully support CHECK constraints; keep only the FK here.
ALTER TABLE Shop
    ADD CONSTRAINT fk_shop_item_item_code
        FOREIGN KEY (`item_code`) REFERENCES `Item` (`item_code`) ON DELETE CASCADE
;

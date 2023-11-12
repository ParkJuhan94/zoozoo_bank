DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer`
(
    `id`           int          NOT NULL,
    `name`         varchar(255) NOT NULL,
    `contact_info` varchar(255) NOT NULL
);

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account`
(
    `id`          int NOT NULL,
    `balance`     bigint(2) NOT NULL,
    `customer_id` int NOT NULL,
    `branch_id`   int NOT NULL
);

DROP TABLE IF EXISTS `bankbranch`;

CREATE TABLE `bankbranch`
(
    `id`          int         NOT NULL,
    `assets`      bigint(20) NOT NULL,
    `branch_name` varchar(30) NOT NULL
);

ALTER TABLE `customer`
    ADD CONSTRAINT `PK_CUSTOMER` PRIMARY KEY (
                                              `id`
        );

ALTER TABLE `account`
    ADD CONSTRAINT `PK_ACCOUNT` PRIMARY KEY (
                                             `id`
        );

ALTER TABLE `bankbranch`
    ADD CONSTRAINT `PK_BANKBRANCH` PRIMARY KEY (
                                                `id`
        );

ALTER TABLE `account`
    ADD CONSTRAINT `FK_customer_TO_account_1` FOREIGN KEY (
                                                           `customer_id`
        )
        REFERENCES `customer` (
                               `id`
            );

ALTER TABLE `account`
    ADD CONSTRAINT `FK_bankbranch_TO_account_1` FOREIGN KEY (
                                                             `branch_id`
        )
        REFERENCES `bankbranch` (
                                 `id`
            );

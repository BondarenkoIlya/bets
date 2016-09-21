CREATE TABLE cash_accounts
(
  id      INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  balance DOUBLE
);

CREATE TABLE avatars
(
  id      INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  picture MEDIUMBLOB,
  date    DATETIME
);

CREATE TABLE conditions
(
  id             INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  conditionsName VARCHAR(255)        NOT NULL,
  coefficient    DOUBLE              NOT NULL,
  result         TINYINT(1)
);

CREATE TABLE matches
(
  id              INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  sportsName      VARCHAR(255)        NOT NULL,
  leaguesName     VARCHAR(255)        NOT NULL,
  eventsDate      DATETIME            NOT NULL,
  firstSidesName  VARCHAR(255)        NOT NULL,
  secondSidesName VARCHAR(255)        NOT NULL,
  active          TINYINT(1)
);

CREATE TABLE bookmakers
(
  id        INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(255)        NOT NULL,
  lastName  VARCHAR(255)        NOT NULL,
  password  VARCHAR(255)        NOT NULL,
  email     VARCHAR(255)        NOT NULL,
  purse_id  INT(11)
);


CREATE TABLE customers
(
  id        INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(255)        NOT NULL,
  lastName  VARCHAR(255)        NOT NULL,
  password  VARCHAR(255)        NOT NULL,
  email     VARCHAR(255)        NOT NULL,
  active    TINYINT(1),
  purse_id  INT(11),
  avatar_id INT(11),
  CONSTRAINT customers_avatar_fk FOREIGN KEY (avatar_id) REFERENCES avatars (id),
  CONSTRAINT fk_purseId_cashAccountId FOREIGN KEY (purse_id) REFERENCES cash_accounts (id)
);
CREATE INDEX customers_avatar_fk ON customers (avatar_id);
CREATE INDEX fk_purseId_cashAccountId ON customers (purse_id);


CREATE TABLE matches_conditions
(
  match_id     INT(11),
  condition_id INT(11),
  CONSTRAINT matches_conditions_conditions_id_fk FOREIGN KEY (condition_id) REFERENCES conditions (id),
  CONSTRAINT matches_conditions_matches_id_fk FOREIGN KEY (match_id) REFERENCES matches (id)
);
CREATE INDEX matches_conditions_conditions_id_fk ON matches_conditions (condition_id);
CREATE INDEX matches_conditions_matches_id_fk ON matches_conditions (match_id);

CREATE TABLE bets
(
  id               INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  value            DOUBLE              NOT NULL,
  customer_id      INT(11)             NOT NULL,
  possibleGain     DOUBLE,
  finalCoefficient DOUBLE,
  finalResult      TINYINT(1),
  betsDate         DATETIME,
  active           TINYINT(1),
  CONSTRAINT bets_customers_id_fk FOREIGN KEY (customer_id) REFERENCES customers (id)
);
CREATE INDEX bets_customers_id_fk ON bets (customer_id);

CREATE TABLE customers_bets
(
  customer_id INT(11) NOT NULL,
  bets_id     INT(11) NOT NULL,
  CONSTRAINT customers_bets_bets_id_fk FOREIGN KEY (bets_id) REFERENCES bets (id),
  CONSTRAINT customers_bets_customers_id_fk FOREIGN KEY (customer_id) REFERENCES customers (id)
);
CREATE INDEX customers_bets_bets_id_fk ON customers_bets (bets_id);
CREATE INDEX customers_bets_customers_id_fk ON customers_bets (customer_id);

CREATE TABLE bets_conditions
(
  bets_id       INT(11) NOT NULL,
  conditions_id INT(11) NOT NULL,
  CONSTRAINT bets_conditions_bets_id_fk FOREIGN KEY (bets_id) REFERENCES bets (id),
  CONSTRAINT bets_conditions_conditions_id_fk FOREIGN KEY (conditions_id) REFERENCES conditions (id)
);
CREATE INDEX bets_conditions_bets_id_fk ON bets_conditions (bets_id);
CREATE INDEX bets_conditions_conditions_id_fk ON bets_conditions (conditions_id);

CREATE TABLE transfers_to_customers
(
  id            INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  sender_id     INT(11),
  amount        DOUBLE              NOT NULL,
  recipient_id  INT(11)             NOT NULL,
  transfersTime DATETIME,
  CONSTRAINT transfers_to_customers_customers_id_fk FOREIGN KEY (recipient_id) REFERENCES customers (id)
);
CREATE INDEX transfers_to_customers_customers_id_fk ON transfers_to_customers (recipient_id);

CREATE TABLE transfers_to_bookmaker
(
  id            INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  sender_id     INT(11),
  amount        DOUBLE              NOT NULL,
  recipient_id  INT(11)             NOT NULL,
  transfersTime DATETIME,
  CONSTRAINT transfers_to_bookmaker_bookmakers_id_fk FOREIGN KEY (recipient_id) REFERENCES bookmakers (id)
);
CREATE INDEX transfers_to_bookmaker_bookmakers_id_fk ON transfers_to_bookmaker (recipient_id);





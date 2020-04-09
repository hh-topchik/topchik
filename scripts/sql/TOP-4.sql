CREATE TABLE IF NOT EXISTS daily_count (
    daily_count_id serial PRIMARY KEY,
    date date NOT NULL,
    account_id bigint,
    repo_id bigint,
    category int NOT NULL,
    counter int NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS weekly_result (
    weekly_result_id serial PRIMARY KEY,
    week_date date NOT NULL,
    account_id bigint,
    repo_id bigint,
    category int NOT NULL,
    points int NOT NULL,
    medal int,
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

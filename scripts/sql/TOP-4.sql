CREATE TABLE IF NOT EXISTS action (
    action_id serial PRIMARY KEY,
    date date NOT NULL,
    developer_id bigint,
    repo_id bigint,
    category int NOT NULL,
    counter int NOT NULL,
    FOREIGN KEY (developer_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS achievement (
    achievement_id serial PRIMARY KEY,
    week_date date NOT NULL,
    developer_id bigint,
    repo_id bigint,
    category int NOT NULL,
    points int NOT NULL,
    medal int,
    FOREIGN KEY (developer_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

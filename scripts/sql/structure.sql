
CREATE TABLE IF NOT EXISTS account (
    account_id bigint PRIMARY KEY,
    login varchar(255) NOT NULL,
    email varchar(255),
    name varchar(255),
    avatar VARCHAR (255)
);

CREATE TABLE IF NOT EXISTS repository (
    repo_id bigint PRIMARY KEY,
    path varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pull_request (
    pull_request_id bigint PRIMARY KEY,
    author_id bigint,
    creation_time timestamp NOT NULL,
    last_update_time timestamp NOT NULL,
    status int NOT NULL,
    repo_id bigint,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS review (
    review_id bigint PRIMARY KEY,
    author_id bigint,
    pull_request_id bigint,
    status int NOT NULL,
    time timestamp NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id)
);

CREATE TABLE IF NOT EXISTS comment (
    comment_id bigint PRIMARY KEY,
    creation_time timestamp NOT NULL,
    review_id bigint NOT NULL,
    FOREIGN KEY (review_id) REFERENCES review(review_id)
);

CREATE TABLE IF NOT EXISTS commit (
    sha char(40) PRIMARY KEY,
    author_id bigint,
    pull_request_id bigint,
    creation_time timestamp NOT NULL,
    added_lines int NOT NULL,
    deleted_lines int NOT NULL,
    repo_id bigint,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS daily_count (
    daily_count_id bigint PRIMARY KEY,
    date date NOT NULL,
    account_id bigint,
    repo_id bigint,
    category int NOT NULL,
    counter int NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS weekly_result (
    weekly_result_id bigint PRIMARY KEY,
    week_date date NOT NULL,
    account_id bigint,
    repo_id bigint,
    category int NOT NULL,
    points int NOT NULL,
    medal int,
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

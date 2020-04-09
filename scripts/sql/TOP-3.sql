CREATE TABLE IF NOT EXISTS account (
    account_id bigint PRIMARY KEY,
    login varchar(255) NOT NULL,
    email varchar(255),
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS repository (
    repo_id bigint PRIMARY KEY,
    path varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS pull_request (
    pull_request_id bigint PRIMARY KEY,
    author_id bigint NOT NULL,
    creation_time timestamp NOT NULL,
    last_update_time timestamp NOT NULL,
    status int NOT NULL,
    repo_id bigint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS review (
    review_id bigint PRIMARY KEY,
    author_id bigint NOT NULL,
    pull_request_id bigint NOT NULL,
    status int NOT NULL,
    time timestamp NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id)
);

CREATE TABLE IF NOT EXISTS commit (
    sha char(40) PRIMARY KEY,
    author_id bigint NOT NULL,
    pull_request_id bigint NOT NULL,
    creation_time timestamp NOT NULL,
    added_lines int NOT NULL,
    deleted_lines int NOT NULL,
    repo_id bigint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE IF NOT EXISTS comment (
    comment_id bigint PRIMARY KEY,
    creation_time timestamp NOT NULL,
    author_id bigint NOT NULL,
    commit_hash char(40) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (commit_hash) REFERENCES commit(sha)
);

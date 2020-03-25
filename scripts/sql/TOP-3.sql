CREATE TABLE account (
    account_id bigint PRIMARY KEY,
    login varchar(255) NOT NULL,
    email varchar(255),
    name varchar(255)
);

CREATE TABLE repository (
    repo_id bigint PRIMARY KEY,
    path varchar(255) NOT NULL
);

CREATE TABLE pull_request (
    pull_request_id bigint PRIMARY KEY,
    author_id bigint NOT NULL,
    creation_date date NOT NULL,
    last_update_date date NOT NULL,
    status int NOT NULL,
    repo_id bigint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE approve (
    approve_id bigint PRIMARY KEY,
    author_id bigint NOT NULL,
    pull_request_id bigint NOT NULL,
    status int NOT NULL,
    date date NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id)
);

CREATE TABLE commit (
    commit_id bigint PRIMARY KEY,
    sha bigint NOT NULL,
    author_id bigint NOT NULL,
    pull_request_id bigint NOT NULL,
    creation_date date NOT NULL,
    added_lines int NOT NULL,
    deleted_lines int NOT NULL,
    repo_id bigint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (pull_request_id) REFERENCES pull_request(pull_request_id),
    FOREIGN KEY (repo_id) REFERENCES repository(repo_id)
);

CREATE TABLE comment (
    comment_id bigint PRIMARY KEY,
    creation_date date NOT NULL,
    author_id bigint NOT NULL,
    commit_id bigint NOT NULL,
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (commit_id) REFERENCES commit(commit_id)
);
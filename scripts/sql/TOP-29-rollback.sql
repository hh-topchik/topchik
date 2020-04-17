CREATE TABLE IF NOT EXISTS comment (
    comment_id bigint PRIMARY KEY,
    creation_time timestamp NOT NULL,
    author_id bigint,
    commit_hash char(40),
    FOREIGN KEY (author_id) REFERENCES account(account_id),
    FOREIGN KEY (commit_hash) REFERENCES commit(sha)
);

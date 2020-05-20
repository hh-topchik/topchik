CREATE TABLE IF NOT EXISTS comment (
    comment_id bigint PRIMARY KEY,
    creation_time timestamp NOT NULL,
    review_id bigint NOT NULL,
    FOREIGN KEY (review_id) REFERENCES review(review_id)
);

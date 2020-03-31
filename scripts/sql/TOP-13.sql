ALTER TABLE approve ALTER COLUMN author_id DROP NOT NULL;
ALTER TABLE approve ALTER COLUMN pull_request_id DROP NOT NULL;

ALTER TABLE comment ALTER COLUMN commit_hash DROP NOT NULL;
ALTER TABLE comment ALTER COLUMN author_id DROP NOT NULL;

ALTER TABLE commit ALTER COLUMN author_id DROP NOT NULL;
ALTER TABLE commit ALTER COLUMN pull_request_id DROP NOT NULL;
ALTER TABLE commit ALTER COLUMN repo_id DROP NOT NULL;

ALTER TABLE pull_request ALTER COLUMN author_id DROP NOT NULL;
ALTER TABLE pull_request ALTER COLUMN repo_id DROP NOT NULL;

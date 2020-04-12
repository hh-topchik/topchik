ALTER TABLE approve RENAME TO review;
ALTER TABLE action RENAME TO daily_count;
ALTER TABLE achievement RENAME TO weekly_result;
ALTER SEQUENCE action_action_id_seq RENAME TO daily_count_daily_count_id_seq;
ALTER SEQUENCE achievement_achievement_id_seq RENAME TO weekly_result_weekly_result_id_seq;
ALTER TABLE review RENAME COLUMN approve_id TO review_id;
ALTER TABLE daily_count RENAME COLUMN action_id TO daily_count_id;
ALTER TABLE daily_count RENAME COLUMN developer_id TO account_id;
ALTER TABLE weekly_result RENAME COLUMN achievement_id TO weekly_result_id;
ALTER TABLE weekly_result RENAME COLUMN developer_id TO account_id;
ALTER TABLE review RENAME CONSTRAINT approve_author_id_fkey TO review_author_id_fkey;
ALTER TABLE review RENAME CONSTRAINT approve_pkey TO review_pkey;
ALTER TABLE review RENAME CONSTRAINT approve_pull_request_id_fkey TO review_pull_request_id_fkey;
ALTER TABLE daily_count RENAME CONSTRAINT action_developer_id_fkey TO daily_count_account_id_fkey;
ALTER TABLE daily_count RENAME CONSTRAINT action_pkey TO daily_count_pkey;
ALTER TABLE daily_count RENAME CONSTRAINT action_repo_id_fkey TO daily_count_repo_id_fkey;
ALTER TABLE weekly_result RENAME CONSTRAINT achievement_developer_id_fkey TO weekly_result_account_id_fkey;
ALTER TABLE weekly_result RENAME CONSTRAINT achievement_pkey TO weekly_result_pkey;
ALTER TABLE weekly_result RENAME CONSTRAINT achievement_repo_id_fkey TO weekly_result_repo_id_fkey;

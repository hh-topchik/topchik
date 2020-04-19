ALTER TABLE daily_count ALTER COLUMN counter TYPE bigint;
DROP SEQUENCE IF EXISTS daily_count_daily_count_id_seq CASCADE;
DROP SEQUENCE IF EXISTS weekly_result_weekly_result_id_seq CASCADE;

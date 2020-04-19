ALTER TABLE daily_count ALTER COLUMN counter TYPE int;
CREATE SEQUENCE daily_count_daily_count_id_seq START 1;
CREATE SEQUENCE weekly_result_weekly_result_id_seq START 1;
ALTER TABLE daily_count ALTER COLUMN daily_count_id SET default nextval('daily_count_daily_count_id_seq');
ALTER TABLE weekly_result ALTER COLUMN weekly_result_id SET default nextval('weekly_result_weekly_result_id_seq');

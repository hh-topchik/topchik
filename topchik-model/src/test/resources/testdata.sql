INSERT INTO account(account_id, login, email, name, avatar)
VALUES (1, 'BigBoss', 'a.korolev@roga-i-kopyta.ru', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (2, 'loskov', 'd.loskov@roga-i-kopyta.ru', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (3, 'TopDev', 'v.vlasov@roga-i-kopyta.ru', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (4, 'Ignatik', 'i.tarasov@roga-i-kopyta.ru', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (5, 'kaban82', 'a.kabanov@roga-i-kopyta.ru', NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (6, 'awesomebot[bot]', NULL, NULL, NULL) ON CONFLICT DO NOTHING;

INSERT INTO repository(repo_id, path)
VALUES (1, 'https://github.com/roga-i-kopyta/ourGreatJavaBackend') ON CONFLICT DO NOTHING;
INSERT INTO repository(repo_id, path)
VALUES (2, 'https://github.com/roga-i-kopyta/ourGreatReactFrontend') ON CONFLICT DO NOTHING;
INSERT INTO repository(repo_id, path)
VALUES (3, 'https://github.com/roga-i-kopyta/our_python_library_for_ds') ON CONFLICT DO NOTHING;

INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (1, 2, '2019-12-18 12:25:18', '2019-12-20 12:25:18', 3, 1) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (2, 3, '2020-01-06 12:25:18', '2020-01-10 12:25:18', 3, 1) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (3, 4, '2020-03-11 12:25:18', '2020-03-19 12:25:18', 1, 3) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (4, 5, '2020-04-25 12:25:18', '2020-04-30 12:25:18', 3, 2) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (5, 2, '2020-04-06 12:25:18', '2020-04-08 12:25:18', 3, 1) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (6, 3, '2020-06-01 12:25:18', '2020-06-02 12:25:18', 3, 1) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (7, 4, '2020-06-01 12:25:18', '2020-06-01 12:25:18', 1, 3) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (8, 5, '2020-05-06 12:25:18', '2020-05-06 12:25:18', 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (9, 6, '2020-04-06 12:25:18', '2020-04-06 12:25:18', 2, 3) ON CONFLICT DO NOTHING;

INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (1, 1, 1, 1, '2019-12-20 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (2, 1, 2, 1, '2020-01-10 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (3, 1, 3, 3, '2020-03-19 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (4, 1, 4, 1, '2020-04-30 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (5, 1, 5, 1, '2020-04-08 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (6, 1, 6, 1, '2020-06-02 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (7, 1, 7, 3, '2020-06-01 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (8, 1, 8, 3, '2020-05-06 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (9, 2, 1, 1, '2019-12-20 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (10, 3, 2, 1, '2020-01-10 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (11, 4, 3, 3, '2020-03-19 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (12, 5, 4, 1, '2020-04-30 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (13, 2, 5, 1, '2020-04-08 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (14, 3, 6, 1, '2020-06-02 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (15, 4, 7, 3, '2020-06-01 12:25:18') ON CONFLICT DO NOTHING;
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (16, 5, 8, 3, '2020-05-06 12:25:18') ON CONFLICT DO NOTHING;

INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (1, '2019-12-20 12:25:18', 1) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (2, '2019-12-20 12:25:18', 9) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (3, '2020-01-10 12:25:18', 2) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (4, '2020-01-10 12:25:18', 10) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (5, '2020-03-19 12:25:18', 3) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (6, '2020-03-19 12:25:18', 11) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (7, '2020-04-30 12:25:18', 4) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (8, '2020-04-30 12:25:18', 12) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (9, '2020-04-08 12:25:18', 5) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (10, '2020-04-08 12:25:18', 13) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (11, '2020-06-02 12:25:18', 6) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (12, '2020-06-02 12:25:18', 14) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (13, '2020-06-01 12:25:18', 7) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (14, '2020-06-01 12:25:18', 15) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (15, '2020-05-06 12:25:18', 8) ON CONFLICT DO NOTHING;
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (16, '2020-05-06 12:25:18', 16) ON CONFLICT DO NOTHING;

INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('D9C12864D7681276D3714EC47BC93D49554E03F6',
2, 1, '2019-12-18 12:25:18', 188, 22, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('59F8221482576238C75E2B4457AB0CF068C0065C',
3, 2, '2020-01-06 12:25:18', 220, 176, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('665AA090C0ECF0FE29C0CA9021314E2949AF674A',
4, 3, '2020-03-11 12:25:18', 215, 103, 3) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('3787A3C05F920084B7AC65645A2D4674EE8E1001',
5, 4, '2020-04-25 12:25:18', 48, 30, 2) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('AEE7D0574A7A06987D07BE0691E078405759CC49',
2, 5, '2020-04-06 12:25:18', 185, 291, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('4EC48FA5615EE21991ABAF2E6BA9A867CA108E39',
3, 6, '2020-06-01 12:25:18', 298, 91, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('14FA4D133C794D843EE35B811412748F83D3085B',
4, 7, '2020-06-01 12:25:18', 221, 280, 3) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('A74EF6B301E06E0EF5820393E5BA7346EC1DB622',
5, 8, '2020-05-06 12:25:18', 209, 126, 2) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('46F7497AD43B86E4883890F899762EE1E4A9089E',
6, 9, '2020-04-06 12:25:18', 119, 102, 3) ON CONFLICT DO NOTHING;

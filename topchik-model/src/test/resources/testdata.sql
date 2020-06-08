INSERT INTO account(account_id, login, email, name, avatar)
VALUES (1, 'BigBoss', 'a.korolev@roga-i-kopyta.ru', NULL, NULL);
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (2, 'loskov', 'd.loskov@roga-i-kopyta.ru', NULL, NULL);
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (3, 'TopDev', 'v.vlasov@roga-i-kopyta.ru', NULL, NULL);
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (4, 'Ignatik', 'i.tarasov@roga-i-kopyta.ru', NULL, NULL);
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (5, 'kaban82', 'a.kabanov@roga-i-kopyta.ru', NULL, NULL);
INSERT INTO account(account_id, login, email, name, avatar)
VALUES (6, 'awesomebot[bot]', NULL, NULL, NULL);

INSERT INTO repository(repo_id, path)
VALUES (1, 'https://github.com/roga-i-kopyta/ourGreatJavaBackend');
INSERT INTO repository(repo_id, path)
VALUES (2, 'https://github.com/roga-i-kopyta/ourGreatReactFrontend');
INSERT INTO repository(repo_id, path)
VALUES (3, 'https://github.com/roga-i-kopyta/our_python_library_for_ds');

INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (1, 2, NOW() - INTERVAL '168 DAYS', NOW() - INTERVAL '168 DAYS', 3, 1);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (2, 3, NOW() - INTERVAL '147 DAYS', NOW() - INTERVAL '147 DAYS', 3, 1);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (3, 4, NOW() - INTERVAL '84 DAYS', NOW() - INTERVAL '77 DAYS', 1, 3);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (4, 5, NOW() - INTERVAL '42 DAYS', NOW() - INTERVAL '35 DAYS', 3, 2);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (5, 2, NOW() - INTERVAL '56 DAYS', NOW() - INTERVAL '56 DAYS', 3, 1);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (6, 3, NOW(), NOW(), 3, 1);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (7, 4, NOW(), NOW(), 1, 3);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (8, 5, NOW() - INTERVAL '28 DAYS', NOW() - INTERVAL '28 DAYS', 1, 2);
INSERT INTO pull_request(pull_request_id, author_id, creation_time, last_update_time, status, repo_id)
VALUES (9, 6, NOW() - INTERVAL '56 DAYS', NOW() - INTERVAL '56 DAYS', 2, 3);

INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (1, 1, 1, 1, NOW() - INTERVAL '168 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (2, 1, 2, 1, NOW() - INTERVAL '147 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (3, 1, 3, 3, NOW() - INTERVAL '77 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (4, 1, 4, 1, NOW() - INTERVAL '35 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (5, 1, 5, 1, NOW() - INTERVAL '56 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (6, 1, 6, 1, NOW());
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (7, 1, 7, 3, NOW());
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (8, 1, 8, 3, NOW() - INTERVAL '28 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (9, 2, 1, 1, NOW() - INTERVAL '168 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (10, 3, 2, 1, NOW() - INTERVAL '147 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (11, 4, 3, 3, NOW() - INTERVAL '77 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (12, 5, 4, 1, NOW() - INTERVAL '35 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (13, 2, 5, 1, NOW() - INTERVAL '56 DAYS');
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (14, 3, 6, 1, NOW());
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (15, 4, 7, 3, NOW());
INSERT INTO review(review_id, author_id, pull_request_id, status, time)
VALUES (16, 5, 8, 3, NOW() - INTERVAL '28 DAYS');

INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (1, NOW() - INTERVAL '168 DAYS', 1);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (2, NOW() - INTERVAL '168 DAYS', 9);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (3, NOW() - INTERVAL '147 DAYS', 2);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (4, NOW() - INTERVAL '147 DAYS', 10);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (5, NOW() - INTERVAL '77 DAYS', 3);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (6, NOW() - INTERVAL '77 DAYS', 11);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (7, NOW() - INTERVAL '35 DAYS', 4);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (8, NOW() - INTERVAL '35 DAYS', 12);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (9, NOW() - INTERVAL '56 DAYS', 5);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (10, NOW() - INTERVAL '56 DAYS', 13);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (11, NOW(), 6);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (12, NOW(), 14);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (13, NOW(), 7);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (14, NOW(), 15);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (15, NOW() - INTERVAL '28 DAYS', 8);
INSERT INTO comment(comment_id, creation_time, review_id)
VALUES (16, NOW() - INTERVAL '28 DAYS', 16);

INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('D9C12864D7681276D3714EC47BC93D49554E03F6',
2, 1, NOW() - INTERVAL '168 DAYS', 188, 22, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('59F8221482576238C75E2B4457AB0CF068C0065C',
3, 2, NOW() - INTERVAL '147 DAYS', 220, 176, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('665AA090C0ECF0FE29C0CA9021314E2949AF674A',
4, 3, NOW() - INTERVAL '84 DAYS', 215, 103, 3) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('3787A3C05F920084B7AC65645A2D4674EE8E1001',
5, 4, NOW() - INTERVAL '42 DAYS', 48, 30, 2) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('AEE7D0574A7A06987D07BE0691E078405759CC49',
2, 5, NOW() - INTERVAL '56 DAYS', 185, 291, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('4EC48FA5615EE21991ABAF2E6BA9A867CA108E39',
3, 6, NOW(), 298, 91, 1) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('14FA4D133C794D843EE35B811412748F83D3085B',
4, 7, NOW(), 221, 280, 3) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('A74EF6B301E06E0EF5820393E5BA7346EC1DB622',
5, 8, NOW() - INTERVAL '28 DAYS', 209, 126, 2) ON CONFLICT DO NOTHING;
INSERT INTO commit(sha, author_id, pull_request_id, creation_time, added_lines, deleted_lines, repo_id)
VALUES ('46F7497AD43B86E4883890F899762EE1E4A9089E',
6, 9, NOW() - INTERVAL '56 DAYS', 119, 102, 3) ON CONFLICT DO NOTHING;

-- 請根據你的 Flashcard 屬性對應欄位名稱
INSERT INTO FLASHCARD (WORD, TRANSLATION, LANGUAGE, LEVEL)
VALUES ('こんにちは', '你好', 'JA', 1)
ON CONFLICT DO NOTHING; -- 💡 這是 PostgreSQL 專用語法，防止重複插入導致報錯

INSERT INTO FLASHCARD (WORD, TRANSLATION, LANGUAGE, LEVEL)
VALUES ('apple', '蘋果', 'EN', 1)
ON CONFLICT DO NOTHING; -- 💡 這是 PostgreSQL 專用語法，防止重複插入導致報錯
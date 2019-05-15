SELECT id, name, year, first_deadline, second_deadline
FROM PublishYears
WHERE year = strftime('%Y', 'now')
;

SELECT count(S.id)                                      AS submitted,
       count(CASE S.chosen WHEN 1 THEN 1 ELSE NULL END) AS chosen,
       first_deadline,
       second_deadline
FROM Submissions S
         JOIN Students S2 ON S.student_id = S2.id
         JOIN PublishYears PY ON S.publish_year = PY.id
WHERE strftime('%Y', 'now') = PY.year
;
SELECT SM.id,
       count(SM.id)                      AS count,
       publish_year,
       faculty_id,
       strftime('%Y-%m-%d', submit_time) AS submit_date,
       chosen
FROM Submissions SM
         JOIN Students S ON SM.student_id = S.id
WHERE SM.publish_year = ?
GROUP BY submit_date, faculty_id
ORDER BY submit_date
;
SELECT strftime('%Y-%m-%d', submit_time) AS submit_date
FROM Submissions S
;
SELECT submit_time
FROM Submissions S
ORDER BY submit_time
;
SELECT name, student_id, publish_year, submit_time, chosen
FROM Submissions S
WHERE student_id = ?
ORDER BY submit_time
LIMIT 10 OFFSET ?
;

SELECT SM.name, rollnumber, U.name AS student_name, submit_time, chosen
FROM Submissions SM
         JOIN Students S ON SM.student_id = S.id
         JOIN Users U ON S.id = U.role_id
WHERE role = 3
  AND faculty_id = ?
  AND publish_year = ?
ORDER BY submit_time DESC
LIMIT 10
;

SELECT SM.name, student_id, publish_year, submit_time, F.id AS file_id
FROM Submissions SM
         JOIN Files F ON SM.id = F.submission_id
         JOIN PublishYears PY ON SM.publish_year = PY.id
WHERE SM.id = ?
;

SELECT F.disk_location, F.id AS file_id FROM Submissions SM JOIN Files F ON SM.id = F.submission_id WHERE SM.id = ?
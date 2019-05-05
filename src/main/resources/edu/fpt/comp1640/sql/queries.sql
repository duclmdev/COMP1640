SELECT id, name, year, first_deadline, second_deadline
FROM PublishYears
WHERE year = strftime('%Y', 'now')
;

SELECT count(S.id) AS submitted, count(CASE S.chosen WHEN 1 THEN 1 ELSE NULL END) AS chosen, first_deadline, second_deadline FROM Submissions S JOIN Students S2 ON S.student_id = S2.id JOIN PublishYears PY ON S.publish_year = PY.id WHERE strftime('%Y', 'now') = PY.year
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
SELECT *
FROM Submissions S
WHERE student_id = ?
ORDER BY submit_time;

 
CREATE OR REPLACE FUNCTION timestamp_diff
(
start_time_in TIMESTAMP
, end_time_in TIMESTAMP
)
RETURN NUMBER
AS
l_days NUMBER;
l_hours NUMBER;
l_minutes NUMBER;
l_seconds NUMBER;
BEGIN
SELECT extract(DAY FROM end_time_in-start_time_in)
, extract(HOUR FROM end_time_in-start_time_in)
, extract(MINUTE FROM end_time_in-start_time_in)
, extract(SECOND FROM end_time_in-start_time_in)
INTO l_days, l_hours, l_minutes, l_seconds
FROM dual;


l_seconds := l_seconds + l_minutes*60 + l_hours*60*60 + l_days*24*60*60;
RETURN l_seconds;

END;
/
-----------------------------timestamp diff----------------------------
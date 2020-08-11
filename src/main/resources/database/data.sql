INSERT INTO usr(
user_id,first_name,last_name,password,role_name)
VALUES
('yamada','山田','太郎','$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa','ROLE_GENERAL');

INSERT INTO usr(
user_id,first_name,last_name,password,role_name)
VALUES
('ichikawa','市川','智之','$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa','ROLE_ADMIN'); 

INSERT INTO meeting_room(room_name)
VALUES
('新木場'),('辰巳'),('豊洲'),('月島'),('新富町'),('銀座一丁目'),('有楽町');


INSERT INTO reservable_room(
reserved_date,room_id)
VALUES
(CURRENT_DATE,1),
(CURRENT_DATE+1,1),
(CURRENT_DATE-1,1),
(CURRENT_DATE,7),
(CURRENT_DATE+1,7),
(CURRENT_DATE-1,7)
;
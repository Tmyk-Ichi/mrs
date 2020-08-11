DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS reservable_room;
DROP TABLE IF EXISTS usr;
DROP TABLE IF EXISTS meeting_room;


CREATE TABLE IF NOT EXISTS
usr(
	user_id VARCHAR(255) NOT NULL PRIMARY KEY,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role_name VARCHAR(255) NOT NULL
); 

CREATE TABLE IF NOT EXISTS
meeting_room(
	room_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	room_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS
reservable_room(
	room_id INTEGER NOT NULL,
	reserved_date DATE NOT NULL,
	PRIMARY KEY(reserved_date, room_id),
	FOREIGN KEY (room_id) REFERENCES meeting_room(room_id)	
);

/* ALTER TABLE reservable_room ADD PRIMARY KEY(reserved_date,room_id); */

CREATE TABLE IF NOT EXISTS
reservation(
	reservation_id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	start_time TIME NOT NULL,
	end_time TIME NOT NULL,
	reserved_date DATE NOT NULL,
	room_id INTEGER NOT NULL,
	user_id VARCHAR(255) NOT NULL,
	FOREIGN KEY (room_id) REFERENCES reservable_room(room_id),
	FOREIGN KEY (user_id) REFERENCES usr(user_id),
	FOREIGN KEY (reserved_date) REFERENCES reservable_room(reserved_date)
);



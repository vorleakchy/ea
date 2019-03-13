 
INSERT INTO credentials(username,password,enabled) VALUES ('guest','guest', TRUE);
INSERT INTO credentials(username,password,enabled) VALUES ('admin','admin', TRUE);
 
INSERT INTO authority (id,username, authority) VALUES (1,'guest', 'ROLE_USER');
INSERT INTO authority (id,username, authority) VALUES (2,'admin', 'ROLE_ADMIN');
INSERT INTO authority (id,username, authority) VALUES (3,'admin', 'ROLE_USER');

INSERT INTO  `MEMBER` (id,firstname, lastname,age,title,membernumber, member_id) VALUES (1,'Curious','George',12,'Boy Monkey', 8754,'admin');
INSERT INTO `MEMBER` (id,firstname, lastname,age,title,membernumber,member_id) VALUES (2,'Allen','Rench',123,'Torque Master', 8733,'guest');

INSERT INTO `ADDRESS` (id,city,state,street,member_id) VALUES (1,'Fairfield','Iowa','Main','1');
INSERT INTO `ADDRESS` (id,city,state,street,member_id) VALUES (2,'Batavia','Iowa','Maple','2');
INSERT INTO `ADDRESS` (id,city,state,street,member_id) VALUES (3,'Ottumwa','Iowa','Maple','2');

INSERT INTO product(id,name,price, description) VALUES (1,"Sail Boat",22.0, "Sails");
INSERT INTO product(id,name,price, description) VALUES (2,"Toy Boat",22.0, "floats");
 
					
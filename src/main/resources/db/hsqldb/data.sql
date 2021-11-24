-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,email,password,enabled) VALUES ('admin1','eladmin@gmail.com','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('alefr99','alefraile1@gmail.com','123123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'alefr99','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('diecrequi','diecrequi@gmail.com','diecrequi',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'diecrequi','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('alfcadmor','alfcadmor@gmail.com','alfcadmor',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'alfcadmor','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('pedolirod','pedolirod@gmail.com','pedolirod',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'pedolirod','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('antquiher1','antquiher1@gmail.com','antquiher1',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'antquiher1','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('juanlo','juanlo@gmail.com','juanlo',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'juanlo','admin');


INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'alefr99');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'alfcadmor');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'pedolirod');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'juanlo');

INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'alfcadmor');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'pedolirod');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'juanlo');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alfcadmor', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alfcadmor', 'juanlo');
INSERT INTO friends(id_user, id_user_friend) VALUES ('juanlo', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('pedolirod', 'antquiher1');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO comments (id, title, body, create_date, username) VALUES (1, 'Welcome to Samurai Sword', 'Welcome to everyone that want to play this game with us!', '2021-10-28', 'admin1');

INSERT INTO types1 VALUES (1, 'Bronce');
INSERT INTO types1 VALUES (2, 'Plata');
INSERT INTO types1 VALUES (3, 'Oro');
INSERT INTO types1 VALUES (4, 'Diamante');

INSERT INTO types2 VALUES (1, 'Shogun');
INSERT INTO types2 VALUES (2, 'Ninja');
INSERT INTO types2 VALUES (3, 'Samurai');
INSERT INTO types2 VALUES (4, 'Ronin');
INSERT INTO types2 VALUES (5, 'Ninguno');

INSERT INTO logros (id, title, body, typeL, typeR, username) VALUES (1, 'Primer comentario', 'Realizar tu primer comentario', 1, 5, 'admin1');

INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('puntuacion','puntuacion.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('armadura','property/armadura.png',4);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('bushido','property/bushido.png',3);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('concentracion','property/concentracion.png',6);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('desenvainado rapido','property/desenvainado rapido.png',3);

INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('ceremona del te','action/ceremona del te.png',4);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('daimio','action/daimio.png',3);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('distraccion','action/distraccion.png',5);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('geisha','action/geisha.png',7);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('grito de batalla','action/grito de batalla.png',4);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('jiu-jitsu','action/jiu-jitsu.png',3);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('parada','action/parada.png',15);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('respiracion','action/respiracion.png',3);

INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('bo','attack/bo.png',5);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('bokken','attack/bokken.png',6);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('daiku','attack/daiku.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('kanabo','attack/kanabo.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('katana','attack/katana.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('kiseru','attack/kiseru.png',5);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('kusarigama','attack/kusarigama.png',4);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('nagayari','attack/nagayari.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('naginata','attack/naginata.png',2);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('nodachi','attack/nodachi.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('shuriken','attack/shuriken.png',3);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('tanegashima','attack/tanegashima.png',1);
INSERT INTO cards(name,image,cards_Per_Deck) VALUES ('wakizashi','attack/wakizashi.png',1);

INSERT INTO games(id,end_date,start_date) VALUES (1, parsedatetime('24-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('24-11-2021 19:08:13', 'dd-MM-yyyy hh:mm:ss'));
INSERT INTO players(id,current_hearts,honor,max_hearts,position,rol,won_game,game_id,username) VALUES (1,5,3,5,2,1,true,1,'diecrequi');
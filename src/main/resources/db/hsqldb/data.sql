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

INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('diecrequi', 'alefr99');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('diecrequi', 'admin1');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('juanlo', 'diecrequi');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('diecrequi', 'admin1');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('juanlo', 'admin1');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('antquiher1', 'admin1');
INSERT INTO friend_Requests(id_user, id_user_requested) VALUES ('pedolirod', 'admin1');


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

INSERT INTO characters VALUES (1, 'tomoe');

INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (1, 'Primer comentario', 'Realizar tu primer comentario', 1, 5, 'admin1');


INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('puntuacion','puntuacion.png',1, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('armadura','property/armadura.png',4, 'Blue');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('bushido','property/bushido.png',3, 'Blue');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('concentracion','property/concentracion.png',6, 'Blue');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('desenvainado rapido','property/desenvainado rapido.png',3, 'Blue');

INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('ceremonia del te','action/ceremonia del te.png',4, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('daimio','action/daimio.png',3, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('distraccion','action/distraccion.png',5, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('geisha','action/geisha.png',7, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('grito de batalla','action/grito de batalla.png',4, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('jiu-jitsu','action/jiu-jitsu.png',3, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('parada','action/parada.png',15, 'Yellow');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('respiracion','action/respiracion.png',3, 'Yellow');

INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('bo','attack/bo.png',5, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('bokken','attack/bokken.png',6, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('daiku','attack/daiku.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('kanabo','attack/kanabo.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('katana','attack/katana.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('kiseru','attack/kiseru.png',5, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('kusarigama','attack/kusarigama.png',4, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('nagayari','attack/nagayari.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('naginata','attack/naginata.png',2, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('nodachi','attack/nodachi.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('shuriken','attack/shuriken.png',3, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('tanegashima','attack/tanegashima.png',1, 'Red');
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('wakizashi','attack/wakizashi.png',1, 'Red');

INSERT INTO games(id,end_date,start_date) VALUES (1, parsedatetime('24-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('24-11-2021 19:08:13', 'dd-MM-yyyy hh:mm:ss'));
INSERT INTO players(id,current_hearts,honor,max_hearts,position,rol,won_game,game_id,username, es_inofensivo) VALUES (1,5,3,5,2,1,true,1,'diecrequi', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,position,rol,won_game,game_id,username, es_inofensivo) VALUES (2,4,5,4,3,1,true,1,'alefr99', false);

INSERT INTO card_hands (id, player) VALUES (1, 1);
INSERT INTO card_hands (id, player) VALUES (2, 1);
INSERT INTO card_hands (id, player) VALUES (3, 2);

INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (5, 5);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (7, 1);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (1, 4);




--Hay que comentarlo porque la entidad player no funciona bien todavia
--INSERT INTO players (id, game, photo, max_hearts, current_hearts, honor, position, rol, username) VALUES (1, 'jugador1', 'foto', 5, 5, 1, 1, 5, 'admin1');

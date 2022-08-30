-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,email,password,enabled) VALUES ('admin1','eladmin@gmail.com','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('alefr99','alefraile1@gmail.com','123123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'alefr99','admin');

INSERT INTO users(username,email,password,enabled) VALUES ('DonShelby','golfoman@gmail.com','DonShelby',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'DonShelby','admin');

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

INSERT INTO users(username,email,password,enabled) VALUES ('prueba','prueba@gmail.com','prueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'prueba','admin');

INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'alefr99');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'alfcadmor');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'pedolirod');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'juanlo');
INSERT INTO friends(id_user, id_user_friend) VALUES ('DonShelby', 'alefr99');
INSERT INTO friends(id_user, id_user_friend) VALUES ('DonShelby', 'alfcadmor');


INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'alfcadmor');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'pedolirod');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'juanlo');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alfcadmor', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alfcadmor', 'juanlo');
INSERT INTO friends(id_user, id_user_friend) VALUES ('juanlo', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('pedolirod', 'antquiher1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'admin1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('alefr99', 'admin1');
INSERT INTO friends(id_user, id_user_friend) VALUES ('pedolirod', 'admin1');

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


INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (1, 'Hello world', 'Write 1 comment', 1, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (2, 'Stalker', 'Send 5 friend requests', 2, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (3, 'Friends with benefits', 'Be friend with 3 users', 3, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (4, 'Center of atention', 'Be friend with 5 users', 4, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (9, 'Initiated', 'Create an account', 1, 5, 'admin1');

INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (5, 'Shogun amateur', 'Win 1 game being a SHOGUN', 1, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (10, 'Shogun junior', 'Win 3 game being a SHOGUN', 2, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (11, 'Shogun senior', 'Win 5 game being a SHOGUN', 3, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (12, 'Shogun master', 'Win 8 game being a SHOGUN', 4, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (22, 'First time Shogun', 'Play 1 game being a SHOGUN', 1, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (23, 'Silver Shogun', 'Play 3 game being a SHOGUN', 2, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (24, 'Gold Shogun', 'Play 5 game being a SHOGUN', 3, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (25, 'Diamond Shogun', 'Play 8 game being a SHOGUN', 4, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (38, 'Mediocre Shogun', 'Lose 1 game being a SHOGUN', 1, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (39, 'Bad Shogun', 'Lose 3 game being a SHOGUN', 2, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (53, 'Horrible Shogun', 'Lose 5 game being a SHOGUN', 3, 1, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (40, 'The worst Shogun', 'Lose 8 game being a SHOGUN', 4, 1, 'admin1');

INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (6, 'Ninja amateur', 'Win 1 game being a NINJA', 1, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (13, 'Ninja junior', 'Win 3 game being a NINJA', 2, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (14, 'Ninja senior', 'Win 5 game being a NINJA', 3, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (15, 'Ninja master', 'Win 8 game being a NINJA', 4, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (26, 'First time Ninja', 'Play 1 game being a NINJA', 1, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (27, 'Silver Ninja', 'Play 3 game being a NINJA', 2, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (28, 'Gold Ninja', 'Play 5 game being a NINJA', 3, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (29, 'Diamond Ninja', 'Play 8 game being a NINJA', 4, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (41, 'Mediocre Ninja', 'Lose 1 game being a NINJA', 1, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (42, 'Bad Ninja', 'Lose 3 game being a NINJA', 2, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (43, 'Horrible Ninja', 'Lose 5 game being a NINJA', 3, 2, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (44, 'The worst Ninja', 'Lose 8 game being a NINJA', 4, 2, 'admin1');

INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (7, 'Samurai amateur', 'Win 1 game being a SAMURAI', 1, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (16, 'Samurai junior', 'Win 3 game being a SAMURAI', 2, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (17, 'Samurai senior', 'Win 5 game being a SAMURAI', 3, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (18, 'Samurai master', 'Win 8 game being a SAMURAI', 4, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (30, 'First time Samurai', 'Play 1 game being a SAMURAI', 1, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (31, 'Silver Samurai', 'Play 3 game being a SAMURAI', 2, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (32, 'Gold Samurai', 'Play 5 game being a SAMURAI', 3, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (33, 'Diamond Samurai', 'Play 8 game being a SAMURAI', 4, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (45, 'Mediocre Samurai', 'Lose 1 game being a SAMURAI', 1, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (46, 'Bad Samurai', 'Lose 3 game being a SAMURAI', 2, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (47, 'Horrible Samurai', 'Lose 5 game being a SAMURAI', 3, 3, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (48, 'The worst Samurai', 'Lose 8 game being a SAMURAI', 4, 3, 'admin1');

INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (8, 'Ronin amateur', 'Win 1 game being a RONIN', 1, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (19, 'Ronin junior', 'Win 3 game being a RONIN', 2, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (20, 'Ronin senior', 'Win 5 game being a RONIN', 3, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (21, 'Ronin master', 'Win 8 game being a RONIN', 4, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (34, 'First time Ronin', 'Play 1 game being a RONIN', 1, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (35, 'Silver Ronin', 'Play 3 game being a RONIN', 2, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (36, 'Gold Ronin', 'Play 5 game being a RONIN', 3, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (37, 'Diamond Ronin', 'Play 8 game being a RONIN', 4, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (49, 'Mediocre Ronin', 'Lose 1 game being a RONIN', 1, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (50, 'Bad Ronin', 'Lose 3 game being a RONIN', 2, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (51, 'Horrible Ronin', 'Lose 5 game being a RONIN', 3, 4, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (52, 'The worst Ronin', 'Lose 8 game being a RONIN', 4, 4, 'admin1');

INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('armadura','property/armadura.png',4,'Blue','Blue');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('bushido','property/bushido.png',3,'Blue','Blue');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('concentracion','property/concentracion.png',6,'Blue','Blue');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('desenvainado rapido','property/desenvainado rapido.png',3,'Blue','Blue');

INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('ceremonia del te','action/ceremonia del te.png',4,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('daimio','action/daimio.png',3,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('distraccion','action/distraccion.png',5,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('geisha','action/geisha.png',7,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('grito de batalla','action/grito de batalla.png',4,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('jiu-jitsu','action/jiu-jitsu.png',3,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('parada','action/parada.png',15,'Yellow','Yellow');
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR) VALUES ('respiracion','action/respiracion.png',3,'Yellow','Yellow');

INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('bo','attack/bo.png',5,'Red','Red',1, 2);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('bokken','attack/bokken.png',6,'Red','Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('daiku','attack/daiku.png',1,'Red','Red',2, 5);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('kanabo','attack/kanabo.png',1,'Red','Red',2, 3);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('katana','attack/katana.png',1,'Red','Red',3, 2);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('kiseru','attack/kiseru.png',5,'Red','Red',2, 1);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('kusarigama','attack/kusarigama.png',4,'Red','Red',2, 2);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('nagayari','attack/nagayari.png',1,'Red','Red',2, 4);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('naginata','attack/naginata.png',2,'Red','Red',1, 4);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('nodachi','attack/nodachi.png',1,'Red','Red',3, 3);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('shuriken','attack/shuriken.png',3,'Red','Red',1, 3);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('tanegashima','attack/tanegashima.png',1,'Red','Red',1, 5);
INSERT INTO cards(name,image,cards_Per_Deck,color,CARD_COLOR, damage, range) VALUES ('wakizashi','attack/wakizashi.png',1,'Red','Red',3, 1);


INSERT INTO games(id,end_date,start_date) VALUES (1, parsedatetime('24-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('24-11-2021 19:08:13', 'dd-MM-yyyy hh:mm:ss'));
INSERT INTO games(id,end_date,start_date) VALUES (2, parsedatetime('27-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('27-11-2021 16:08:13', 'dd-MM-yyyy hh:mm:ss'));

INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (1,0,0,0,1,false,1,'diecrequi', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (2,0,0,0,1,false,1,'alefr99', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (3,0,0,0,1,false,1,'admin1', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (4,0,0,0,1,false,1,'pedolirod', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (5,0,0,0,1,false,1,'antquiher1', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (6,0,0,0,1,false,1,'prueba', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (7,0,0,0,1,false,1,'DonShelby', false);

INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (5, 5);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (7, 1);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (1, 4);


INSERT INTO characters(id,name,life,image) VALUES (1,'Benkei',5,'characters/benkei.png');
INSERT INTO characters(id,name,life,image) VALUES (2,'Chiyome',4,'characters/chiyome.png');
INSERT INTO characters(id,name,life,image) VALUES (3,'Ginchiyo',4,'characters/ginchiyo.png');
INSERT INTO characters(id,name,life,image) VALUES (4,'Goemon',5,'characters/goemon.png');
INSERT INTO characters(id,name,life,image) VALUES (5,'Hideyoshi',4,'characters/hideyoshi.png');
INSERT INTO characters(id,name,life,image) VALUES (6,'Musashi',5,'characters/musashi.png');
INSERT INTO characters(id,name,life,image) VALUES (7,'Tomoe',5,'characters/tomoe.png');


INSERT INTO invitation(user_sender, user_addresse, game_id) VALUES ('alefr99', 'alfcadmor', 1);
INSERT INTO invitation(user_sender, user_addresse, game_id) VALUES ('alefr99', 'DonShelby', 1);
--Hay que comentarlo porque la entidad player no funciona bien todavia
--INSERT INTO players (id, game, photo, max_hearts, current_hearts, honor, position, rol, username) VALUES (1, 'jugador1', 'foto', 5, 5, 1, 1, 5, 'admin1');

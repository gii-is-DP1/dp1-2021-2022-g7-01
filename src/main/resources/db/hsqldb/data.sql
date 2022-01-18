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


INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (1, 'Hello world', 'Write 1 comment', 1, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (2, 'Stalker', 'Send 5 friend requests', 2, 5, 'admin1');
INSERT INTO achievements (id, title, body, typeL, typeR, username) VALUES (3, 'Friends with benefits', 'Be friend with 3 users', 3, 5, 'admin1');


INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR) VALUES ('puntuacion','puntuacion.png',1, 'Blue');
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

INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('bo','attack/bo.png',5, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('bokken','attack/bokken.png',6, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('daiku','attack/daiku.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('kanabo','attack/kanabo.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('katana','attack/katana.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('kiseru','attack/kiseru.png',5, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('kusarigama','attack/kusarigama.png',4, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('nagayari','attack/nagayari.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('naginata','attack/naginata.png',2, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('nodachi','attack/nodachi.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('shuriken','attack/shuriken.png',3, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('tanegashima','attack/tanegashima.png',1, 'Red',1, 1);
INSERT INTO cards(name,image,cards_Per_Deck, CARD_COLOR, damage, range) VALUES ('wakizashi','attack/wakizashi.png',1, 'Red',1, 1);


INSERT INTO games(id,end_date,start_date) VALUES (1, parsedatetime('24-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('24-11-2021 19:08:13', 'dd-MM-yyyy hh:mm:ss'));
INSERT INTO games(id,end_date,start_date) VALUES (2, parsedatetime('27-11-2021 18:47:52', 'dd-MM-yyyy hh:mm:ss'),parsedatetime('27-11-2021 16:08:13', 'dd-MM-yyyy hh:mm:ss'));

INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (1,0,0,0,1,false,1,'diecrequi', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (2,0,0,0,1,false,1,'alefr99', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (3,0,0,0,1,false,1,'admin1', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (4,0,0,0,1,false,1,'pedolirod', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (5,0,0,0,1,false,1,'antquiher1', false);
INSERT INTO players(id,current_hearts,honor,max_hearts,rol,won_game,game_id,username, disabled) VALUES (6,0,0,0,1,false,1,'prueba', false);

INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (5, 5);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (7, 1);
INSERT INTO cards_in_hands(card_id, card_hand_id) VALUES (1, 4);

INSERT INTO characters(id,name,life,image,text, game_phase) VALUES (1,'Benkei',5,'characters/benkei.png','All other players have +1 Difficulty when they attack you.', 0);
INSERT INTO characters(id,name,life,image,text, game_phase) VALUES (2,'Chiyome',4,'characters/chiyome.png','You can only be wounded by Weapon cards. You are not affected by Jujutsu and Battle Cry cards. You are affected normally by other Action cards, such as Tea Ceremony.',3);
INSERT INTO characters(id,name,life,image,text) VALUES (3,'Ginchiyo',4,'characters/ginchiyo.png','You take 1 wound less than normal when attacked by any Weapon, to a minimum of 1 wound. E.g., a Nodachi would only deal 2 wounds to you instead of 3, but a Shuriken still inflicts 1 wound.');
INSERT INTO characters(id,name,life,image,text, game_phase) VALUES (4,'Goemon',5,'characters/goemon.png','You may play 1 additional Weapon card during your turn. So, if you have 1 Focus in play, you may play up to 3 Weapon cards each turn.',0);
INSERT INTO characters(id,name,life,image,text) VALUES (5,'Hanzo',4,'characters/hanzo.png','You may play a Weapon card from your hand as a parry , unless it is the only card in your hand. You may use this ability even in response to cards such as Battle Cry.');
INSERT INTO characters(id,name,life,image,text) VALUES (6,'Hideyoshi',4,'characters/hideyoshi.png','You may play a Weapon card from your hand as a parry , unless it is the only card in your hand. You may use this ability even in response to cards such as Battle Cry.');
INSERT INTO characters(id,name,life,image,text, game_phase) VALUES (7,'Ieyasu',5,'characters/ieyasu.png','During your Draw Phase, you may take the top card of the discard pile as your first draw. Any other cards you draw must come from the draw deck.',2);
INSERT INTO characters(id,name,life,image,text) VALUES (8,'Kojiro',5,'characters/kojiro.png','Your Weapons can hit any Difficulty, regardless of the value on your Weapon card.');
INSERT INTO characters(id,name,life,image,text, game_phase) VALUES (9,'Musashi',5,'characters/musashi.png','If you successfully attack another character with a Weapon card, you inflict 1 additional wound. This does not apply to cards that are not Weapons, like Jujutsu.',0);
INSERT INTO characters(id,name,life,image,text) VALUES (10,'Nobunaga',5,'characters/nobunaga.png','During your Play Phase, you may discard 1 Resilience Point to draw 1 card from the deck. You may not use your last Resilience Point in this way.');
INSERT INTO characters(id,name,life,image,text) VALUES (11,'Tomoe',5,'characters/tomoe.png','Each time you successfully attack another character with a Weapon card, you draw 1 card from the deck. You only draw 1 card, even if your Weapon does more than 1 wound.');
INSERT INTO characters(id,name,life,image,text) VALUES (12,'Ushiwaka',4,'characters/ushiwaka.png','Each time you suffer a wound from a Weapon card, you draw 1 card from the deck (so 3 wounds = 3 cards).');

INSERT INTO invitation(user_sender, user_addresse, game_id) VALUES ('alefr99', 'alfcadmor', 1);
INSERT INTO invitation(user_sender, user_addresse, game_id) VALUES ('alefr99', 'DonShelby', 1);
--Hay que comentarlo porque la entidad player no funciona bien todavia
--INSERT INTO players (id, game, photo, max_hearts, current_hearts, honor, position, rol, username) VALUES (1, 'jugador1', 'foto', 5, 5, 1, 1, 5, 'admin1');

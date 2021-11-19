-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','user');


INSERT INTO users(username,password,enabled) VALUES ('alefr99','123123',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'alefr99','admin');

INSERT INTO users(username,password,enabled) VALUES ('diecrequi','diecrequi',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'diecrequi','admin');

INSERT INTO friends(id_user, id_user_friend) VALUES ('diecrequi', 'alefr99');
INSERT INTO friends(id_user, id_user_friend) VALUES ('admin1', 'diecrequi');

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

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

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
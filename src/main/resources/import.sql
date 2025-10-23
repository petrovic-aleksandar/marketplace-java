insert into appuser (id, username, password, salt, name, email, phone, balance, role, active) values (1, 'admin', '882615587793bdf29fe32e1ea466c85a6ad45920dd476aa45c58072aa044dc213d0cf8a622cc8d5ad2c49681327ad9aaf6183f6cabd6619ce6a3bcc64d2159d6', '[B@4861a26c', 'Marko Markovic', 'mm@gmail.com', '67 123 123', 0.0, 'Admin', true);
insert into appuser (id, username, password, salt, name, email, phone, balance, role, active) values (2, 'petar.petrovic', '69836c291eb16d62ed3645dd9c0ad40ad86eb239d36ad299ac9d2cf9408c64ae084071ae5cd29dbea5e947ca504601609c2864cf83ecf69ad2c53a46d05054b7', '[B@2db732d1', 'Petar Petrovic', 'pp@gmail.com', '67 100 100', 1000.0, 'User', true);
insert into appuser (id, username, password, salt, name, email, phone, balance, role, active) values (3, 'janko.jankovic', 'f2a20305e37a64a569b709b656040dbc09884b8e2f89c36480d5cb9748033c087741c5bf89a0d16900ce27f9d43ba6f180f05017d2ed38064a3bbbb252baafa6', '[B@64838816', 'Janko Jankovic', 'jj@gmail.com', '67 111 222', 500.0, 'User', true);
select setval('user_seq', 3);

insert into itemtype (id, name, description, imagePath) values (1, 'Cars&Vehicles', 'Cars and other transportation. Scooters, bikes, trains, boats, yachts, airships..', 'Categories/1.jpg');
insert into itemtype (id, name, description, imagePath) values (2, 'Home&Kitchen', 'Everything for your home, small home and kitchen electronics.', 'Categories/2.jpg');
insert into itemtype (id, name, description, imagePath) values (3, 'Electronics', 'All kinds of devices. Gaming equipment, laptops, home appliances etc.', 'Categories/3.jpg');
insert into itemtype (id, name, description, imagePath) values (4, 'Sports&Outdoors', 'Sports clothing and sport requisits.', 'Categories/4.jpg');
insert into itemtype (id, name, description, imagePath) values (5, 'Women\'s clothing', 'Everything for women.', 'Categories/5.jpg');
insert into itemtype (id, name, description, imagePath) values (6, 'Jewelry&Accesories', 'All kinds of jewelry and decorative items.', 'Categories/6.jpg');
insert into itemtype (id, name, description, imagePath) values (7, 'Mens\'s clothing', 'Clothing items for men', 'Categories/7.jpg');
insert into itemtype (id, name, description, imagePath) values (8, 'Books&Media', 'Books and stuff.', 'Categories/8.jpg');
insert into itemtype (id, name, description, imagePath) values (9, 'Services', 'For various intelectual or physical services.', 'Categories/9.jpg');
insert into itemtype (id, name, description, imagePath) values (10, 'Other', 'Whatever else.', 'Categories/10.jpg');
select setval('item_type_seq', 10);

insert into item (id, name, description, price, type_id, active, deleted, createdAt, seller_id) values (1, 'olovka', 'obicna  olovka', 1.1, 1, true, false, '2025-10-19 17:43:38.8328', 1);
insert into item (id, name, description, price, type_id, active, deleted, createdAt, seller_id) values (2, 'olovka', 'hemijska  olovka', 1.1, 1, true, false, '2025-10-19 17:43:38.8328', 2);
select setval ('item_seq', 2);
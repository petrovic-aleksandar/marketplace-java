FROM quay.io/wildfly/wildfly:latest-jdk21
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin#7rules --silent
ADD --chmod=0755 wildfly-init-config.sh /opt/jboss/wildfly/bin
ADD --chmod=0755 module.xml /opt/jboss/wildfly/modules/org/postgres/main/
ADD --chmod=0755 postgresql-42.7.8.jar /opt/jboss/wildfly/modules/org/postgres/main/
ADD --chmod=0755 standalone.xml /opt/jboss/wildfly/standalone/configuration/

COPY target/marketplace-java.war /opt/jboss/wildfly/standalone/deployments/

CMD ["/opt/jboss/wildfly/bin/wildfly-init-config.sh"]

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
select setval('item_type_seq', 2);

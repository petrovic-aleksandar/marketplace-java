FROM quay.io/wildfly/wildfly:latest-jdk21
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin#7rules --silent
ADD --chmod=0755 wildfly-init-config.sh /opt/jboss/wildfly/bin
ADD --chmod=0755 module.xml /opt/jboss/wildfly/modules/org/postgres/main/
ADD --chmod=0755 postgresql-42.7.8.jar /opt/jboss/wildfly/modules/org/postgres/main/
ADD --chmod=0755 standalone.xml /opt/jboss/wildfly/standalone/configuration/

CMD ["/opt/jboss/wildfly/bin/wildfly-init-config.sh"]

#COPY target/marketplace-java.war /opt/jboss/wildfly/standalone/deployments/

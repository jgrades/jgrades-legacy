#!/usr/bin/env

# Scratch installation script for release 0.4

# Open SSH installation
sudo apt-get install openssh-server -y
sed -i "s/PermitRootLogin without-password/PermitRootLogin yes/" /etc/ssh/sshd_config

# Network interface eth1 configuration
echo "auto eth1" >> /etc/network/interfaces
echo "iface eth1 inet dhcp" >> /etc/network/interfaces
echo "nameserver 8.8.8.8" > /etc/resolv.conf

# Java 8 installation
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt-get update -y
sudo apt-get install oracle-java8-installer -y
sudo apt-get install oracle-java8-set-default -y

# Maven 3 installation
sudo add-apt-repository ppa:timothy-downey/maven3 -y
sudo apt-get update -y
sudo apt-get install maven3 -y

# PostgreSQL installation
sudo apt-get install postgresql-9.3 -y
sed -i "s/#listen_addresses = 'localhost'/listen_addresses = '*'/" /etc/postgresql/9.3/main/postgresql.conf
sed -i "s,127.0.0.1/32,0.0.0.0/0,g" /etc/postgresql/9.3/main/pg_hba.conf

# Tomcat installation
sudo add-apt-repository ppa:dirk-computer42/c42-edge-server -y
sudo apt-get update -y
sudo apt-get install tomcat8 -y
sed -i "s/TOMCAT8_USER=tomcat8/TOMCAT8_USER=root/" /etc/default/tomcat8
sed -i "s/TOMCAT8_USER=tomcat8/TOMCAT8_USER=root/" /etc/init.d/tomcat8
sudo chown -R root /etc/tomcat8
sudo chmod -R 700 /etc/tomcat8
sudo chown -R root /usr/share/tomcat8
sudo chmod -R 700 /usr/share/tomcat8

# Remove sudo permissions for omc
sed -i "s/sudo:x:27:omc/sudo:x:27:/" /etc/group

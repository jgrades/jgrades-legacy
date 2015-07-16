#!/usr/bin/env bash

# Keys for licensing generation script for release 0.4

mkdir /etc/opt/jgrades -p
cd /etc/opt/jgrades

keystorepass=$(openssl rand -base64 32 | head -c 32)
cryptopass=$(openssl rand -base64 32 | head -c 32)
signpass=$(openssl rand -base64 32 | head -c 32)
passfile="sec.dat"

touch $passfile
echo $keystorepass >> $passfile
echo $cryptopass >> $passfile
echo $signpass >> $passfile

keytool -genseckey -keystore jg-ks.jceks -storetype JCEKS -alias jg-crypto -storepass $keystorepass -keypass $cryptopass -dname "CN=jGrades, C=PL" -validity 365 -keyalg AES -keysize 128
keytool -genkey -keystore jg-ks.jceks -storetype JCEKS -alias jg-signature -storepass $keystorepass -keypass $signpass -dname "CN=jGrades, C=PL" -validity 365 -keyalg RSA -keysize 4096

#!/usr/bin/env bash

# install BOM in local mvn repository first
cd jg-bom
mvn clean install
cd ..
# Project "Bank System"

*Java MIPT 2022*

>Authors
>>Kalinin Ivan
>
>>Zaretskaia Mariia

Current status: `version 2.0 is available`


## What's new

>1. Site design is changed
>2. My profile
>3. Navigation bar
>4. FAQ and About us pages
>5. Jar distribution


## Run Settings

*To run the application visit [http://localhost:3333](http://localhost:3333/)*

## Requirements

- MySQL database `banksys`

## Setting the MySQL database on MacOS

If you use MAMP as we suggested then the solution of this problem is to change `application.properties`:

before:
```properties
server.port=3333

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/banksys
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true

management.endpoint.restart.enabled=true
```

after:
```properties
server.port=3333

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:8889/banksys
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true

management.endpoint.restart.enabled=true
```

then go to page [phpMyAdmin](http://localhost/phpMyAdmin/) and create a `banksys` database

Or install MySQL server and run 

```console
$ mysql> CREATE DATABASE banksys;
```
Use user root with password root and database port 3306

## Setting the MySQL database on Ubuntu (use same commands on other Linux OS)

```console
$ sudo apt upgrade
$ sudo apt install mysql-server
$ sudo apt install mysql-client
#Start server
$ sudo service mysql start
#Log in to MySQL server
$ sudo mysql -u root -p
#Then enter password - root
#In MySQL 
$ mysql> CREATE DATABASE banksys;
#To exit MySQl
$ mysql> \q
```

# Project "Bank System"

*Java MIPT 2022*

>Authors
>>Kalinin Ivan
>
>>Zaretskaya Mariya

Current status: `in progress`

## Run Settings

*To gun the application visit [http://localhost:3333](http://localhost:3333/)*

##Requirements

- MySQL database

>What we suggest doing
> >Use MAMP app to run your local MySQL server
> >
> >Then open [phpMyAdmin](http://localhost/phpMyAdmin/sql.php) and create new database called `banksys`
> >
> >That is all

##FAQ

> I'm a Mac user and there are problems with database connection. What should I do?

The project developers used Window OS.
It means that the project is configured according to Windows settings.

If you use MAMP as we suggest then the solution of this problem is to change `application.properties`:

was:
```properties
server.port=3333

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/banksys
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true
```

new:
```properties
server.port=3333

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:8889/banksys
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true
```

# Project "Bank System"

*Java MIPT 2022*

>Authors
>>Kalinin Ivan
>
>>Zaretskaia Mariia

Current status: `version 1.0 is available`

## Run Settings

*To run the application visit [http://localhost:3333](http://localhost:3333/)*

##Requirements

- MySQL database

>What we suggest doing
> >Use MAMP app to run your local MySQL server
> >
> >Then open [phpMyAdmin](http://localhost/phpMyAdmin/) and create a new database called `banksys`
> >
> >That is all

##FAQ

> I'm a macOS user and there are problems with database connection. What should I do?

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

> I'm a Linux user and there are problems with database connection. What should I do?

To solve the problem you should install phpMyAdmin and configure it.
After this you should go to phpMyAdmin page and create a `banksys` database and change `application.properties:

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
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:<DatabasePort>/banksys
spring.datasource.username=<UserName>
spring.datasource.password=<UserPassword>
spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.jpa.show-sql: true

management.endpoint.restart.enabled=true
```

where
- `<UserName>` - user's name which you used to log in to phpMyAdmin
- `<UserPassword>` - user's password
- `<DatabasePort>` - connection port to database (find it at phpMyAdmin page)
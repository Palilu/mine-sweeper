# Mine Sweeper API

### Notes

I'm using [Project Lombok](https://projectlombok.org/). In order to see the code properly, you might need to install a plugin for your IDE of choice that can be found [here](https://projectlombok.org/setup/overview). 

 ### Running Mine Sweeper API

##### Pre-requisites
* Java 16.
* Maven.

1.1. Open terminal, go to the project's folder.

1.2. Run `mvn clean install`.

1.3. Run `mvn spring-boot:run`.

### Testing Mine Sweeper API

2.1 You can use [Mine Sweeper API Swagger](http://localhost:8080/swagger-ui.html)

2.2 If you want to access the database you can go [here](http://localhost:8080/h2-console/) and log in using:
    
    JDBC_URL = "jdbc:h2:mem:testdb"
    User Name = "sa"
    Password = ""

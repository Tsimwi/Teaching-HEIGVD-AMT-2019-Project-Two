# APIs implementation

## Common on both APIs

### Swagger and bottom-up

First we had to define our APIs using swagger and a YAML file. From this file we were able to generate JAVA code using the "spring-fox" plugin, this technique is called "Bottom-up". Here is an example of the swagger file:

```yaml
swagger: '2.0'
info:
  description: First API specifications.
  version: '1.0'
  title: AMT Account Manager
host: localhost:8080
basePath: /api
schemes:
  - http
securityDefinitions:
  Bearer:
    type: apiKey
    name: Authorization
    in: header

paths:
  /authentication:
    post:
      description: Authenticate a user.
      operationId: authenticateUser
      consumes:
        - application/json
      parameters:
        - name: credentials
          in: body
          required: true
          schema:
            $ref: '#/definitions/UserCredentials'
      responses:
        200:
          description: success
          schema:
            description: JWT Token
            type: string
        400:
          description: Invalid email/password supplied.

```

### JWT

Some endpoints need to be protected, we want that only authenticated users use our APIs. The only endpoint that is not protected is the `authentication` endpoint. When a user authenticate himself, on this endpoint, a token will be created and signed, with a secret, by the server. To proceed to the verification we have set up a filter on others endpoint, this filter is responsible of the verification of the token. On the filter we used the dependence injection on the `TokenImplementation` with the annotation `@Autowired` of spring. The filter extract the value of the `Authorization` header and then give this value to the service. The service verify if the token is still valid, with claims `IssuedAt` and `ExpiresAt`, and if the signature is correct.

### JPA

We used _Spring Data JPA_ to manage our data persistence. We simply add the annotation `@Entity` on the class that define the _entity_ that we want to store in the database. We also need to give which attribute is the primary key:

```java
    @Entity
	public class UserEntity implements Serializable {
        @Id
        private String mail;

        private String firstName;
        private String lastName;
        private String password;
        private String role;
        
        //getter and setter ...
    }
```

With some properties (`spring.jpa.hibernate.ddl-auto`) on the file _application.properties_ of _Spring_ you can chose what JPA should do when the project is start : 

- *validate*: validate the schema, makes no changes to the database.
- *update*: update the schema.
- *create*: creates the schema, destroying previous data.
- *create-drop*: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.

It's also on this file that you give the URL, the user and the password of the database :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/project_two
spring.datasource.username=<user>
spring.datasource.password=<password>
```

JPA provide also an API to interact with _Hibernate ORM_. WIth this ORM, database request are very simple. Some request are already defined in the interface `CrudRepository`, if we want custom request we have to create an interface and write the signature of the request, for example to find a user with his mail we write `UserEntity findByMail(String mail);` but we never write the class, it's hibernate that will parse the function's name and make the request.

## Authentication API

## Application API


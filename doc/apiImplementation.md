# APIs implementation

## Common on both APIs

### Swagger and bottom-up

First we had to define our APIs using swagger and an YAML file. From this file we were able to generate JAVA code using the "spring-fox" plug in, this technique is called "top-down". Here is an example of the swagger file:

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

Some endpoints need to be protected, we want that only authenticated users use our APIs. The only endpoint that is not protected is the `authentication` endpoint. When an user authenticate himself, on this endpoint, a token will be created and signed, with a secret, by the server. To proceed to the verification we have set up a filter on others endpoint, this filter is responsible of the verification of the token. In the filter class we used the dependence injection on the class `TokenImplementation` with the annotation `@Autowired` of spring. The filter extract the value of the `Authorization` header and then give this value to the service. The service verify if the token is still valid, with claims `IssuedAt` and `ExpiresAt`, and if the signature is correct (both api share a secret to verify signature).

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

JPA provide also an API to interact with _Hibernate ORM_. With this ORM, database request are very simple. Some request are already defined in the interface `CrudRepository`, if we want custom request we have to create an interface and write the signature of the method, for example to find an user with his mail we write `UserEntity findByMail(String mail)`. But we never write the code of the method, it's _Hibernate_ that will parse the function's name and make the request.

### Dependency injection

Like in the first project, we use _dependency injection_. To use _dependency injection_ in _Spring_ you have to add an annotation on the class that you want to inject, the annotation is `@Component`.

```java
public class UnicornsService {
//...
}
```

 And in the class where you want to inject the component you have to define it as a local variable with the annotation `@Autowired`

```java
@Controller
public class UnicornsApiController implements UnicornsApi {

    @Autowired
    UnicornsService unicornsService;
    
    public ResponseEntity<Void> addUnicorn(...) {
        try {
            return unicornsService.addUnicorn(...);
        } catch (ApiException exception) {
            return new ResponseEntity<>(HttpStatus.valueOf(exception.getCode()));
        }
    }
    //...
}
```



## Authentication API

### JWT creation

As we said before we have to create a JWT, this operation is done when an user login.

```java
Algorithm algorithmHS = Algorithm.HMAC256("secret");
Date now = new Date();

/* 24 hours of validity */
Date expiration = new Date(now.getTime() + (24 * 3600 * 1000));
return JWT.create()
    .withSubject(userEntity.getMail())
    .withIssuer("auth-server")
    .withIssuedAt(now)
    .withExpiresAt(expiration)
    .withClaim("role", userEntity.getRole())
    .sign(algorithmHS);
```

In the token we store the ID of the user, his role, when the token has been created and when it expire. And after that we sign the token.

## Application API

### Paging

Spring and JPA give the tool to implement paging, indeed when we create the signature of a method in the file that extends from `CrudRepository` we simply have to change the extends to `PagingAndSortingRepository` and then we add a parameter to the method, an object `Pageable`.

For example `List<UnicornEntity> getUnicornEntitiesByEntityCreator(String entity_creator, Pageable pageable);`

And when we call this method we pass the parameter `Pageable` as `PageRequest.of(pageNumber - 1, numberPerPage)`. And that's how we implemented paging.


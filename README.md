# README.md of project 'ragdemo'

## secrets handling

you have to create a file `secrets/secrets.properties` containing the following secrets:

The keys of that file will be **modified* and set as environment variables:
- `'-'` will be replaced by `'_'`
- `'.'` will be replaced by `'__'`

`<root>/secrets/secret.properties` might look like:

```properties
# build.gradle.kts will set these as environment variables
# '-' will be replaced by '_'
# '.' will be replaced by '__'
spring.ai.openai.api-key=xyz
spring.ai.openai.chat.api-key=xyz
db.postgres.dbname=mydatabase
db.postgres.user=myuser
db.postgres.password=hoffi
```

## building

`./gradlew build`

## running

`./gradlew bootRun`

to run against containerized instances of e.g. postgresql DB:

`./gradlew bootTestRun`

`curl localhost:8080/...`<br>
`http://localhost:9090/actuator/swagger-ui/index.html`

## local dev postgresql DB

for development a postgresql is spun up in docker via `<root>/compose.yml`

(by having gradle dependency `developmentOnly("org.springframework.boot:spring-boot-docker-compose")` on the classpath)

The dbname, username and password are passed as environment variables (set in `<root>/secrets/secrets.properties`)

you can login to it with e.g.: `psql --host=localhost --port=15432 --username=myuser mydatabase`<br>
using the secret from `secrets.properties`

## Tests

### Cucumber Gherkin

#### Cucumber Gherkin glue code

### Testcontainers Databases

defined (incl. user, password and dbname) in `TestcontainersConfiguration.java`

## todo

- make testcontainers run and document
  - use containers for db to run standalone with bootrun
- make cucumber/gherkin tests run
- refactor and document how tests work
- make json openApi actuator endpoint work and document it
- 

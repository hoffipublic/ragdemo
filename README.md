# README.md of project 'ragdemo'

## secrets handling

you have to create a file `secrets/secrets.properties` containing the following secrets:

```properties
spring.ai.openai.api-key=xyz
spring.ai.openai.chat.api-key=xyz
```

## building

`./gradlew build`

## running

`./gradlew bootRun`

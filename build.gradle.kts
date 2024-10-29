import java.util.*

plugins {
    java
    application
    alias(libs.plugins.org.springframework.boot)
    alias(libs.plugins.io.spring.dependencyManagement)
}

group = "com.hoffi.ai"
version = "0.0.1-SNAPSHOT"
val artifactName: String by extra { "${rootProject.name.lowercase()}" } // by extra { "${rootProject.name.lowercase()}-${project.name.lowercase()}" }
val rootPackage: String by extra { "${rootProject.group}.${project.name.lowercase()}" } // by rootProject.extra
val projectPackage: String  by extra { "${rootPackage}" }// by extra { "${rootPackage}.${project.name.lowercase()}" }

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

fun <T> ProviderConvertible<T>.v() = this.asProvider().get()
fun <T> Provider<T>.v(): T = get()

extra["springAiVersion"] = "1.0.0-M3"

dependencyManagement {
    imports { mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}") }
}

dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.ai:spring-ai-openai")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.ai:spring-ai-markdown-document-reader")
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.ai:spring-ai-tika-document-reader")
    implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
    // <root>/src/java/resources/db/migration/*.sql
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    //implementation("org.springdoc:springdoc-openapi-ui:${libs.versions.springdoc.openapi.ui.v()}")
    // http://server:port/context-path/swagger-ui.html and the OpenAPI description url for json format: http://server:port/context-path/v3/api-docs
    // and if exposing via actuator (in application.yml)
    // http://localhost:9090/actuator/swagger-ui/index.html
    // http://localhost:9090/actuator/openapi/springdocDefault
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${libs.versions.springdoc.openapi.starter.webmvc.ui.v()}")
    //implementation("org.springdoc:springdoc-openapi-starter-webflux-api:${libs.versions.springdoc.openapi.starter.webmvc.ui.v()}")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    // picks up <root>/compose.yaml
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:json-path")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.ai:spring-ai-spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    testImplementation("io.cucumber:cucumber-java:${libs.versions.cucumber.java.v()}")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:${libs.versions.cucumber.java.v()}")
    testImplementation("io.cucumber:cucumber-spring:${libs.versions.cucumber.java.v()}")

    testImplementation("org.junit.platform:junit-platform-suite")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val secrets: Properties = Properties().apply {
    load(rootProject.file("secrets/secrets.properties").reader())
}
fun setEnvironmentVariablesFromFiles(secrets: Properties, theScope: JavaExec) {
    val errors: MutableList<String> = mutableListOf()
    val neededSecretKeys = mutableMapOf(
        Pair("spring.ai.openai.api-key", false),
        Pair("spring.ai.openai.chat.api-key", false),
    )
    theScope.apply {
        environment("DB_HOST", "https://nowhere")
        //environment("spring__ai__openai__api_key", "<HARDCODED_APIKEY>")
        //environment("spring__ai__openai__chat__api_key", "<HARDCODED_CHAT_APIKEY>")
        for (prop in secrets) {
            if ("${prop.value}".isBlank()) { errors.add("secrets/secrets.properties key `${prop.key}` is illegal to be empty") }
            neededSecretKeys["${prop.key}"] = true
            environment("${prop.key}".replace("-", "_").replace(".", "__"), "${prop.value}")
        }
        if (errors.isNotEmpty()) {
            throw GradleException(errors.joinToString("\n", "\n", "\n"))
        }
        val missingSecretKeys = neededSecretKeys.filterValues { !it }.keys
        if (missingSecretKeys.isNotEmpty()) {
            throw GradleException("secrets/secrets.properties missing keys:${missingSecretKeys.joinToString("\n", "\n", "\n")}")
        }
    }
}
val bootRun by tasks.getting(JavaExec::class) {
    setEnvironmentVariablesFromFiles(secrets, this)
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    // named<JavaExec>("run") {
    //     // needed if App wants to read from stdin
    //     standardInput = System.`in`
    // }
    withType<Jar> {
        archiveBaseName.set(artifactName)
    }
    // shadowJar {
    //     // manifest { attributes["Main-Class"] = theMainClass }
    //     mergeServiceFiles()
    //     minimize()
    //     doLast {
    //         delete(project.tasks.jar.get().outputs)
    //     }
    // }
}

import org.gradle.model.internal.core.ModelNodes.withType

plugins {
    java
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

extra["springAiVersion"] = "1.0.0-M3"

dependencyManagement {
    imports { mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}") }
}

dependencies {
    // Replace the following with the starter dependencies of specific modules you wish to use
    implementation("org.springframework.ai:spring-ai-openai")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.ai:spring-ai-markdown-document-reader")
    implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    developmentOnly("org.springframework.ai:spring-ai-spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.ai:spring-ai-spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

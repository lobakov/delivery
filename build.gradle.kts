import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.openapi.generator") version "7.5.0"
    id("org.sonarqube") version "3.2.0"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    jacoco
}

group = "com.github.lobakov.delivery"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

val springBootVersion = "3.2.4"
val mapstructVersion = "1.5.5.Final"

repositories {
    mavenLocal()
    mavenCentral()
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$projectDir/src/main/resources/ui.contract/http-api.yml")
    outputDir.set("$buildDir/generated/openapi")
    apiPackage.set("com.github.lobakov.delivery.api.adapters.http.contract")
    modelPackage.set("com.github.lobakov.delivery.api.adapters.http.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "library" to "spring-boot",
            "openApiNullable" to "false",
            "useSpringBoot3" to "true"
        )
    )
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/openapi/src/main/java")
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.mapstruct:mapstruct-processor:$mapstructVersion")

    implementation("org.liquibase:liquibase-core:4.28.0")
    implementation("org.postgresql:postgresql:42.7.3")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.6.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.7")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.vintage:junit-vintage-engine:5.9.2")
    testImplementation("com.h2database:h2:2.2.224")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
    dependsOn(tasks.openApiGenerate)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.bootJar {
    enabled = true
}

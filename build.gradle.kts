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
val liquiBaseVersion = "4.28.0"
val postgresVersion = "42.7.3"
val openApiStarterVersion = "2.5.0"
val openApiGeneratorVersion = "7.6.0"
val swaggerAnnotationsVerion = "2.2.7"
val openApiKotlinVersion = "1.8.0"
val h2Version = "2.2.224"

repositories {
    mavenLocal()
    mavenCentral()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
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
            srcDirs("$buildDir/generated/openapi/src/main/kotlin")
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.mapstruct:mapstruct-processor:$mapstructVersion")

    implementation("org.liquibase:liquibase-core:$liquiBaseVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiStarterVersion")
    implementation("org.openapitools:openapi-generator-gradle-plugin:$openApiGeneratorVersion")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerAnnotationsVerion")
    runtimeOnly("org.springdoc:springdoc-openapi-kotlin:$openApiKotlinVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
    testImplementation("com.h2database:h2:$h2Version")
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

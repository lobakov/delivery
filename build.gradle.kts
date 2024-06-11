import com.google.protobuf.gradle.id
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.openapi.generator") version "7.5.0"
    id("com.google.protobuf") version "0.9.4"
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.spring") version "1.9.10"
    kotlin("plugin.jpa") version "1.9.10"
    jacoco
}

group = "com.github.lobakov.delivery"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

val coroutinesVersion = "1.8.1"
val springBootVersion = "3.2.4"
val mapstructVersion = "1.5.5.Final"
val liquiBaseVersion = "4.28.0"
val postgresVersion = "42.7.3"
val openApiStarterVersion = "2.5.0"
val openApiGeneratorVersion = "7.6.0"
val swaggerAnnotationsVerion = "2.2.15"
val openApiKotlinVersion = "1.8.0"
val h2Version = "2.2.224"

val protocVersion = "3.18.1"
val grpcStarterVersion = "5.1.5"
val protocGenVersion = "1.4.1"
val grpcKotlinStubVersion = "1.4.0"
val grpcVersion = "1.57.2"
val protobufKotlinVersion = "3.25.1"

repositories {
    mavenLocal()
    mavenCentral()
}

protobuf {

    protoc {
        artifact = "com.google.protobuf:protoc:$protocVersion:osx-aarch_64"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion:osx-aarch_64"
        }

        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$protocGenVersion:jdk8@jar"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach { task ->
            task.plugins {
                id("grpc")
                id("grpckt")
            }

            task.builtins {
                id("kotlin")
            }
        }
    }
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
        proto {
            srcDirs("src/main/kotlin/com/github/lobakov/delivery/infrastructure/adapters/grpc/proto")
            srcDirs("src/main/kotlin/com/github/lobakov/delivery/api/adapters/kafka/basket/confirmed/proto")
            srcDirs("src/main/kotlin/com/github/lobakov/delivery/infrastructure/adapters/kafka/order/status/changed/proto")
        }

        kotlin {
            srcDirs("$buildDir/generated/openapi/src/main/kotlin")
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    implementation("org.springframework.kafka:spring-kafka")

    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinStubVersion")
    implementation("io.grpc:protoc-gen-grpc-kotlin:$protocGenVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$protobufKotlinVersion")
    implementation("io.github.lognet:grpc-spring-boot-starter:$grpcStarterVersion")

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

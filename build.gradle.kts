plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.0"
    id("maven-publish")
    id("com.adarshr.test-logger") version "3.2.0"
}


group = "ru.realm-weavers"
version = "0.0.4"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")

    // SpringBoot starters
    api("org.springframework.boot:spring-boot-starter:3.1.0")
    api("org.springframework.boot:spring-boot-starter-web:3.1.0")
    api("org.springframework.boot:spring-boot-starter-webflux:3.1.0")
    api("org.springframework:spring-webflux:6.0.0")
    api("org.springframework.boot:spring-boot-starter-validation:3.1.0")

    // Jackson
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0")

    // Log

    api("org.slf4j:slf4j-api:2.0.7")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}

publishing {
    publications {
        create<MavenPublication>("package-publisher") {
            groupId = "ru.realm-weavers"
            artifactId = "polinations-adapter"
            version = "0.0.4"
            // Укажите путь к вашему артефакту здесь
            artifact("${layout.buildDirectory.get()}/libs/${artifactId}-${version}.jar")
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Ps4on1k/polinations-adapter")
            credentials {
                username = project.findProperty("gpr.user").toString()
                password = project.findProperty("gpr.token").toString()
            }
        }
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

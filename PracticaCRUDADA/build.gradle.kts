import org.gradle.kotlin.dsl.testImplementation


plugins {
    kotlin("jvm") version "1.9.10"
    val kotlinVersion = "1.9.22"
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version "2.0.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("org.hibernate.orm:hibernate-core:6.6.0.Final")
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:9.0.0")

}

tasks.test {
    useJUnitPlatform()
}
plugins {
    id("base")
    id("org.sonarqube") version "3.3"
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

allprojects {
    version = extra["giskardVersion"]!!
}

sonarqube {
    properties {
        property("sonar.organization", "giskard")
        property("sonar.host.url", "https://sonarcloud.io")

        property("sonar.projectKey", "giskard")
        property("sonar.projectName", "Giskard")
        property("sonar.projectVersion", version.toString())
    }
}
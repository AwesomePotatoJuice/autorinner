plugins {
    id("application")
}

group = "ru.surin"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.melloware:jintellitype:1.4.1")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("ru.surin.Main")
}

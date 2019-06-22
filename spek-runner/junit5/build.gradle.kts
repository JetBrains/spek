plugins {
    kotlin("jvm")
    `maven-publish`
}

dependencies {
    implementation(project(":spek-runtime"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(Dependencies.junitPlatformEngine)

    testImplementation(Dependencies.hamkrest)
    testImplementation(Dependencies.junitJupiterApi)
    testImplementation(Dependencies.mockitoKotlin)
    testImplementation(Dependencies.junitPlatformLauncher)

    testRuntimeOnly(Dependencies.mockitoInline)
    testRuntimeOnly(Dependencies.junitJupiterEngine)
}

val baseName = "spek-runner-junit5"

tasks {
    test {
        useJUnitPlatform {
            excludeEngines("spek2")
        }
    }

    jar {
        archiveBaseName.value(baseName)
    }
}

val sourceJar by tasks.registering(Jar::class) {
    archiveBaseName.value(baseName)
    archiveClassifier.value("sources")
    from(sourceSets.main.get().java)
}

val stubJavaDocJar by tasks.registering(Jar::class) {
    archiveClassifier.value("javadoc")
}

publishing {
    publications {
        register("maven", MavenPublication::class) {
            groupId = "org.spekframework.spek2"
            artifactId = "spek-runner-junit5"
            from(components["java"])
            artifact(sourceJar.get())
            artifact(stubJavaDocJar.get())
        }
    }
}

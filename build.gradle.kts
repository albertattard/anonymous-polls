allprojects {
    repositories {
        mavenLocal()
        jcenter()
    }
}

subprojects {
    version = "1.0"
}

task("copyFrontendResources") {
    group = "build"
    description = "Copy frontend resources into backend"
    dependsOn(":frontend-open-wc:npm-build")

    doFirst {
        copy {
            from("./frontend-open-wc/dist")
            into("./backend-micronaut/generated/main/resources/web")
        }
    }
}

task("assembleAll") {
    group = "build"
    description = "Build combined backend & frontend into one JAR"
    dependsOn("copyFrontendResources", ":backend-micronaut:shadowJar")
}

defaultTasks("clean", "ktlintFormat", "dependencyUpdates", "test")

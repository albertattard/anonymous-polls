import com.moowork.gradle.node.npm.NpmTask

plugins {
  id("com.github.node-gradle.node").version("1.3.0")
}

node {
  version = "12.12.0"
  distBaseUrl = "https://nodejs.org/dist"
  download = true
}

task("npm-start", NpmTask::class) {
  group = "Application"
  description = "Run the frontend application in development mode"
  setNpmCommand("run-script", "start")
}

task("npm-build", NpmTask::class) {
  group = "build"
  description = "Build the frontend application"
  setNpmCommand("run-script", "build")
}

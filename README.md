# Anonymous Polls

One of the challenges we faced in our team was, gathering of anonymous feedback, such as polls.  There are many applications available on the web but none were approved by either our company or our project.  Given that I am on [kurzarbeit](https://de.wikipedia.org/wiki/Kurzarbeit), decided to develop an anonymous poll application using new emerging technologies, such as (listed in no particular order):

1. [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference designed to interoperate fully with Java, and the JVM version of its standard library depends on the Java Class Library, but type inference allows its syntax to be more concise
1. [Rust](https://www.rust-lang.org/) (*coming soon*) - a language empowering everyone
to build reliable and efficient software.
1. [Jetbrains Exposed](https://github.com/JetBrains/Exposed) - a lightweight SQL library written for Kotlin language. It does have two layers of database access: typesafe SQL wrapping DSL and lightweight data access objects
1. [Micronaut](https://micronaut.io/) - a modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless applications.
1. [Quarkus](https://quarkus.io/) (*coming soon*) - a Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards
1. [Helidon](https://helidon.io/) (*coming soon*) - a collection of Java libraries for writing microservices that run on a fast web core powered by Netty.
1. [Open WebComponents](https://open-wc.org/) - a community-effort, independent of any framework or company. We use mostly open-source tools and services
1. [Tailor](https://github.com/zalando/tailor) (*coming soon*) - a layout service that uses streams to compose a web page from fragment services.
1. [GraalVM](https://www.graalvm.org/) (*coming soon*) - a universal virtual machine for running applications written in JavaScript, Python, Ruby, R, JVM-based languages like Java, Scala, Groovy, Kotlin, Clojure, and LLVM-based languages such as C and C++
1. [Gauge](https://docs.gauge.org/) (*work in progress*) - a free and open source test automation framework that takes the pain out of acceptance testing
1. [Taiko](https://github.com/getgauge/taiko) (*work in progress*) - a free and open source browser automation tool built by the team behind [Gauge](https://docs.gauge.org/) from [ThoughtWorks](https://www.thoughtworks.com/).  Taiko is a [Node.js](https://nodejs.org/) library with a clear and concise API to automate [Chromium](https://www.chromium.org/) based browsers.
1. [JDepend](https://github.com/clarkware/jdepend) (*coming soon*) - traverses Java class and source file directories and generates design quality metrics for each Java package.  JDepend allows you to automatically measure the quality of a design in terms of its extensibility, reusability, and maintainability to effectively manage and control package dependencies.
1. [ArchUnit](https://www.archunit.org/) (*coming soon*) - a free, simple and extensible library for checking the architecture of your Java code using any plain Java unit test framework. That is, ArchUnit can check dependencies between packages and classes, layers and slices, check for cyclic dependencies and more. It does so by analyzing given Java bytecode, importing all classes into a Java code structure.
1. [pitest](https://pitest.org/) (*coming soon*) - a mutation testing system, providing gold standard test coverage for Java and the jvm.  It's fast, scalable and integrates with modern test and build tooling.  **This does not work well with Kotlin**.

### In the Horizon

1. [Lerna](https://github.com/lerna/lerna)

While technology can make the project fun, the emphasis of this project is to produce anonymous polls.  No authentication is required, yet it provides the following features:

1. Create polls
1. Invite others to collaborate (edit the polls)
1. Invite others to participate in the poll
1. Generate a summary while keeping it all anonymous

An [in-memory database](https://www.h2database.com/) is used simply to deal with concurrency through transactions.  The database exists only in memory and is disposed from once the service is restarted.

## Sub-Projects

Different technologies can be used to produce the same value.

1. [backend-micronaut](backend-micronaut)
1. [backend-quarkus](backend-quarkus) (*coming soon*)
1. [backend-helidon](backend-helidon) (*coming soon*)
1. [frontend-open-wc](frontend-open-wc)
1. [journey-test-gauge-taiko](journey-test-gauge-taiko) (*work in progress*)

Hopefully this list will keep growing to include more technologies.

## Run Locally

All commands shown next are to be executed from within the `anonymous-polls` folder

1. Run tests for all projects

    ```bash
    $ ./gradlew test
    ```

1. Build both frontend and backend

    ```bash
    $ ./gradlew assembleAll
    ```

1. Run the application locally

    ```bash
    $ java -jar backend-micronaut/build/libs/backend-micronaut-1.0-all.jar
    ```

    This will start the [backend-micronaut](backend-micronaut) together with the [frontend-open-wc](frontend-open-wc).

1. Access the application [http://localhost:8080/](http://localhost:8080/)

    ![Coming Soon](docs/images/Coming%20Soon.png)

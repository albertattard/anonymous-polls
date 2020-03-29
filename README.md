# Anonymous Polls

One of the challenges we faced in our team was, gathering of anonymous feedback, such as polls.  There are many applications available on the web but none were approved by either our company or our project.  Given that I am on [kurzarbeit](https://de.wikipedia.org/wiki/Kurzarbeit), decided to develop an anonymous poll application using new emerging technologies, such as

1. [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference designed to interoperate fully with Java, and the JVM version of its standard library depends on the Java Class Library, but type inference allows its syntax to be more concise.
1. [Jetbrains Exposed](https://github.com/JetBrains/Exposed) - a lightweight SQL library written for Kotlin language. It does have two layers of database access: typesafe SQL wrapping DSL and lightweight data access objects.
1. [Micronaut](https://micronaut.io/) - a modern, JVM-based, full-stack framework for building modular, easily testable microservice and serverless applications.
1. [Quarkus](https://quarkus.io/) (*coming soon*) - a Kubernetes Native Java stack tailored for OpenJDK HotSpot and GraalVM, crafted from the best of breed Java libraries and standards.
1. [Helidon](https://helidon.io/) (*coming soon*) - a collection of Java libraries for writing microservices that run on a fast web core powered by Netty.
1. [Open WebComponents](https://open-wc.org/) - a community-effort, independent of any framework or company. We use mostly open-source tools and services.
1. [GraalVM](https://www.graalvm.org/) (*coming soon*) - a universal virtual machine for running applications written in JavaScript, Python, Ruby, R, JVM-based languages like Java, Scala, Groovy, Kotlin, Clojure, and LLVM-based languages such as C and C++

While technology can make the project fun, the emphasis of this project is to produce anonymous polls.  No authentication is required, yet it provides the following features:

1. Create polls
1. Invite others to collaborate (edit the polls)
1. Invite others to participate in the poll
1. Generate a summary while keeping it all anonymous

An [in-memory database](https://www.h2database.com/html/main.html) is used simply to deal with concurrency through transactions.  The database exists only in memory and is disposed from once the service is restarted. 

## Sub-Projects

Different technologies can be used to produce the same value.

1. [backend-micronaut](backend-micronaut/README.md)
1. [backend-quarkus](backend-quarkus/README.md) (*coming soon*)
1. [backend-helidon](backend-helidon/README.md) (*coming soon*)
1. [frontend-open-wc](frontend-open-wc/README.md)

Hopefully this list will keep growing to include more technologies.

## Run Locally

1. Build both frontend and backend

    ```bash
    $ ./gradlew assembleAll
    ```

1. Run the application locally

    ```bash
    $ java -jar backend-micronaut/build/libs/backend-micronaut-1.0-all.jar
    ```

1. Access the application [http://localhost:8080/](http://localhost:8080/)

    ![Coming Soon](docs/images/Coming%20Soon.png)

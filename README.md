# Anonymous Polls

One of the challenges we faced in our team was, gathering of anonymous feedback, such as polls.  There are many applications available on the web but none were approved by either our company or our project.  Given that I am on [kurzarbeit](https://de.wikipedia.org/wiki/Kurzarbeit), decided to develop an anonymous poll application using new emerging technologies, such as

1. [Kotlin](https://kotlinlang.org/)
1. [Micronaut](https://micronaut.io/)
1. [Open WebComponents](https://open-wc.org/) 

While technology can make the project fun, the emphasis of this project is to produce anonymous polls.  No authentication is required, yet it provides the following features:

1. Create polls
1. Invite others to collaborate (edit the polls)
1. Invite others to participate in the poll
1. Generate a summary while keeping it all anonymous

An [in-memory database](https://www.h2database.com/html/main.html) is used simply to deal with concurrencies through transactions.  The database exists only in memory and is lost once the service is restarted.

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

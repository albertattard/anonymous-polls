# Anonymous Polls build with Open WebComponents Standards

## Pending Tasks

1. We need to somehow inject `PollGateway` into `AnonymousPoll`.

    As is, the `AnonymousPoll` is hardcoded to use `PollGateway` and tests are producing unnecessary warnings.

    ```
    29 03 2020 12:59:21.030:WARN [web-server]: 404: /poll/count
      Anonymous Poll
        ✔ renders a h1
    29 03 2020 12:59:21.046:WARN [web-server]: 404: /poll/count
        ✔ passes the a11y audit
    29 03 2020 12:59:21.104:WARN [web-server]: 404: /poll/count
        ✔ displays an error when there is an error
    29 03 2020 12:59:21.110:WARN [web-server]: 404: /poll/count
        ✔ displays a custom message when total is 0
    29 03 2020 12:59:21.120:WARN [web-server]: 404: /poll/count    
        ✔ displays the number of polls when total is greater than 0
    ```

    There is no point in making an HTTP request during some of the tests.

1. Issue with eslint

    There are several [routing options](https://open-wc.org/developing/routing.html).
    
    ```
    anonymous-polls/frontend-open-wc/src/poll-router.js
      35:7  error  Assignment to property of function parameter 'active'  no-param-reassign
      38:7  error  Assignment to property of function parameter 'active'  no-param-reassign
      42:9  error  Assignment to property of function parameter 'active'  no-param-reassign
      45:9  error  Assignment to property of function parameter 'active'  no-param-reassign
    ```
    Tried [lit-element-router](https://github.com/hamedasemi/lit-element-router) and worked well with a small exception.  This router uses the path (anything after the host:port `https://some.where/routing-path`).  I was not able to make this work well with [Micronaut Single-Page-App (SPA)](https://guides.micronaut.io/micronaut-spa-react/guide/index.html) routing options.

# Anonymous Polls Journey tests with Gauge and Taiko

## Pending Tasks

1. We are not able to properly test Web Components/ShadowDOM with Taiko.

    I've reached to Scott Davis ([Website](https://thirstyhead.com/) | [LinkedIn](https://www.linkedin.com/in/scottdavis99/)) and he told me that this is a known issue within the [Taiko](https://gauge.org/gauge-taiko/) community: [https://github.com/getgauge/taiko/issues/952](https://github.com/getgauge/taiko/issues/952).

    Scott also mentioned that [Puppeteer](https://github.com/puppeteer/puppeteer) struggles with the same issue: [https://github.com/puppeteer/puppeteer/issues/4171](https://github.com/puppeteer/puppeteer/issues/4171).  

    Scott managed to hacked something together using `page.evaluateHandle` as shown at the bottom of this page: [https://github.com/puppeteer/puppeteer/issues/858](https://github.com/puppeteer/puppeteer/issues/858).

## Setup

1. Install NPM, if not already installed

    ```bashs
    $ brew install npm
    ```

1. Install [Gauge](https://gauge.org/) globally, if not already installed

    ```bash
    $ npm install -g @getgauge/cli
    ```

1. Install [Taiko](https://github.com/getgauge/taiko) globally, if not already installed

    ```bash
    $ npm install -g taiko
    ```

1. Update all the installed plugins

    ```bash
    $ gauge update --all
    ```

    I had to run this command to get the tests running, as I was getting the following problem.

    ```bash
    /Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/grpc_extension.js:55
    throw error;
    ^
    Error: Failed to load gRPC binary module because it was not installed for the current system
    Expected directory: node-v79-darwin-x64-unknown
    Found: [node-v64-darwin-x64-unknown]
    This problem can often be fixed by running "npm rebuild" on the current system
    Original error: Cannot find module '/Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/node/extension_binary/node-v79-darwin-x64-unknown/grpc_node.node'
    Require stack:
    - /Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/grpc_extension.js
    - /Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/client_interceptors.js
    - /Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/client.js
    - /Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/index.js
    - /Users/albertattard/.gauge/plugins/js/2.3.5/src/gauge.js
    at Object.<anonymous> (/Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/grpc_extension.js:53:17)
    at Module._compile (internal/modules/cjs/loader.js:1147:30)
    at Object.Module._extensions..js (internal/modules/cjs/loader.js:1167:10)
    at Module.load (internal/modules/cjs/loader.js:996:32)
    at Function.Module._load (internal/modules/cjs/loader.js:896:14)
    at Module.require (internal/modules/cjs/loader.js:1036:19)
    at require (internal/modules/cjs/helpers.js:72:18)
    at Object.<anonymous> (/Users/albertattard/.gauge/plugins/js/2.3.5/node_modules/grpc/src/client_interceptors.js:144:12)
    at Module._compile (internal/modules/cjs/loader.js:1147:30)
    at Object.Module._extensions..js (internal/modules/cjs/loader.js:1167:10) {
    code: 'MODULE_NOT_FOUND'
    }
    Error ----------------------------------

    [Gauge]
    Failed to start gauge API: Timed out connecting to 127.0.0.1:55268

    Get Support ----------------------------
    	Docs:          https://docs.gauge.org
    	Bugs:          https://github.com/getgauge/gauge/issues
    	Chat:          https://spectrum.chat/gauge

    Your Environment Information -----------
    	darwin, 1.0.8
    	html-report (4.0.8), java (0.7.3), js (2.3.5), screenshot (0.0.1)
    ```

## Run the Tests

```bash
$ gauge run specs/
```

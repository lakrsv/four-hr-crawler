# Four Hr Crawler

This is a webcrawler made in approximately 4 hours, excluding setting up the java boilerplate and creating the
diagrams (30 minutes).

## Running Locally

### Prerequisites

* Java 21
* Maven 3
* Docker

### IntelliJ

To run this project in IntelliJ, configure the active profile to `dev`. Once running,
you can execute requests using `curl`

```
curl -X POST http://localhost:8080/crawl/v1/submit?url=http%3A%2F%2Fwww.rapid7.com%2F
```

For now, this will only result in log output in the console.


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

`Submit Crawl`
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"url\": \"http://monzo.com\"}" http://localhost:8080/crawl
```

`Poll Crawl Status`
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/crawl/LTE4NDAwMDAwNDg=/status
```

For now, this will only result in log output in the console.


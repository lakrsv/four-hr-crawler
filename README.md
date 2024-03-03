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
curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"url\": \"https://rapid7.com\", \"allowedDomains\": []}" http://localhost:8080/crawl
```
By default, the crawl will be limited to the provided domain. Additional domains may be passed through the `allowedDomains` parameter, as regex expressions. For example, to allow **all domains**, pass `.*` as the domain filter.
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d "{\"url\": \"https://rapid7.com\", \"allowedDomains\": [\".*\"]}" http://localhost:8080/crawl
```

`Poll Crawl Status`
```
curl -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/crawl/LTE4NDAwMDAwNDg=/status
```

## Visualising Results
During the crawl, the application publishes the results to `Neo4j` which is running in a docker container. The results may be visualised by opening the [visualize.html](./visualize.html) file.
![Crawl Visualization with allow all filter](./Four%20Hour%20Crawler%20-%20Visualised%20Result.png)


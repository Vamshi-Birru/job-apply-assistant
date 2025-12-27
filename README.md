# Job Apply Assistant

Automated job application assistant built using **Dropwizard**.

## 🚀 Features (v1)
- Classifies job URLs into:
    - Easy Apply
    - Greenhouse
    - Workday
    - Company career pages
- REST API using Dropwizard
- Dockerized for easy local execution

## 🛠 Tech Stack
- Java 17
- Dropwizard 4.x
- Maven
- Docker

## ▶️ Run Locally (Docker)

```bash
mvn clean package
docker build -t job-apply-assistant .
docker run -p 9090:9090 -p 9091:9091 job-apply-assistant

# Smart Ecommerce Platform

## Overview
This is a modern e-commerce platform built using a microservices architecture . The platform includes features such as user management, product catalog, order processing, inventory management, personalized recommendations, notifications, and payment processing.

## Technology Stack
- Java 21
- Spring Boot 3.4.4
- Kafka for asynchronous event-driven communication
- Redis for caching
- PostgreSQL and MongoDB for data storage
- Docker and Kubernetes for containerization and orchestration
- Prometheus and Grafana for monitoring
- Liquibase and Flyway for relation database migrations
- Mongock for document database migrations
- WebClient for external API integration (e.g., Stripe for payments)
- CI/CD with GitHub Actions

## Project Structure
```plaintext
smart-ecommerce-platform/
├── services/
│   ├── user-service/
│   ├── product-service/
│   ├── order-service/
│   ├── inventory-service/
│   ├── recommendation-service/
│   ├── notification-service/
│   ├── payment-service/
├── infrastructure/
│   ├── base/
│   │   ├── configmap/
│   │   ├── kafka/
│   │   ├── mongodb/
│   │   ├── postgresql/
│   │   ├── redis/
│   │   ├── zookeeper/
│   ├── ingres/
│   ├── kind/
│   ├── monitoring/
├── .github/
│   ├── workflows/
├── README.md
```

## Monitoring
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000
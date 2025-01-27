# Catalog Microservice

## Overview

The **Catalog Microservice** is a demo application designed for **interview exercises**. It is intended to test your 
live coding, debugging, and troubleshooting skills in a real-world application scenario. The system is designed to 
simulate production-like issues, promote hands-on problem-solving, and evaluate your understanding of observability, 
system reliability, and scalability concepts.

### Key Features

- CRUD operations on `Product` entities, including name, description, and price.
- Integration with PostgreSQL for persistent storage.
- Preloaded database with sample products for testing.
- Prometheus metrics and Zipkin-based tracing for monitoring and debugging.

### Components

The application is build using the following frameworks and technologies:

- **Spring Boot** for rapid development.
- **Spring Data JPA** for data layer abstraction.
- **Flyway** for database schema management and migrations.
- **Actuator, Prometheus, and Zipkin** for observability, metrics, and distributed tracing.

---

## Running with Docker Compose

The project includes a `docker-compose.yaml` file to simplify local deployment with all dependencies. Follow the steps
below to run the Catalog Microservice:

### Prerequisites

- Ensure Docker and Docker Compose are installed on your machine.

### Steps to Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-dir>
   ```

2. Start the stack:
   ```bash
   docker-compose up --build
   ```

3. Services included in the `docker-compose.yaml`:
    - **`app`**: The Catalog Microservice (accessible on port `8080`).
    - **`postgres`**: PostgreSQL database.
    - **`prometheus`**: Metrics monitoring (port `9090`).
    - **`zipkin`**: Tracing server (port `9411`).

4. Verify the application is running:
    - **Spring Actuator Health Check**:  
      Visit `http://localhost:8081/actuator/health`.
    - **Prometheus Metrics**:  
      Visit `http://localhost:9090`.
    - **Zipkin Trace Visualization**:  
      Visit `http://localhost:9411`.

5. Test the API:
    - Base URL: `http://localhost:8080/products`.
    - Examples:
        - Retrieve all products:
          ```bash
          curl -X GET http://localhost:8080/products
          ```
        - Retrieve a specific product by ID:
          ```bash
          curl -X GET http://localhost:8080/products/<product-id>
          ```
        - Add a new product:
          ```bash
          curl -X POST -H "Content-Type: application/json" \
          -d '{"name": "New Product", "description": "Example", "price": 99.99}' \
          http://localhost:8080/products
          ```
        - Update an existing product:
          ```bash
          curl -X PUT -H "Content-Type: application/json" \
          -d '{"name": "Updated Product", "description": "Updated Description", "price": 79.99}' \
          http://localhost:8080/products/<product-id>
          ```
        - Delete a product:
          ```bash
          curl -X DELETE http://localhost:8080/products/<product-id>
          ```

---

## Additional Notes

- **Database**:
  The application connects to a PostgreSQL database (`catalog`) with preloaded data (see `V0001__add-products-table.sql`
  for table definitions and sample inserts).

- **Environment Variables**:
  Environment variables used in the `app` service:
    - `POSTGRES_URL`: Database connection URL.
    - `POSTGRES_USER` and `POSTGRES_PASSWORD`: Credentials for PostgreSQL.
    - `ZIPKIN_ENDPOINT`: URL of the Zipkin tracing server.

- **Observability**:
  The `application.yaml` is configured to expose tracing, metrics, and logs:
    - Tracing enabled through Zipkin.
    - Prometheus metrics exposed on `/actuator/prometheus`.

This microservice provides a scalable, observable, and production-like environment for testing your technical skills
during interviews. Good luck!

# Univaq Street Science Event System
```bash
    git clone https://github.com/vilalali/Assignment3_11
```
## Follow the documentation given below:

This project is focused on enhancing the visitor experience at the Univaq Street Science event during the European Researchers Night in L'Aquila, Italy. It involves implementing architectural patterns to improve venue and parking availability, booking services, accessibility, weather forecast services, and recommendation systems. The system uses a Service-Oriented Pattern and a Publish-Subscribe Pattern for the ticket booking system.

## Table of Contents

1. [Introduction](#introduction)
2. [Project Setup](#project-setup)
3. [Publish-Subscribe Pattern](#publish-subscribe-pattern)
4. [Service-Oriented Pattern](#service-oriented-pattern)
5. [Comparative Analysis](#comparative-analysis)
6. [Conclusion](#conclusion)

## Introduction

The project aims to improve the visitor experience at the Univaq Street Science event through various architectural patterns. These patterns help manage venue and parking availability, booking/ticketing services, accessibility services, weather forecast services, and more.

## Project Setup

To set up and run the project, follow the steps below:

1. **Install Kafka**: Follow [this blog post](link_to_blog_post) for installation instructions.
2. **Start Kafka**: Use the following command to start the Kafka service:
    ```
    sudo systemctl start kafka
    ```
3. **Publish-Subscribe Pattern**: Navigate to the `publish_subscribe` folder and run the `consumer.py` script:
    ```
    python consumer.py
    ```
4. In another terminal, run the `producer.py` script to simulate ticket booking:
    ```
    python producer.py <visitor_id> <event_id> <num_tickets>
    ```

5. **Service-Oriented Pattern**: Navigate to the `service_oriented` folder and run the `consumer.py` script:
    ```
    python consumer.py
    ```

6. In another terminal, run the `producer.py` script, which launches the Flask app:
    ```
    python producer.py
    ```

7. Use `cURL` or Postman to send HTTP POST requests for ticket booking to the running Flask app:
    ```
    curl -X POST -H "Content-Type: application/json" -d '{"visitor_id": "123", "event_id": "456", "num_tickets": 2}' http://localhost:5000/book_ticket
    ```

## Publish-Subscribe Pattern

The Publish-Subscribe pattern handles event-driven communication between components. The booking service publishes an event when a visitor books a ticket, and subscribed components receive the event and take appropriate actions.

- **Producer**: The `producer.py` script publishes booking events to the Kafka topic.
- **Consumer**: The `consumer.py` script consumes booking events from the Kafka topic and processes them.

## Service-Oriented Pattern

The Service-Oriented pattern promotes modularization and loose coupling. Various functionalities are implemented as separate services, each exposing a well-defined API for interaction.

- **Producer**: The `producer.py` script runs a Flask application that handles booking requests.
- **Consumer**: The `consumer.py` script consumes booking requests from the Kafka topic and processes them.

## Comparative Analysis

The project compares the Publish-Subscribe Pattern and the Service-Oriented Pattern for the ticket booking system. 

- **Publish-Subscribe Pattern**:
    - Pros: Decoupling, Asynchronous Communication, Scalability.
    - Cons: Complexity, Debugging challenges.

- **Service-Oriented Pattern**:
    - Pros: Loose Coupling, Interoperability, Maintainability, Scalability.
    - Cons: Latency, Overhead.

## Conclusion

Based on the project scenario, the Service-Oriented Pattern is a better choice due to its flexibility, maintainability, and scalability, which aligns with the goal of providing a robust booking system for the Univaq Street Science event.

---

For any questions or issues, please reach out to the project team at [vilal.ali@students.iiit.ac.in](mailto:vilal.ali@students.iiit.ac.in).


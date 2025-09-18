E-Commerce Microservices Platform

This project is a Microservices-based E-Commerce Platform that provides the core functionalities of a modern e-commerce application.
It is designed with scalability, performance, and reliability in mind, using Spring Boot, Spring Cloud, and event-driven architecture.


Features
> Product Catalog Service – Manage and discover products with advanced sorting & filtering powered by ElasticSearch.
> Payment Service – Integrated with Razorpay Payment Gateway for secure and reliable payments.
> Order Service – Handle order creation, tracking, and management seamlessly.
> Notification Service – Event-driven service to send email notifications at scale across different services.
> Authentication & Authorization Service – Implemented with JWT & OAuth2 to provide secure login, signup, and token validation.
> Caching with Redis – Reduced API response time from ~500 ms to ~50 ms for static data.
> Scalable Event-Driven Architecture – Powered by Kafka for reliable communication between microservices.


Tech Stack
> Backend Framework: Spring Boot, Spring Cloud, Spring Security
> Database: MySQL
> Cache: Redis
> Messaging Queue: Apache Kafka
> Search: ElasticSearch
> Payment Gateway: Razorpay
> Authentication: JWT, OAuth2
> Testing: JUnit


Microservices
Auth Service (JWT/OAuth2), Product Catalog Service, Payment Gateway Service, Order Service, Notification Service



Future Enhancements
> Shopping Cart Service
> Recommendation Engine using ML
> API Gateway for unified access
> Deployment with Docker & Kubernetes



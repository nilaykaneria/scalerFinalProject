# scalerFinalProject
This is the project that I am submitting for the project module section in scaler neoversity
## Overview
Modular microservices backend featuring:
- **ServiceDiscovery (Eureka)** – service registry
- **UserAuthenticationService** – signup/login/token validation (MySQL, JPA)
- **ProductCatalogService_Nov2024** – product CRUD + paginated search (JPA, optional Redis)
- **PaymentService** – hosted payment links via **Razorpay/Stripe** + webhook intake
- **EmailService** – scaffold for SMTP/SES

## Quick Start
1. Start MySQL; create DBs per each service `application.properties`.
2. Run Eureka (port **8761**).
3. Run Auth, Catalog, Payment services.
4. Test:
   - `POST /auth/signup` `{"email":"user@demo.com","password":"Pass@123"}`
   - `POST /auth/login`
   - `GET /products`
   - `POST /payment` `{"amount":50000,"orderId":"ORD-1","phoneNum":"9999999999","customerName":"Demo"}`

## Endpoints
- **Auth:** `/auth/signup`, `/auth/login`, `/auth/validateToken`
- **Catalog:** `/products` (CRUD), `/products/{id}`, `/products/{productId}/{userId}`
- **Payment:** `/payment`, `/stripeWebhook`

## Tech Stack
Java 17, Spring Boot 3.5.x, Spring MVC, Spring Data JPA, MySQL, Redis (optional), Eureka, Razorpay/Stripe, Maven, Actuator, Docker/AWS.

## Architecture
See embedded figures in the full report (architecture, ERD, payment flow, cloud topology).

## Security & Ops
- TLS at Gateway; tokens with expiry; secrets in env/SSM.
- Health checks via Actuator; logs & metrics to CloudWatch/OTLP.

## License
For academic use by **Nilay Ambalal Kaneria**.

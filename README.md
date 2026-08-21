# Arbitra Backend

> An intelligent escrow dispute resolution and settlement engine built for modern fintech applications integrating with payment gateways like Razorpay.

[![Java Version](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-blue.svg)](https://www.postgresql.org/)
[![Flyway](https://img.shields.io/badge/Flyway-Migrations-red.svg)](https://flywaydb.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## Overview

### What the project does
Arbitra Backend acts as an automated backend engine for handling conditional escrow holds, multi-party payouts, dispute tracking, and evidence management. It bridges payment gateways (such as Razorpay) with internal risk assessment logic and database storage.

### What problem it solves
Managing delayed payouts, escrow releases, and customer disputes manually introduces severe operational friction and risk of human error. Arbitra automates the reconciliation lifecycle, mapping orders, transfers, and disputes into a robust ACID-compliant database architecture.

### Who it is for
* **Fintech Developers** building marketplace platforms or escrow systems.
* **Backend Engineers** looking for a clean, production-ready Spring Boot template integrating Flyway, JPA, and PostgreSQL.

### Why someone should use it
Arbitra provides a ready-to-deploy architectural foundation complete with schema migrations, health checks, data seeders, and repository layers—saving dozens of hours of boilerplate setup.

---

## Key Features

* **Escrow & Transfer Management:** Conditional payout tracking with automated hold states (`on_hold`) and unique transaction IDs.
* **Dispute Lifecycle Tracking:** Comprehensive mapping of customer disputes linked to specific orders and underlying transfers.
* **Database Migrations:** Version-controlled schema management powered by Flyway.
* **Robust Persistence:** Spring Data JPA repositories with optimized entity mappings.
* **Built-in Diagnostics & Seeding:** Dedicated endpoints for system health verification and test-data initialization.

---

## Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.2.5 (Spring Web, Spring Data JPA)
* **Database:** PostgreSQL 18
* **Migrations:** Flyway Community Edition
* **Build Tool:** Gradle

---

## Project Structure

```text
arbitra-backend/
├── src/
│   ├── main/
│   │   ├── java/com/arbitra/
│   │   │   ├── ArbitraApplication.java
│   │   │   └── backend/
│   │   │       ├── controller/   # REST Controllers (Health, Seed, etc.)
│   │   │       ├── model/        # JPA Entities (Order, Merchant, Dispute, Transfer, etc.)
│   │   │       └── repository/   # Spring Data JPA Repositories
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/     # Flyway SQL Migration scripts
└── build.gradle

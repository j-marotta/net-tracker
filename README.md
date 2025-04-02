# Networth Tracker

A Java and Spring Boot application for tracking personal assets (stocks, crypto, savings, etc.) and calculating real-time net worth with the help of external financial APIs.

## Table of Contents
1. [Overview](#overview)
2. [Tech Stack](#tech-stack)
3. [Features](#features)
4. [Setup & Usage](#setup--usage)
5. [Roadmap](#roadmap)
6. [Contributing](#contributing)
7. [License](#license)

---

## Overview

**Networth Tracker** is designed to help individuals consolidate all their financial assets in one place. Whether you hold stocks, crypto, or simply keep cash in a savings account, this application will aggregate your asset data and fetch real-time market prices (where applicable) to calculate your overall net worth.

Reasons for building this project:
- Gain hands-on experience with **Java**, **Spring Boot**, and various **APIs**.
- Learn about RESTful services, scheduling tasks for market data, and user authentication with **Spring Security**.
- Create a tool that can project and visualize your financial growth in one simplified dashboard.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.x**
    - Spring Web (REST APIs)
    - Spring Data JPA (database interactions)
    - Spring Security (authentication & authorization)
- **Maven** (for dependency management)
- **Database**: H2 (in-memory for development) or PostgreSQL (for production)
- **APIs for Market Data**:
    - [Alpha Vantage](https://www.alphavantage.co/) / [Yahoo Finance](https://finance.yahoo.com/) / [IEX Cloud](https://iexcloud.io/) (for stocks)
    - [CoinGecko](https://www.coingecko.com/) / [Binance API](https://binance-docs.github.io/apidocs/) (for crypto)
- **(Optional) Front-end**: If you choose to implement a user interface, you could use Thymeleaf or any JavaScript framework (React, Vue, etc.).

---

## Features

1. **User Accounts / Authentication**
    - Users can sign up, log in, and manage their assets.
    - Secured endpoints using Spring Security (JWT or session-based).
    - Passwords are stored securely (e.g., using BCrypt).

2. **Asset Management**
    - Users can add and track various types of assets, such as:
        - **Stocks** (e.g., ticker symbols like AAPL, TSLA, etc.)
        - **Crypto** (e.g., BTC, ETH, etc.)
        - **Savings** (e.g., bank accounts with a known interest rate)
        - (Potentially) **Real Estate** or other custom assets
    - Each asset holds details like `name`, `symbol` (if applicable), `purchase date`, `quantity`, and more.

3. **Data Aggregation**
    - Fetches real-time (or near real-time) price data for stocks and crypto from external APIs.
    - Savings accounts can be updated manually or auto-calculated based on a fixed interest rate.
    - Uses scheduled tasks or on-demand fetching to keep data fresh.

4. **Net Worth Calculation**
    - Summation of `(quantity * currentPrice)` for each asset.
    - Ability to handle multiple asset types and currencies (if you implement an FX rates API).
    - Provides a single **net worth** figure for quick reference.

5. **Portfolio Visualization**
    - (Optional) A dashboard or REST endpoints to visualize:
        - Pie charts of portfolio distribution (stocks vs. crypto vs. cash).
        - Line charts of historical net worth if you store snapshots over time.
    - Could use a front-end library like Chart.js or Highcharts for rendering.

6. **Future Networth Projections**
    - Estimate future portfolio value based on:
        - Historical stock/crypto returns (e.g., average annual growth).
        - Fixed interest rates for savings.
    - Provide hypothetical scenarios for long-term planning.

---

## Setup & Usage

Below are general steps to get the project running locally. Adapt as needed:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/networth-tracker.git
   cd networth-tracker

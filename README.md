User Accounts / Authentication

    Brief Description: Users can create accounts, log in, and secure endpoints.

    Tech: Spring Security with JWT or session-based authentication.

Asset Management

    Brief Description: Users can add multiple types of assets (stocks, crypto, savings, real estate, etc.) with relevant fields (name, ticker, purchase date, quantity).


Data Aggregation

    Brief Description: Pull real-time or on-demand prices using external APIs.

    API Integration: Alpha Vantage for stocks, CoinGecko for crypto, etc.

Net Worth Calculation

    Brief Description: Summation of (quantity * currentPrice) for each asset.

    Implementation: service class that aggregates data from the DB and external price feeds.

Portfolio Visualization

    Brief Description: Provide charts (pie chart for allocation, line chart for historical net worth).


Future Net Worth Projections

    Brief Description: Calculate potential growth based on historical returns, fixed interest, etc.

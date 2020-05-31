package com.example.portfoliomanager.Model.LocalDataSource;

public class PortfolioValues {
    private double portfolio_start_value;
    private double portfolio_updated_value;

    public PortfolioValues(double portfolio_start_value, double portfolio_updated_value) {
        this.portfolio_start_value = portfolio_start_value;
        this.portfolio_updated_value = portfolio_updated_value;
    }

    public double getPortfolio_start_value() {
        return portfolio_start_value;
    }

    public double getPortfolio_updated_value() {
        return portfolio_updated_value;
    }
}

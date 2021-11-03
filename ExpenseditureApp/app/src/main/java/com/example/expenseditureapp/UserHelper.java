package com.example.expenseditureapp;

public class UserHelper {
    String date, income,expense;

    public UserHelper(String date,String income, String expense) {
        this.date=date;
        this.income = income;
        this.expense = expense;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}

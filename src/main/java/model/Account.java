package model;

import java.util.Objects;

public class Account extends BaseEntity {
    private Client client;
    private String number;
    private Double sum;
    private String currency;

    public Account(Client client, String number, Double sum, String currency) {
        this.client = client;
        this.number = number;
        this.sum = sum;
        this.currency = currency;
    }

    public Account(Long id, Client client, String number, Double sum, String currency) {
        super(id);
        this.client = client;
        this.number = number;
        this.sum = sum;
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(client, account.client) &&
                Objects.equals(number, account.number) &&
                Objects.equals(sum, account.sum) &&
                Objects.equals(currency, account.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, number, sum, currency);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id= " + getId() +
                ", client=" + client +
                ", number='" + number + '\'' +
                ", sum=" + sum +
                ", currency='" + currency + '\'' +
                '}';
    }
}

package model;

import java.util.Objects;

public class Cards extends BaseEntity {
    private Account account;
    private Client client;
    private String number;

    public Cards(Account account, Client client, String number) {
        this.account = account;
        this.client = client;
        this.number = number;
    }

    public Cards(Long id, Account account, Client client, String number) {
        super(id);
        this.account = account;
        this.client = client;
        this.number = number;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cards cards = (Cards) o;
        return Objects.equals(account, cards.account) &&
                Objects.equals(client, cards.client) &&
                Objects.equals(number, cards.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, client, number);
    }

    @Override
    public String toString() {
        return "Cards{" +
                "id= " + getId() +
                ", account=" + account +
                ", client=" + client +
                ", number='" + number + '\'' +
                '}';
    }
}

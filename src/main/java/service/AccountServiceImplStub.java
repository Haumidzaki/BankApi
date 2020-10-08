package service;

import model.Account;
import model.Cards;
import model.Client;

import java.util.Collections;
import java.util.List;

public class AccountServiceImplStub implements AccountService {
    @Override
    public Cards createNewCard(long accountId) {
        Client client = new Client(2432423L, "Pupa");
        Account account = new Account(accountId, client, "42343242342", 1337D, "UAH");
        return new Cards(account, client, "1234-5678-8765-4321");
    }

    @Override
    public Account updateAccountSum(long accountId, double newSum) {
        Client client = new Client(2432423L, "Pupa");
        return new Account(accountId, client, "42343242342", newSum, "UAH");
    }

    @Override
    public Account getAccountInfo(long accountId) {
        return new Account(354645L, new Client(2432423L, "Pupa"), "42343242342", 1337D, "UAH");
    }

    @Override
    public List<Cards> getListOfCards(long accountId) {
        Client client = new Client(2432423L, "Pupa");
        Account account = new Account(accountId, client, "42343242342", 1337D, "UAH");
        Cards cards = new Cards(account, client, "1234-5678-8765-4321");
        return Collections.singletonList(cards);
    }
}

package service;

import model.Account;
import model.Cards;
import model.Client;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    @Override
    public Cards createNewCard(long accountId) {
        return null;
    }

    @Override
    public Account updateAccountSum(long accountId, double newSum) {
        return null;
    }

    @Override
    public Account getAccountInfo(long accountId) {
        return new Account(354645L, new Client(2432423L, "Pupa"), "42343242342", 1337D, "UAH");
    }

    @Override
    public List<Cards> getListOfCards(long accountId) {
        return null;
    }
}

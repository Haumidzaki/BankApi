package service;

import model.Account;
import model.Cards;

import java.util.List;

public interface AccountService {
    Cards createNewCard(long accountId);

    Account updateAccountSum(long accountId, double newSum);

    Account getAccountInfo(long accountId);

    List<Cards> getListOfCards(long accountId);
}

package service;

import model.Account;
import model.Cards;
import repository.*;
import util.RandomCardNamber;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {
    private AccountRepository accountrepository = new AccountRepositoryImpl(new ClientRepositoryImpl());
    private CardRepository cardRepository = new CardRepositoryImpl(accountrepository);


    @Override
    public Cards createNewCard(long accountId) {
        Account account = accountrepository.getById(accountId);
        String cardNumber = RandomCardNamber.getNamber();
        Cards cards = new Cards(account, account.getClient(), cardNumber);
        cardRepository.create(cards);
        return cardRepository.getCardByNumber(cardNumber);
    }

    @Override
    public Account updateAccountSum(long accountId, double newSum) {
        Account account = accountrepository.getById(accountId);
        Double accountSum = account.getSum() + newSum;
        account.setSum(accountSum);
        if (accountrepository.update(account, account.getId())) {
            return account;
        }
        return null;
    }

    @Override
    public Account getAccountInfo(long accountId) {
        return accountrepository.getById(accountId);
    }

    @Override
    public List<Cards> getListOfCards(final long accountId) {
        return cardRepository.getAll().stream().filter(x -> x.getAccount().getId() == accountId).collect(Collectors.toList());
    }
}

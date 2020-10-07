package repository;

import model.Cards;


public interface CardRepository extends StandartRepo<Cards> {

    Cards getCardByNumber(String number);
}

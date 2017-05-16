package sb.lon.dao;

import sb.lon.model.Card;

public interface CardDao {

    Card findCardById(Long cardIdNumber);

    Long balance(Long cardIdNumber);

    void pinEnteredCount(Long cardIdNumber);

    Card findCardByIdAndPin(Long cardIdNumber, Long pin);

    void withdraw(Long cardIdNumber, Long amount);
}
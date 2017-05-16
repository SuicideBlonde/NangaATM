package sb.lon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import sb.lon.model.Card;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CardDaoImpl implements CardDao {

    private AtomicLong atomicInteger = new AtomicLong(100);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Card findCardById(final Long cardIdNumber) {
        final String sql = "SELECT * FROM cards WHERE id=:id AND active=TRUE";
        final Map<String, Long> params = Collections.singletonMap("id", cardIdNumber);
        final List<Card> result = namedParameterJdbcTemplate.query(sql, params, CARD_MAPPER);

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public Long balance(final Long cardIdNumber) {
        final Map<String, Long> params = Collections.singletonMap("card_id", cardIdNumber);
        String sql = "SELECT SUM(amount_in_cents) AS amnt FROM cards_accounting WHERE card_id=:card_id";
        final List<Long> result = namedParameterJdbcTemplate.query(sql, params, (resultSet, i) -> resultSet.getLong("amnt"));

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void pinEnteredCount(final Long cardIdNumber) {
        final String sql = "SELECT * FROM cards WHERE id=:id";
        final Map<String, Long> params = Collections.singletonMap("id", cardIdNumber);
        final List<Card> result = namedParameterJdbcTemplate.query(sql, params, CARD_MAPPER);
        if (result.isEmpty()) {
            return;
        }

        String SQL = "UPDATE cards SET pin_entered_count = :pin_entered_count WHERE id = :cardIdNumber";

        int pinEnteredCount = result.get(0).getPinEnteredCount() + 1;

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pin_entered_count", pinEnteredCount);
        namedParameters.addValue("cardIdNumber", cardIdNumber);

        namedParameterJdbcTemplate.update(SQL, namedParameters);

        if (pinEnteredCount == 4) {
            SQL = "UPDATE cards SET active = FALSE WHERE id = :cardIdNumber";
            namedParameterJdbcTemplate.update(SQL, (SqlParameterSource) null);
        }
    }

    @Override
    public Card findCardByIdAndPin(Long cardIdNumber, Long pin) {
        final String sql = "SELECT * FROM cards WHERE id=:id AND pin = :pin";
        final Map<String, Long> params = new HashMap<>();
        params.put("id", cardIdNumber);
        params.put("pin", pin);

        final List<Card> result = namedParameterJdbcTemplate.query(sql, params, CARD_MAPPER);

        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public void withdraw(Long cardIdNumber, Long amount) {
        Long balance = balance(cardIdNumber);
        amount = amount * 100;
        if (balance >= amount) {
            amount = 0 - amount;
            final Map<String, Long> params = new HashMap<>();
            params.put("id", atomicInteger.incrementAndGet());
            params.put("card_id", cardIdNumber);
            params.put("amount_in_cents", amount);
            final String sql = "INSERT INTO cards_accounting VALUES (:id, :card_id, :amount_in_cents, 'ATM cash withdraw')";
            namedParameterJdbcTemplate.update(sql, params);
        }
    }

    private static final RowMapper<Card> CARD_MAPPER = (rs, i) -> {
        Card card = new Card();
        card.setId(rs.getLong("id"));
        card.setPin(rs.getInt("pin"));
        card.setActive(rs.getBoolean("active"));
        card.setPinEnteredCount(rs.getInt("pin_entered_count"));
        return card;
    };
}
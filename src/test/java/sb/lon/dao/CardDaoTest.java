package sb.lon.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sb.lon.model.Card;

public class CardDaoTest {

    private EmbeddedDatabase db;
    private CardDao cardDao;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
    }

    @Test
    public void testFindByname() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(db);
        CardDaoImpl cardDao = new CardDaoImpl();
        cardDao.setNamedParameterJdbcTemplate(template);

        Card card = cardDao.findCardById(1111111111111111L);

        Assert.assertNotNull(card);
        Assert.assertEquals(Long.valueOf(1111111111111111L), card.getId());
        Assert.assertEquals(Integer.valueOf(1111), card.getPin());
        Assert.assertEquals(Integer.valueOf(0), card.getPinEnteredCount());
        Assert.assertTrue(card.isActive());
    }

    @After
    public void tearDown() {
        db.shutdown();
    }
}
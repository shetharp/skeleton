package api;


import controllers.NetIDController;
import dao.ReceiptDao;
import dao.TagDao;
import io.dropwizard.jersey.validation.Validators;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class TagReceiptTest {

    private final Validator validator = Validators.newValidator();

    public static org.jooq.Configuration setupJooq() {
        // For now we are just going to use an H2 Database.  We'll upgrade to mysql later
        // This connection string tells H2 to initialize itself with our schema.sql before allowing connections
        final String jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;INIT=RUNSCRIPT from 'classpath:schema.sql'";
        JdbcConnectionPool cp = JdbcConnectionPool.create(jdbcUrl, "sa", "sa");

        // This sets up jooq to talk to whatever database we are using.
        org.jooq.Configuration jooqConfig = new DefaultConfiguration();
        jooqConfig.set(SQLDialect.MYSQL);   // Lets stick to using MySQL (H2 is OK with this!)
        jooqConfig.set(cp);
        return jooqConfig;
    }


    @Test
    public void testValid() {
        org.jooq.Configuration jooqConfig = setupJooq();
        ReceiptDao receiptDaoTest = new ReceiptDao(jooqConfig);
        TagDao tagDaoTest = new TagDao(jooqConfig);

        int receiptID1 = receiptDaoTest.insert("The Cafe at Cornell Tech", new BigDecimal(99.01));
        tagDaoTest.toggle(receiptID1,"Mucho Expensivo");
        assertThat(receiptDaoTest.getReceiptsByTag("Mucho Expensivo"),hasSize(1));
    }
}
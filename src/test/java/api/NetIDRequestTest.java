package api;


import controllers.NetIDController;
import io.dropwizard.jersey.validation.Validators;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class NetIDRequestTest {

    private final Validator validator = Validators.newValidator();

    @Test
    public void testValid() {
        NetIDController netIDController = new NetIDController();
        Assert.assertEquals("as3668", netIDController.getNetID());
    }
}
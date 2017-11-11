package com.itv.checkoutkata.pricing;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.itv.checkoutkata.exceptions.WrongPriceFormatException;
import com.itv.checkoutkata.exceptions.WrongProductFormatException;
import com.itv.checkoutkata.exceptions.WrongSpecialRuleFormat;

@RunWith(value = Parameterized.class)
public class PricingRuleTest {

	@Parameter(value = 0)
	public char product;
	
	@Parameter(value = 1)
	public int price;

	@Parameter(value = 2)
	public String multiprice;

	@Parameter(value = 3)
	public PricingRule expectedRule;
	
	@Parameter(value = 4)
	public Class<? extends Exception> expectedException;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Parameters(name = "{index}: Product {0}, Price {1}, Multiprice {2}")
    public static Collection<Object[]> data() throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException {
        return Arrays.asList(new Object[][]{
                {'A', 50, "3:125", new PricingRule('A', 50, "3:125"), null},
                {'B', 30, "2:45", new PricingRule('B', 30, "2:45"), null},
                {'C', 20, null, new PricingRule('C', 20), null},
                {'D', 15, null, new PricingRule('D', 15), null},
                {'E', 55, "12;52", new PricingRule('E', 55, "12:52"), WrongSpecialRuleFormat.class},
                {'5', 55, "12:52", new PricingRule('F', 55, "12:52"), WrongProductFormatException.class},
                {'5', 55, "12;52", new PricingRule('G', 55, "12:52"), WrongProductFormatException.class},
                {'H', 0, null, new PricingRule('H', 10, null), WrongPriceFormatException.class}
                               
        });
    }

    @Test
    public void testCreatePricingRules() throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException {
    	if (expectedException != null) {
    		exception.expect(expectedException);
    	}
    	assertThat(new PricingRule(product, price, multiprice), is(expectedRule));
    }
}

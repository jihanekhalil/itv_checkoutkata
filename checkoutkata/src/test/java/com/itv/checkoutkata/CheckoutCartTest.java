package com.itv.checkoutkata;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.itv.checkoutkata.cart.CheckoutCart;
import com.itv.checkoutkata.exceptions.NoPricingRuleForProductException;
import com.itv.checkoutkata.exceptions.WrongPriceFormatException;
import com.itv.checkoutkata.exceptions.WrongProductFormatException;
import com.itv.checkoutkata.exceptions.WrongSpecialRuleFormat;
import com.itv.checkoutkata.pricing.Pricer;
import com.itv.checkoutkata.pricing.PricingRule;

@RunWith(value = Parameterized.class)
public class CheckoutCartTest {
	
	@Parameter(value = 0)
	public CheckoutCart cart;
	
	@Parameter(value = 1)
	public List<Character> products;
	
	@Parameter(value = 2)
	public int expectedTotal;
	
	@Parameter(value = 3)
	public Class<? extends Exception> expectedException;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Parameters
    public static Collection<Object[]> data() throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException {
    	PricingRule ruleA = new PricingRule('A', 50, "3:130");
		PricingRule ruleB = new PricingRule('B', 30, "2:45");
		PricingRule ruleC = new PricingRule('C', 20);
		PricingRule ruleD = new PricingRule('D', 15);
		
		Pricer pricer = new Pricer(new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)));
		
    	return Arrays.asList(new Object[][]{
                {new CheckoutCart(pricer), Arrays.asList('B'), 30, null},
                {new CheckoutCart(pricer), Arrays.asList('B', 'A'), 80, null},
                {new CheckoutCart(pricer), Arrays.asList('B', 'A', 'B'), 95, null},
                {new CheckoutCart(pricer), Arrays.asList('B', 'A', 'B', 'A', 'C', 'A', 'A'), 245, null},
                {new CheckoutCart(pricer), null, 0, null},
                {new CheckoutCart(pricer), Arrays.asList('E'), 0, NoPricingRuleForProductException.class}                                                              
        });
    }
	
	@Test
	public void testCheckout() throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException, NoPricingRuleForProductException {
		
		if (expectedException != null) {
    		exception.expect(expectedException);
    	}
		if (products != null) {
			for (Character product : products) {
				cart.addToCart(product);
			}
		}
		assertEquals(expectedTotal, cart.getTotal());
	}

}

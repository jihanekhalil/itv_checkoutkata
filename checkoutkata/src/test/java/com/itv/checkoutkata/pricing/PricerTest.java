package com.itv.checkoutkata.pricing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.itv.checkoutkata.exceptions.PricerException;
import com.itv.checkoutkata.exceptions.WrongPriceFormatException;
import com.itv.checkoutkata.exceptions.WrongProductFormatException;
import com.itv.checkoutkata.exceptions.WrongSpecialRuleFormat;

@RunWith(value = Parameterized.class)
public class PricerTest {
	
	@Parameter(value = 0)
	public List<PricingRule> rules;
	
	@Parameter(value = 1)
	public char product;
	
	@Parameter(value = 2)
	public int quantity;
	
	@Parameter(value = 3)
	public int expectedPrice;
	
	@Parameters
    public static Collection<Object[]> data() throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException {
    	PricingRule ruleA = new PricingRule('A', 50, "3:130");
		PricingRule ruleB = new PricingRule('B', 30, "2:45");
		PricingRule ruleC = new PricingRule('C', 20);
		PricingRule ruleD = new PricingRule('D', 15);
		
    	return Arrays.asList(new Object[][]{
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'A', 1, 50},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'B', 1, 30},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'C', 1, 20},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'D', 1, 15},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'A', 2, 100},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'A', 3, 130},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'B', 2, 45},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'A', 4, 180},
                {new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC, ruleD)), 'A', 6, 260}
                               
        });
    }

    @Test
    public void testPricer() {
    	Pricer pricer = new Pricer(rules);
    	Assert.assertThat(pricer.getPriceForProduct(product, quantity), CoreMatchers.is(expectedPrice));
    }
    
    
	@Test
	public void testCreatePricingRule() throws WrongProductFormatException, WrongSpecialRuleFormat, PricerException, WrongPriceFormatException {
		
		PricingRule ruleA = new PricingRule('A', 50, "3:130");
		PricingRule ruleB = new PricingRule('B', 30, "2:45");
		PricingRule ruleC = new PricingRule('C', 20);
		PricingRule ruleD = new PricingRule('D', 15);
		
		Pricer pricer = new Pricer(new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC)));
		Assert.assertNotNull(pricer);
		Assert.assertEquals(3, pricer.getRules().size());
		
		pricer.addPricingRule(ruleD);
		Assert.assertEquals(4, pricer.getRules().size());
	}
	
	@Test(expected = PricerException.class)
	public void testAddingAnExistingRule() throws WrongProductFormatException, WrongSpecialRuleFormat, PricerException, WrongPriceFormatException {
		
		PricingRule ruleA = new PricingRule('A', 50, "3:130");
		PricingRule ruleB = new PricingRule('B', 30, "2:45");
		PricingRule ruleC = new PricingRule('C', 20);
		
		Pricer pricer = new Pricer(new ArrayList<>(Arrays.asList(ruleA, ruleB, ruleC)));
		Assert.assertNotNull(pricer);
		Assert.assertEquals(3, pricer.getRules().size());
		
		pricer.addPricingRule(ruleA);
	}
	
	
}

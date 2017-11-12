package com.itv.checkoutkata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.itv.checkoutkata.cart.CheckoutCart;
import com.itv.checkoutkata.pricing.Pricer;
import com.itv.checkoutkata.pricing.PricingRule;

/**
 * Hello world!
 *
 */
public class Checkout {
	
    public static void main( String[] args ) {
    	System.out.println("Enter pricing rules: ");
    	String inputString = null;
    	try {
    		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
    		
			inputString = bufferRead.readLine();
			
			String[] pricingRules = inputString.split(";");
			List<PricingRule> rules = new ArrayList<>();
			
			for (String pricingRule : pricingRules) {
				String[] ruleParameters = pricingRule.split(",");
				PricingRule rule ;
				if (ruleParameters.length > 2) {
					rule = new PricingRule(ruleParameters[0].trim().charAt(0), Integer.valueOf(ruleParameters[1].trim()), ruleParameters[2].trim());
				} else {
					rule = new PricingRule(ruleParameters[0].trim().charAt(0), Integer.valueOf(ruleParameters[1].trim()));
				}
				rules.add(rule);
			}
		
			CheckoutCart cart = new CheckoutCart(new Pricer(rules));
			boolean stillShopping = true;
			while (stillShopping) {
				System.out.println("Scan product: ");
				inputString = bufferRead.readLine();
				if (inputString.equals("quit")) {
					stillShopping = false;
				} else {
					cart.addToCart(inputString.charAt(0));
				}
				System.out.println("Total: " + cart.getTotal());
			}
			
    		
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}
    }
}

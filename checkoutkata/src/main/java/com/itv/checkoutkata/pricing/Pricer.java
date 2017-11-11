package com.itv.checkoutkata.pricing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.itv.checkoutkata.exceptions.PricerException;

/**
 * Pricer containing a set of Pricing Rules
 * @author Jihane
 *
 */
public class Pricer {
	
	private List<PricingRule> rules;
	
	public Pricer (List<PricingRule> rules) {
		this.rules = rules;
	}

	/**
	 * Add a pricing rule to the pricer
	 * @param rule
	 * @throws PricerException, if the rule already exists
	 */
	public void addPricingRule(PricingRule rule) throws PricerException {
		if (rules == null) {
			rules = new ArrayList<PricingRule>();
		}		
		if(rules.contains(rule)) {
			throw new PricerException("This rule is already in the pricer");
		}
		rules.add(rule);
	}

	/**
	 * Returns the pricing rule for the given product if it exists, null otherwise
	 * @param product
	 * @return the pricing rule for the product
	 */
	private PricingRule getPricingRuleForProduct(char product) {
		Optional<PricingRule> ruleForProduct = rules.stream()
				.filter(p -> Character.compare(product, p.getProduct()) == 0)
				.findAny();
		
		if (ruleForProduct.isPresent()) {
			return ruleForProduct.get();
		}
		return null;
	}
	
	/**
	 * Returns the correct price for the product and the given quantity. 
	 * @param product
	 * @param quantity
	 * @return the total price with multiprice applied if relevant
	 */
	public int getPriceForProduct(char product, int quantity) {
		if (quantity == 1) {
			return getPriceForOneUnit(product);
		} else {
			if (productHasSpecialPriceForQuantity(product, quantity)) {
				return getSpecialPriceForProduct(product, quantity);
			} else {
				return quantity*getPriceForOneUnit(product);
			}
		}
	}
	
	private int getPriceForOneUnit(char product) {
		PricingRule rule = getPricingRuleForProduct(product);
		return rule != null ? rule.getPrice() : 0;
	}
	
	private boolean productHasSpecialPriceForQuantity(char product, int quantity) {
		PricingRule rule = getPricingRuleForProduct(product);
		return rule != null && quantity >= rule.getQuantityForSpecialRule();		
	}
	
	private int getSpecialPriceForProduct(char product, int quantity) {
		PricingRule rule = getPricingRuleForProduct(product);
		if (rule != null) {
			if (quantity%rule.getQuantityForSpecialRule() == 0) {
				return (quantity/rule.getQuantityForSpecialRule())*rule.getMultiprice();
			} else {
				return (quantity/rule.getQuantityForSpecialRule())*rule.getMultiprice() + 
						(quantity%rule.getQuantityForSpecialRule())*rule.getPrice();
			}
		}
		return 0;
	}
		
	public List<PricingRule> getRules() {
		return rules;
	}
}

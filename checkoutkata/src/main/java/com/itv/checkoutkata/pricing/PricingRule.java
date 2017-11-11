package com.itv.checkoutkata.pricing;

import com.itv.checkoutkata.exceptions.WrongPriceFormatException;
import com.itv.checkoutkata.exceptions.WrongProductFormatException;
import com.itv.checkoutkata.exceptions.WrongSpecialRuleFormat;

public class PricingRule {
	
	private final char product;
	private final int price;
	private String specialRule;
	
	/**
	 * Create a new Pricing Rule
	 * @param product
	 * @param price
	 * @throws WrongProductFormatException if the name of the product is not a letter
	 * @throws WrongPriceFormatException if the price is less than 0
	 */
	public PricingRule(char product, int price) throws WrongProductFormatException, WrongPriceFormatException {
		
		if (!Character.isLetter(product)) {
			throw new WrongProductFormatException("A product must be defined by a letter.");
		}
		
		this.product = product;
		
		if (price <= 0) {
			throw new WrongPriceFormatException("The price must be greater than 0.");
		}
		this.price = price;
	}
	
	/**
	 * Create a new Pricing Rule with a multiprice rule in the format quantity:price
	 * @param product
	 * @param price
	 * @param specialRule
	 * @throws WrongProductFormatException if the name of the product is not a letter
	 * @throws WrongPriceFormatException if the price is less than 0
	 * @throws WrongSpecialRuleFormat if the Special Rule is not in the correct format
	 */
	public PricingRule(char product, int price, String specialRule) throws WrongProductFormatException, WrongSpecialRuleFormat, WrongPriceFormatException {
		this(product, price);
		if (specialRule != null) {
			checkSpecialRuleIsValid(specialRule);
			this.specialRule = specialRule;
			
		}		
	}

	private void checkSpecialRuleIsValid(String specialRule) throws WrongSpecialRuleFormat {
		String[] ruleComponents = specialRule.split(":");
		if (ruleComponents.length != 2) {
			throw new WrongSpecialRuleFormat("Special Rules must be defined with the following format quantity:price.");
		}
	}

	public String getSpecialRule() {
		return specialRule;
	}

	public char getProduct() {
		return product;
	}

	public int getPrice() {
		return price;
	}

	public int getMultiprice() {
		String[] components = specialRule.split(":");
		return Integer.valueOf(components[1]);
	}
	
	public int getQuantityForSpecialRule() {
		String[] components = specialRule.split(":");
		return Integer.valueOf(components[0]);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + price;
		result = prime * result + product;
		result = prime * result + ((specialRule == null) ? 0 : specialRule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PricingRule other = (PricingRule) obj;
		if (price != other.price)
			return false;
		if (product != other.product)
			return false;
		if (specialRule == null) {
			if (other.specialRule != null)
				return false;
		} else if (!specialRule.equals(other.specialRule))
			return false;
		return true;
	}
	
	
}

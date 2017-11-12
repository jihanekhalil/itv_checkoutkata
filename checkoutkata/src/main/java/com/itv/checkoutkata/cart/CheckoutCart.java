package com.itv.checkoutkata.cart;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.itv.checkoutkata.exceptions.NoPricingRuleForProductException;
import com.itv.checkoutkata.pricing.Pricer;
import com.itv.checkoutkata.pricing.PricingRule;

public class CheckoutCart {
	
	private Pricer pricer;
	private List<Character> products;
	private int total;
	
	public CheckoutCart(Pricer pricer) {
		this.pricer = pricer;
	}

	/**
	 * Adds a product to the cart
	 * @param product
	 * @throws NoPricingRuleForProductException if the product is not recognised
	 */
	public void addToCart(char product) throws NoPricingRuleForProductException {
		if (products == null) {
			products = new ArrayList<Character>();
		}
		
		Optional<PricingRule> pricingRuleForProduct = pricer.getRules()
															.stream()
															.filter(rule -> Character.compare(rule.getProduct(), product) == 0)
															.findFirst();
		
		if (!pricingRuleForProduct.isPresent()) {
			throw new NoPricingRuleForProductException("This product is not supported by our pricer.") ;
		}
		
		products.add(product);
	}
	
	public int getTotal() {
		if (products != null) {
			Map<Character, Long> nbProducts = products.stream().collect(groupingBy(identity(), counting()));
			
			nbProducts.keySet().forEach(p -> {
				total += pricer.getPriceForProduct(p, nbProducts.get(p).intValue());
			});		
			return total;
		}	
		return 0;
	}

}

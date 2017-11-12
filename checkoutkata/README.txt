The main method can be used to demonstrate a basic scenario. If an error occurs, the programs stops. A possible evolution would be to allow for mistakes
and keep the checkout running.

Assumptions:
	- Once a rule is defined in the pricer, it can't be modified.
	- A product is defined by a name (a single letter), a price and a possible special rule.
	- A special pricing rule is defined with the following format quantity:price
	
Remarks:
	- CheckoutCartTest is used to demonstrate various scenarios and can be used to test multiple cases in a test environment.

Pricing Rules example (to use in the console):
A, 50, 3:130; B, 30, 2:45; C, 20; D, 15
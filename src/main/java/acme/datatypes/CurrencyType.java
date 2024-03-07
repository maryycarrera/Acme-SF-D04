
package acme.datatypes;

public enum CurrencyType {

	EUR, USD, GBP;


	public static CurrencyType getDefault() {
		return EUR;
	}
}

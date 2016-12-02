package it.polito.dp2.xml.fibonacci;
import java.math.BigInteger;

/*
 * Generator of the Fibonacci series
 */

public class Fibonacci {
	private BigInteger low = BigInteger.ONE; //two big integeri initilize with 1
	private BigInteger high = BigInteger.ONE;

	public BigInteger next() 
	{
		BigInteger n = low;
		BigInteger temp = high;
		high = high.add(low);
		low = temp;
		return n;
	}
}

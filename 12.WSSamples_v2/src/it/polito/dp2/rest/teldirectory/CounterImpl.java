package it.polito.dp2.rest.teldirectory;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterImpl{

	private AtomicInteger countervalue;
	  
    private CounterImpl() {
    	this.countervalue = new AtomicInteger(0);
    }
    
    private static CounterImpl counter = new CounterImpl();
    
    
    public static CounterImpl getCounter(){
    	return counter;
    }

    /**
     * 
     * @param arg0
     */
    public void increment(int arg0) {
		this.countervalue.addAndGet(arg0);
	}

    /**
     * 
     * @return
     *     returns int
     */
	public int value() {
		return this.countervalue.get();
	}

    /**
     * 
     */
	public void zero() {
		this.countervalue.set(0);
	}

}


package it.polito.dp2.rest.counter;

public class CounterImpl{

	private int countervalue;
	  
    private CounterImpl() {
        this.countervalue=0;
    }
    
    private static CounterImpl counter = new CounterImpl();
    
    
    public static CounterImpl getCounter(){
    	return counter;
    }

    /**
     * 
     * @param arg0
     */
    public synchronized void increment(int arg0) {
		this.countervalue += arg0;
	}

    /**
     * 
     * @return
     *     returns int
     */
	public int value() {
		return this.countervalue;
	}

    /**
     * 
     */
	public synchronized void zero() {
		this.countervalue=0;
	}

}


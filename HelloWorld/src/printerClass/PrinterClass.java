package printerClass;

//create the class
public class PrinterClass
{
	public boolean isOn;
	private String modelNumber;
	
	//create a method that doesn't return any value
	public void print()
	{
		if(isOn)
		{
			System.out.println( modelNumber + "\n" + "isOn ebi joon!!!!" );	
			}
		else {
			System.out.println( modelNumber + "\n" + "isOff ebi joon!!!");
		}
					
		//System.out.println(modelNumber);
	//	System.out.println(isOn);
	}
	//create a method that get and print a input value
	/*private void print(String text,boolean isOn)
	{
		if(isOn)
		{
			System.out.println(modelNumber + "isOn!!!!" );	
			}
			
	}
	//create a method that return a value
	public String getModelNumber(){
		return modelNumber;
	}*/
	//create a constructor of printclass
	public PrinterClass(String model,boolean On){
		this.isOn=On;
		this.modelNumber=model;		
	}

	public void printColors(){
		String[] colors= new String[] { "Red", "Blue", "Green"};
		
		for( String currentColor: colors){
			
				if("Green".equals(currentColor))
				continue;
				
			System.out.println(currentColor);
		}
		System.out.println("we broke up");
	}


}

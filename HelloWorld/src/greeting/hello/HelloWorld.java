package greeting.hello;

import printerClass.PrinterClass;

public class HelloWorld {
	
	//private static int age=50;
	
	public static void main(String[] args)
	{
		System.out.println("Hellow World");
		
		//create class and instance of a class
		PrinterClass myprinter=new PrinterClass("my model", false);
		
		myprinter.print();
	

		PrinterClass yourPrinter=new PrinterClass("your model",false);
		//yourPrinter.modelNumber="Olivetti";
		//yourPrinter.isOn=false;
		
		yourPrinter.printColors();
	//	myprinter.print();
		
		//myprinter.print("Ebi kargar",30);
		//String getModel= yourPrinter.getModelNumber();
		
		//System.out.println(getModel);
		
		/*int numero=6;
		{
			int age=30;
		}
		*/
	/* numb= new int[10];
		numb[0]=33;
		numb[1]=44;
		
		System.out.println(numb[1]);
		
		
		
		numero+=age;
		String name="Ebi";
		boolean value=false;
		System.out.println(3==3);
		System.out.println(numero);
		System.out.println(name);
	}*/
}
}

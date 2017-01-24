package it.polito.dp2.NFFG.sol1;

import it.polito.dp2.NFFG.NamedEntityReader;

public class MynamedEntityReader implements NamedEntityReader {

	// ------------ EntitiesName --------------------------------//
	private String entityName;
	// ---------------------------------------------------------//

	public MynamedEntityReader(String name) {
		this.entityName = name;
	}

	// ----------------------------------------------------------------------//

	@Override
	public String getName() {
		if (entityName != null)
			return this.entityName;
		else {
			System.out.println("EntityName Object is Null");
			return null;
		}
	}

}

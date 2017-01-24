package it.polito.dp2.rest.teldirectory;
// This is a sample interceptor that eliminates the
// entries referred to some ids that should remain hidden
// in the IdEntries and PhoneEntries messages

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import javax.xml.bind.JAXBElement;

import it.polito.dp2.rest.teldirectory.jaxb.IdEntries;
import it.polito.dp2.rest.teldirectory.jaxb.IdEntry;
import it.polito.dp2.rest.teldirectory.jaxb.PhoneEntries;
import it.polito.dp2.rest.teldirectory.jaxb.PhoneEntry;

@Provider
public class TelDirectorySampleInterceptor implements WriterInterceptor {
	Logger logger;
	private HashSet<String> privateIds = new HashSet<String>();

	public TelDirectorySampleInterceptor() {
		logger = Logger.getLogger(TelDirectorySampleInterceptor.class.getName());
		privateIds.add("B123456");
	}

	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		logger.log(Level.INFO, "Sample Interceptor called");
		Object entity = context.getEntity();		
		if (entity instanceof JAXBElement) {
			Object returned = ((JAXBElement) entity).getValue();
			if (returned != null && returned instanceof IdEntries) {
				// remove entries with private ids
				List<IdEntry> list = ((IdEntries)returned).getIdEntry();
				if (list != null) {
					List<IdEntry> refList = new ArrayList<IdEntry>(list);
					for (IdEntry ide:refList) {
						if (privateIds.contains(ide.getId()))
							list.remove(ide);
					}
				}
			} else 	if (returned != null && returned instanceof PhoneEntries) {
				// remove entries with private ids
				List<PhoneEntry> peList = ((PhoneEntries)returned).getPhoneEntry();
				if (peList != null) {
					for (PhoneEntry pe:peList) {
						List<String> idList = pe.getId();
						List<String> refList = new ArrayList<String>(idList);
						for (String id:refList) {
							if (privateIds.contains(id))
								idList.remove(id);
						}
					}
				}
			} 
		} 
		context.proceed();
	}
}

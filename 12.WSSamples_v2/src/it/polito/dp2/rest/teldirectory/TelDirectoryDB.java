package it.polito.dp2.rest.teldirectory;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.rest.teldirectory.jaxb.IdEntries;
import it.polito.dp2.rest.teldirectory.jaxb.IdEntry;
import it.polito.dp2.rest.teldirectory.jaxb.PhoneEntry;

public class TelDirectoryDB {
	private ConcurrentSkipListMap<String, PhoneEntry> phones = new ConcurrentSkipListMap<String, PhoneEntry>();
	private ConcurrentSkipListMap<String, IdEntry> ids = new ConcurrentSkipListMap<String, IdEntry>();
	private Logger logger = Logger.getLogger(TelDirectoryDB.class.getName());
	private InputStream fsr;
	private InputStream schemaStream;

	public TelDirectoryDB() {
			try {				
				fsr = TelDirectoryDB.class.getResourceAsStream("/xml/teldir.xml");
				if (fsr == null) {
					logger.log(Level.SEVERE, "xml directory file Not found.");
					throw new IOException();
				}
				schemaStream = TelDirectoryDB.class.getResourceAsStream("/xsd/Teldirectory.xsd");
				if (schemaStream == null) {
					logger.log(Level.SEVERE, "xml schema file Not found.");
					throw new IOException();
				}
	            JAXBContext jc = JAXBContext.newInstance( "it.polito.dp2.rest.teldirectory.jaxb" );
	            Unmarshaller u = jc.createUnmarshaller();
	            SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	            Schema schema = sf.newSchema(new StreamSource(schemaStream));
	            u.setSchema(schema);
	            JAXBElement<IdEntries> element = (JAXBElement<IdEntries>) u.unmarshal( fsr );
	            
	            IdEntries idEntries = element.getValue();
	            if (idEntries!=null) {
	            	List<IdEntry> list = idEntries.getIdEntry();
	            	for (IdEntry entry: list)
	            		addIdEntry(entry);
	            }
	            fsr.close();
				logger.info("TelDirectoryDB Initialization Completed Successfully");

			} catch (SAXException | JAXBException | IOException se) {
				logger.log(Level.SEVERE, "Error parsing xml directory file. Working with no data.", se);
			} finally {
					try {
						if (fsr!=null)
							fsr.close();
						if (schemaStream!=null)
							schemaStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}

	synchronized IdEntry addIdEntry(IdEntry entry) {
		IdEntry result = ids.put(entry.getId(), entry);
		// add phones to the other map
		for (String phone:entry.getPhone()) {
			if (phones.containsKey(phone)) {
				// add id avoiding duplicates
				List<String> entries = phones.get(phone).getId();
				if (!entries.contains(entry.getId())) {
					// entries.add(entry.getId());
					// do not modify existing object but create new one
					PhoneEntry pe = new PhoneEntry();
					pe.setPhone(phone);
					List<String> newEntries = pe.getId();
					newEntries.addAll(entries);
					newEntries.add(entry.getId());
					phones.put(phone, pe);
				}

			}
			else {
				PhoneEntry pe = new PhoneEntry();
				pe.setPhone(phone);
				pe.getId().add(entry.getId());
				phones.put(phone, pe);
			}
		}
		return result;
	}
	
	ConcurrentSkipListMap<String, PhoneEntry> getPhones() {
		return phones;
	}

	ConcurrentSkipListMap<String, IdEntry> getIds() {
		return ids;
	}
	

}

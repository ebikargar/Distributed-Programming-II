package it.polito.dp2.rest.teldirectory;

import java.net.URI;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import it.polito.dp2.rest.teldirectory.jaxb.IdEntries;
import it.polito.dp2.rest.teldirectory.jaxb.IdEntry;
import it.polito.dp2.rest.teldirectory.jaxb.ObjectFactory;
import it.polito.dp2.rest.teldirectory.jaxb.PhoneEntries;
import it.polito.dp2.rest.teldirectory.jaxb.PhoneEntry;

@Path("/teldirectory")
public class TelDirectory {
	private ConcurrentSkipListMap<String, PhoneEntry> phones;
	private ConcurrentSkipListMap<String, IdEntry> ids;
	private static TelDirectoryDB db = new TelDirectoryDB();
	private static ObjectFactory of = new ObjectFactory();
	private static Logger logger = Logger.getLogger(TelDirectory.class.getName());;

	public TelDirectory() {
		phones = db.getPhones();
		ids =db.getIds();
	}
	
	@GET
	@Path("/phones")
	@Produces({MediaType.APPLICATION_XML,MediaType.TEXT_XML})
	public JAXBElement<PhoneEntries> getPhoneEntries(@QueryParam("prefix") String prefix, @QueryParam("from") String from, @QueryParam("to") String to) {
		SortedMap<String,PhoneEntry> map = phones;
		PhoneEntries pe = new PhoneEntries();
		List<PhoneEntry> pel = pe.getPhoneEntry();
		
		synchronized(db) {
			map = filterSortedMap(prefix, from, to, map);
			pel.addAll(map.values());
		}
		return of.createPhoneEntries(pe);
	}
	
	@GET
	@Path("/phones/{phone}")
	@Produces({MediaType.APPLICATION_XML,MediaType.TEXT_XML})
	public JAXBElement<PhoneEntry> getPhoneEntry(@PathParam("phone") String phone) {
		PhoneEntry pe = phones.get(phone);
		if (pe==null)
			throw new NotFoundException();
		else
			return of.createPhoneEntry(pe);
	}
	
	@GET
	@Path("/ids")
	@Produces({MediaType.APPLICATION_XML,MediaType.TEXT_XML})
	public JAXBElement<IdEntries> getIdEntries(@QueryParam("prefix") String prefix, @QueryParam("from") String from, @QueryParam("to") String to) {
		SortedMap<String,IdEntry> map = ids;
		IdEntries ie = new IdEntries();
		List<IdEntry> iel = ie.getIdEntry();
		synchronized(db) {
			map = filterSortedMap(prefix, from, to, map);
			iel.addAll(map.values());
		}
		return of.createIdEntries(ie);
	}
	
	@GET
	@Path("/ids/{id}")
	@Produces({MediaType.APPLICATION_XML,MediaType.TEXT_XML})
	public JAXBElement<IdEntry> getIdEntry(@PathParam("id") String id) {
		IdEntry ie = ids.get(id);
		if (ie==null)
			throw new NotFoundException();
		else
			return of.createIdEntry(ie);
	}
	
	@PUT
	@Path("/ids/{id}")
	@Consumes({MediaType.APPLICATION_XML,MediaType.TEXT_XML})
	public Response putIdEntry(JAXBElement<IdEntry> entryElement, @PathParam("id") String id, @Context UriInfo uriInfo) {
		IdEntry entry = null;
		if (entryElement != null)
			entry = entryElement.getValue();
		if (entry.getId()==null)
			logger.log(Level.WARNING,"Null id in request!");
		if (entry != null && id!=null && id.equals(entry.getId())) {
			IdEntry result = db.addIdEntry(entry);
			if (result == null) { // resource did not exist
				UriBuilder builder = uriInfo.getAbsolutePathBuilder();
	        	URI u = builder.build();
	        	return Response.created(u).build();
			} else // resource already existed, it has been updated
				return Response.noContent().build();
		} else
			throw new BadRequestException();
	}
	
	private <T> SortedMap<String, T> filterSortedMap(String prefix, String from, String to,
			SortedMap<String, T> map) {
		// apply prefix filtering
		if (prefix!=null && prefix.length()>0) {
			char c = prefix.charAt(prefix.length() - 1); // take last character in prefix
			c++;										 // increment it
			String next = prefix.substring(0, prefix.length()-1)+c;	// substitute last character in prefix with the next one
			map = map.subMap(prefix, next);
		}
		// apply from filtering
		if (from!=null && from.length()>0) {
			map = map.tailMap(from);
		}
		// apply to filtering
		if (to!=null && to.length()>0) {
			map = map.headMap(to);
		}
		return map;
	}
	
}

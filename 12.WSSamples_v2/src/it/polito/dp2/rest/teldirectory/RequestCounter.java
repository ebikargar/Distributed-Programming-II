package it.polito.dp2.rest.teldirectory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/teldirectory")
public class RequestCounter {
	private CounterImpl counter;

	public RequestCounter() {
		counter = CounterImpl.getCounter();
	}
	
	@GET
	@Path("/numberOfRequests")
	@Produces({MediaType.TEXT_PLAIN})
	public int getCount() {
		return counter.value();
	}	
}

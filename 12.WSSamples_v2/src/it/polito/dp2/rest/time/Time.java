package it.polito.dp2.rest.time;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("time")
public class Time {

	public Time() {
	}
	
	@GET
	@Produces("text/plain")
    public String getTime() {
        return new Date().toString();
    }

}

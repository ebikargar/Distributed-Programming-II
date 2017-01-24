package it.polito.dp2.rest.counter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * Root resource (exposed at "mycounter" path)
 */
@Path("mycounter")
@Api(value = "/mycounter", description = "Counter operations")
public class CounterResource {

	CounterImpl counter = CounterImpl.getCounter();
    
    
    @POST
    @ApiOperation(value = "increment", notes = "increment the value "
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Something wrong in Server")})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void increment(int arg) {
    	counter.increment(arg);
    }
    
    @PUT
    @ApiOperation(value = "set to zero", notes = "set the value to zero "
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Something wrong in Server")})
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public void zero() {
    	counter.zero();
    }
    
    @GET
    @ApiOperation(value = "get the current counter", notes = "get the counter "
	)
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Something wrong in Server")})
    @Produces(MediaType.TEXT_PLAIN)
    public String getvalue() {
    	return Integer.toString(counter.value());
    }
    
}

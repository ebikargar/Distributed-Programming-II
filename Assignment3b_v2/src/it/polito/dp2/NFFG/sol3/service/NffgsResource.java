package it.polito.dp2.NFFG.sol3.service;

import java.util.List;

import it.polito.dp2.NFFG.sol3.service.jaxb.Nffg;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/nffgservice")
@Api(value = "/nffgservice", description = "a collection of Nffg objects")
public class NffgsResource {
	// create an instance of the object that can execute operations
	NffgServiceImp nffgService = new NffgServiceImp();

	// GET list of Nffg objects
	@GET
	@ApiOperation(value = "get list of Nffg objects ", notes = "text plain format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.APPLICATION_XML)
	public List<Nffg> getNffg() {
		try {
			List<Nffg> nffg_list = nffgService.getListOfNffgs();
			return nffg_list;
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
	}

	// GET a single Nffg object
	@GET
	@Path("{NffgId}")
	@ApiOperation(value = "get a single Nffg object", notes = "text plain format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.APPLICATION_XML)
	public Nffg getSingleNffgXml(@PathParam("NffgId") String NffgId) {
		try {
			if (NffgId != null) {
				nffgService.getSingleNffg(NffgId);
			}
		} catch (Exception e) {
			throw new InternalServerErrorException();
		}
		return nffgService.getSingleNffg(NffgId);
	}

	// Create a new Nffg object
	@POST
	@ApiOperation(value = "create a new Nffg object", notes = "xml format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 403, message = "Forbidden because Nffg conflict"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Consumes(MediaType.APPLICATION_XML)
	public Response postSingleNffgXml(Nffg nffg, @Context UriInfo uriInfo) {
		if (nffg != null) {
			nffgService.postSingleNffg(nffg);
		}
		return Response.ok().build();
	}

}

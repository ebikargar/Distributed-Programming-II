package it.polito.dp2.NFFG.sol3.service;

import java.util.List;

import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

@Path("/policyservice")
@Api(value = "/policyservice", description = "a collection of Policy objects")
public class PoliciesResource {
	// create an instance of the object that can execute operations
	NffgServiceImp nffgService = new NffgServiceImp();

	// GET list of Policy objects
	@GET
	@ApiOperation(value = "get list of Policy objects ", notes = "text plain format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces({ MediaType.APPLICATION_XML })
	public List<Policy> getPolicy() {
		List<Policy> policy_list = nffgService.getListOfPolicies();
		return policy_list;
	}

	// GET single Policy object
	@GET
	@Path("{PolicyId}")
	@ApiOperation(value = "get a single Policy object", notes = "text plain format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces({ MediaType.APPLICATION_XML })
	public Policy getSinglePolicyXml(@PathParam("PolicyId") String PolicyId) {
		try {
			if (PolicyId != null) {
				nffgService.getSinglePolicy(PolicyId);
			}
		} catch (Exception e) {
			throw new NotFoundException();
		}
		return nffgService.getSinglePolicy(PolicyId);
	}

	// create a new Policy object
	@POST
	@ApiOperation(value = "create a new Reachability Policy object", notes = "xml format")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 403, message = "Forbidden because Reachability Policy failed"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Consumes(MediaType.APPLICATION_XML)
	public Response postSinglePolicyXml(Policy policy, @Context UriInfo uriInfo) {
		if (policy != null)
			System.out.println("PolicyResource:policy posted successfully");
		nffgService.postSinglePolicy(policy);
		return Response.ok().build();
	}

	// Remove list of policy object
	@DELETE
	@ApiOperation(value = "remove list of policy object", notes = "xml formats")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Consumes(MediaType.APPLICATION_XML)
	public void deletePolicies() {
		nffgService.deleteListOfPolicies();
	}

	// Remove a single Policy object
	@DELETE
	@Path("{PolicyId}")
	@ApiOperation(value = "remove a single Policy object", notes = "xml formats")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.APPLICATION_XML)
	public void deleteSinglePolicyXML(@PathParam("PolicyId") String PolicyId) {
		try {
			nffgService.deleteSinglePolicy(PolicyId);
		} catch (Exception e) {
			throw new NotFoundException();
		}
	}

	// UPDATE a Policy object
	@PUT
	@Path("{PolicyId}")
	@ApiOperation(value = "update a Reachability Policy object", notes = "xml formats")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response updatePolicyXML(Policy policy, @Context UriInfo uriInfo) {
		try {
			nffgService.postSinglePolicy(policy);
		} catch (Exception e) {
			throw new ForbiddenException("Update Policy failed");
		}
		return Response.ok().build();
	}

}

package it.polito.dp2.NFFG.sol3.service;

import java.util.GregorianCalendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import it.polito.dp2.NFFG.sol3.service.jaxb.Policy;
import it.polito.dp2.NFFG.sol3.service.jaxb.ResultType;
import it.polito.dp2.NFFG.sol3.service.neo4j.Paths;

@Path("/policyservice")
public class VerificationOfPolicyResource {

	// create an instance of the object that can execute operations
	NffgServiceImp nffgService = new NffgServiceImp();
	WebTarget target;

	// Verification of one or more reachabilitypolicy
	@POST
	@Path("/{policyId}/verification")
	@ApiOperation(value = "verification of one or more reachability policy", notes = "xml formats")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 403, message = "Forbidden because verification failed"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response putVerificationOfPolicyXml(@PathParam("policyId") String policyId, @Context UriInfo uriInfo) {

		Boolean testResultFlag = false;
		ResultType verfificationResult = new ResultType();

		Policy policyName = nffgService.getSinglePolicy(policyId);
		if (policyName == null) {
			throw new NotFoundException();
		}

		String srcId, destId;
		if (policyName.getReachabilityPolicy() != null) {
			srcId = nffgService.getNodeId(policyName.getReachabilityPolicy().getSrcNode());
			destId = nffgService.getNodeId(policyName.getReachabilityPolicy().getDestNode());
		} else {
			srcId = nffgService.getNodeId(policyName.getTraversalPolicy().getSrcNode());
			destId = nffgService.getNodeId(policyName.getTraversalPolicy().getDestNode());
		}
		if (srcId == null || destId == null) {
			System.out.println("Source || Destination Node can't be retrieve from Map correctly");
			throw new InternalServerErrorException();
		}

		try {
			Paths pathResp = target.path("resource/node/" + srcId + "/paths").queryParam("dst", destId).request()
					.accept(MediaType.APPLICATION_XML).get(Paths.class);
			if (pathResp.getPath().size() > 0) {
				System.out.println("Path response Successfully retrieved");
				testResultFlag = true;
			} else {
				System.out.println("Path response can't be retrieve correctly");
				testResultFlag = false;
			}
		} catch (ProcessingException pe) {
			System.out.println("Error during JAX-RS request processing");
			pe.printStackTrace();
		} catch (WebApplicationException wae) {
			System.out.println("Server returned error");
			wae.printStackTrace();
		} catch (Exception e) {
			System.out.println("Unexpected exception");
			e.printStackTrace();
		}
		verfificationResult.setVerificationMessage("Msg is verified");
		Boolean compareResult;
		if (policyName.getReachabilityPolicy() != null) {
			compareResult = policyName.getReachabilityPolicy().isIsPositive();
		} else {
			compareResult = policyName.getTraversalPolicy().isIsPositive();
		}
		if (compareResult == testResultFlag)
			verfificationResult.setVerificationResult(true);
		else
			verfificationResult.setVerificationResult(false);

		XMLGregorianCalendar XMLgregorianCalendar = null;
		try {
			GregorianCalendar gregorianCalendar = new GregorianCalendar();
			XMLgregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		} catch (DatatypeConfigurationException e) {
			System.err.println("convertDate - DatatypeConfigurationException");
			e.printStackTrace();
			System.exit(1);
			return null;
		}
		verfificationResult.setVerificationTime(XMLgregorianCalendar);
		if (policyName.getReachabilityPolicy() != null) {
			policyName.getReachabilityPolicy().setVerificationResult(verfificationResult);
		} else {
			policyName.getTraversalPolicy().setVerificationResult(verfificationResult);
		}

		return Response.ok().build();
	}
}

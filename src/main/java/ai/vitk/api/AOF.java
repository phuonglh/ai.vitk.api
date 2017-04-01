package ai.vitk.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.tuple.Pair;

import com.google.gson.Gson;

import ai.vitk.aof.AttributeType;
import ai.vitk.aof.Feasibility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Mar 30, 2017, 11:56:35 PM
 * <p>
 * AOF service.
 */

@Path("/aof")
@Api(value = "/aof")
public class AOF {
	Feasibility perFeasibility = new Feasibility(AttributeType.PERSON);
	Feasibility locFeasibility = new Feasibility(AttributeType.LOCATION);
	Feasibility orgFeasibility = new Feasibility(AttributeType.ORGANIZATION);
	Feasibility accFeasibility = new Feasibility(AttributeType.ACCOUNT);
	Gson gson = new Gson();
	
	@GET
	@Path("/execute")
	@ApiOperation(value = "AOF Service")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "type", required = true) @QueryParam("type") String type, 
			@ApiParam(value = "object", required = true) @QueryParam("object") String object) {
		List<Pair<String, Double>> distribution = new LinkedList<>();
		switch (type) {
		case "per":
			distribution = perFeasibility.predict(type, object);
			break;
		case "loc":
			distribution = locFeasibility.predict(type, object);
			break;
		case "org":
			distribution = orgFeasibility.predict(type, object);
			break;
		case "acc":
			distribution = accFeasibility.predict(type, object);
			break;
		}
		String json = gson.toJson(distribution.toArray(new Pair[distribution.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

package ai.vitk.api;

import ai.vitk.aof.AttributeType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Mar 18, 2017, 2:45:39 PM
 * <p>
 * AOF service.
 *
 */
@Path("/aof")
@Api(value = "aof")
public class Feasibility {
	ai.vitk.aof.Feasibility femaleFeasibility = new ai.vitk.aof.Feasibility(AttributeType.FEMALE);
	ai.vitk.aof.Feasibility maleFeasibility = new ai.vitk.aof.Feasibility(AttributeType.MALE);
	ai.vitk.aof.Feasibility orgFeasibility = new ai.vitk.aof.Feasibility(AttributeType.ORGANIZATION);
	ai.vitk.aof.Feasibility locFeasibility = new ai.vitk.aof.Feasibility(AttributeType.LOCATION);
	ai.vitk.aof.Feasibility accFeasibility = new ai.vitk.aof.Feasibility(AttributeType.ACCOUNT);
	
	@GET
	@Path("/execute")
	@ApiOperation(value = "Attribute/Object Feasibility")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text,
                          @ApiParam(value = "type", required = true) @QueryParam("type") String type) {
    List<Pair<String, Double>> result = new LinkedList<>();
	  switch (type) {
      case "female":
       result = femaleFeasibility.predict(type, text);
       break;
      case "male":
        result = maleFeasibility.predict(type, text);
        break;
      case "org":
        result = orgFeasibility.predict(type, text);
        break;
      case "loc":
        result = locFeasibility.predict(type, text);
        break;
      case "acc":
        result = accFeasibility.predict(type, text);
        break;
    }
		Response response = Response.status(200).entity(result).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

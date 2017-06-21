package ai.vitk.api;

import ai.vitk.aof.AttributeType;
import com.google.gson.Gson;
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
	ai.vitk.aof.Feasibility orgFeasibility = new ai.vitk.aof.Feasibility(AttributeType.ORGANIZATION);
	ai.vitk.aof.Feasibility locFeasibility = new ai.vitk.aof.Feasibility(AttributeType.LOCATION);
  Gson gson = new Gson();
  
	@GET
	@Path("/execute")
	@ApiOperation(value = "Attribute/Object Feasibility")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text,
                          @ApiParam(value = "type", allowableValues = "ORG, LOC", required = true, defaultValue = "ORG") @QueryParam("type") String type) {
    List<Pair<String, Double>> result = new LinkedList<>();
	  switch (type) {
      case "ORG":
        result = orgFeasibility.predict(type.toLowerCase(), text);
        break;
      case "LOC":
        result = locFeasibility.predict(type.toLowerCase(), text);
        break;
    }
    String json = gson.toJson(result.toArray(new Pair[result.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

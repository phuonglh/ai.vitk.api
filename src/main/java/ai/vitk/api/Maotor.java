package ai.vitk.api;

import ai.vitk.type.SAO;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Subject, Attribute, Object extraction  service.
 *
 */
@Path("/mao")
@Api(value = "mao")
public class Maotor {
  private ai.vitk.sao.Maotor maotor = new ai.vitk.sao.Maotor();
  private Gson gson = new Gson();
	
	@POST
	@Path("/execute")
	@ApiOperation(value = "English Multiple SAO Extractor")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
    List<SAO> sao = maotor.decode(text);
		String json = gson.toJson(sao);
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

package ai.vitk.api;

import ai.vitk.type.SAO;
import ai.vitk.util.Mode;
import ai.vitk.util.ModelParameters;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Mar 18, 2017, 2:45:39 PM
 * <p>
 * Subject, Attribute, Object extraction  service.
 *
 */
@Path("/sao")
@Api(value = "sao")
public class Saotor {
  private ai.vitk.sao.Saotor saotor;
  private Gson gson = new Gson();
	
  public Saotor() {
    ModelParameters parameters = new ModelParameters();
    parameters.setLanguage("eng");
    saotor = new ai.vitk.sao.Saotor(Mode.TEST, parameters);    
  }
  
	@POST
	@Path("/execute")
	@ApiOperation(value = "English Single SAO Extractor")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
    SAO sao = saotor.decode(text);
		String json = gson.toJson(sao);
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

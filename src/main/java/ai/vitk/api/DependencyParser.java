package ai.vitk.api;

import ai.vitk.dep.FeatureFrame;
import ai.vitk.dep.TransitionBasedParser;
import ai.vitk.type.DependencyGraph;
import ai.vitk.type.Token;
import ai.vitk.util.Language;
import ai.vitk.util.Mode;
import ai.vitk.util.ModelParameters;
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
 * Mar 18, 2017, 2:45:39 PM
 * <p>
 * Parser service.
 *
 */
@Path("/dep")
@Api(value = "dep")
public class DependencyParser {
	ModelParameters parameters = new ModelParameters();
	TransitionBasedParser parser = new TransitionBasedParser(Mode.TEST, parameters, FeatureFrame.POS_WORD);
	Gson gson = new Gson();
	
	@GET
	@Path("/execute")
	@ApiOperation(value = "Vietnamese Dependency Parser")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
		DependencyGraph graph = parser.parse(text);
		List<Token> tokens = graph.getSentence().getTokens();
		String json = gson.toJson(tokens.toArray(new Token[tokens.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

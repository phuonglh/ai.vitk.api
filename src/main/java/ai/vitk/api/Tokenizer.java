package ai.vitk.api;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ai.vitk.type.Token;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Mar 18, 2017, 2:45:39 PM
 * <p>
 * Tokenizer service.
 *
 */
@Path("/tok")
@Singleton
@Api(value = "tok")
public class Tokenizer {
	private ai.vitk.tok.Tokenizer tokenizer;
	private Gson gson = new Gson();
	
	public Tokenizer() {
    tokenizer = new ai.vitk.tok.Tokenizer();
  }
	
	@GET
	@Path("/execute")
	@ApiOperation(value = "Vietnamese Word Segmentation")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
		List<Token> tokens = tokenizer.tokenize(text);
		String json = gson.toJson(tokens.toArray(new Token[tokens.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

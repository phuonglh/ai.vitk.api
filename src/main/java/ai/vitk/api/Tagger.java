package ai.vitk.api;

import ai.vitk.seq.POS;
import ai.vitk.type.Sentence;
import ai.vitk.type.Token;
import ai.vitk.util.Mode;
import ai.vitk.util.ModelParameters;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * Mar 18, 2017, 2:45:39 PM
 * <p>
 * Part-of-speech tagging service.
 *
 */
@Path("/tag")
@Singleton
@Api(value = "tag")
public class Tagger {
	private ModelParameters parameters = new ModelParameters();
	private POS pos;
	private Gson gson = new Gson();
	
	public Tagger() {
	   pos = new POS(Mode.TEST, parameters);
  }
	
	@GET
	@Path("/execute")
	@ApiOperation(value = "Vietnamese Part-of-Speech Tagger")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
		pos.setSentence(text);
		try {
			pos.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sentence sentence = pos.getSentence();
		String json = gson.toJson(sentence.getTokens().toArray(new Token[sentence.length()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

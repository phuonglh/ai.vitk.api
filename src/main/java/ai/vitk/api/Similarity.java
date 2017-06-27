package ai.vitk.api;

import ai.vitk.type.Token;
import ai.vitk.util.Language;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Phuong LE-HONG, phuonglh@gmail.com
 * <p>
 * April 20, 2017, 2:45:39 PM
 * <p>
 * Similarity service.
 *
 */
@Path("/sim")
@Singleton
@Api(value = "sim")
public class Similarity {
	ai.vitk.sim.Similarity englishSimilarity = new ai.vitk.sim.DefaultSimilarity(Language.ENGLISH);
	ai.vitk.sim.Similarity vietnameseSimilarity = new ai.vitk.sim.DefaultSimilarity(Language.VIETNAMESE);
  Gson gson = new Gson();
  
	@GET
	@Path("/generate")
	@ApiOperation(value = "English Word Similarity")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "word", required = true) @QueryParam("word") String word,
                          @ApiParam(value = "language", allowableValues = "ENG, VIE", required = true, defaultValue = "ENG") @QueryParam("language") String language) {
    List<Token> result = new LinkedList<>();
	  switch (language) {
      case "ENG":
        result = englishSimilarity.generate(word);
        break;
      case "VIE":
        result = vietnameseSimilarity.generate(word);
        break;
    }
    String json = gson.toJson(result.toArray(new Token[result.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

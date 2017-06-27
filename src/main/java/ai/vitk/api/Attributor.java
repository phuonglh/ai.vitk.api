package ai.vitk.api;

import ai.vitk.sao.AttributeClassifier;
import ai.vitk.util.Mode;
import ai.vitk.util.ModelParameters;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.tuple.Pair;

import javax.inject.Singleton;
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
 * Attributor service.
 *
 */
@Path("/att")
@Singleton
@Api(value = "att")
public class Attributor {
  private AttributeClassifier classifier;
  private Gson gson = new Gson();
	
  public Attributor() {
    ModelParameters parameters = new ModelParameters();
    parameters.setClassifierType("mlp");
    classifier = new AttributeClassifier(Mode.TEST, parameters);
  }
  
  class Output {
    private String attribute;
    private double probability;

    Output(String attribute, double probability) {
      this.attribute = attribute;
      this.probability = probability;
    }

    @Override
    public String toString() {
      return "[" + attribute + ", " + probability + "]";
    }
  }
  
	@GET
	@Path("/execute")
	@ApiOperation(value = "English Personal Attribute Detection")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response execute(@ApiParam(value = "text", required = true) @QueryParam("text") String text) {
    List<Pair<String, Double>> attributes = classifier.decode(text);
    List<Output> outputs = new LinkedList<>();
    attributes.forEach(e -> outputs.add(new Output(e.getLeft(), e.getRight())));
		String json = gson.toJson(outputs.toArray(new Output[outputs.size()]));
		Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
		return response;
	}
}

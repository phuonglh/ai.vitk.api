package ai.vitk.api;

import ai.vitk.pai.models.*;
import ai.vitk.type.SAO;
import ai.vitk.util.Mode;
import ai.vitk.util.ModelParameters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by phuonglh on 6/21/17.
 */
@Path("/pai")
@Api(value = "pai")
public class PAI {
  private ai.vitk.sao.Saotor saotor;
  private ai.vitk.sao.Maotor maotor;
  private Gson gson = new GsonBuilder().serializeNulls().create();
  private DateFormat dateFormat = DateFormat.getDateTimeInstance();
  
  public PAI() {
    ModelParameters parameters = new ModelParameters();
    parameters.setLanguage("eng");
    saotor = new ai.vitk.sao.Saotor(Mode.TEST, parameters);
    maotor = new ai.vitk.sao.Maotor();
  }

  @POST
  @Path("/post")
  @ApiOperation(value = "P.A.I Integration Service")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(PipelineBuffer buffer) {
    String text = buffer.getSurface_text();
    List<SpoValue> spoValues = new LinkedList<>();
    // maotor
    // 
    List<SAO> triples = maotor.decode(text);
    for (SAO sao : triples) {
      SO subject = createSubjectOrObject(text, sao.getS());
      P predicate = createPredicate(text, sao.getA());
      SO object = createSubjectOrObject(text, sao.getO());
      SpoValue x = new SpoValue("uuid", subject, predicate, object, "MAO_EXTRACTOR_V1", 0);
      spoValues.add(x);
    }
    // saotor
    //
    SAO triple = saotor.decode(text);
    SO subject = createSubjectOrObject(text, triple.getS());
    P predicate = createPredicate(text, triple.getA());
    SO object = createSubjectOrObject(text, triple.getO());
    SpoValue x = new SpoValue("uuid", subject, predicate, object, "SAO_EXTRACTOR_V1", 0);
    spoValues.add(x);
    
    // update the buffer
    // 
    buffer.getSentences().forEach(sentence -> sentence.setSpo_values(spoValues));
    String json = gson.toJson(buffer);
    Response response = Response.status(200).entity(json).type(MediaType.APPLICATION_JSON).build();
    return response;
  }

  private SO createSubjectOrObject(String input, String value) {
    int startIndex = input.indexOf(value);
    int endIndex = (startIndex >= 0) ? startIndex + value.length() : -1;
    List<String> surfaceText = new LinkedList<>();
    surfaceText.add(value);
    String createdAt = dateFormat.format(new Date());
    SO element = new SO("uuid", createdAt, startIndex, endIndex, surfaceText, "entity_uuid");
    return element;
  }

  private P createPredicate(String input, String value) {
    int startIndex = input.indexOf(value);
    int endIndex = (startIndex >= 0) ? startIndex + value.length() : -1;
    List<String> surfaceText = new LinkedList<>();
    surfaceText.add(value);
    String createdAt = dateFormat.format(new Date());
    P element = new P("uuid", createdAt, startIndex, endIndex, surfaceText, new RelatedTo());
    return element;
  }
  
}

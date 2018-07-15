package hr.fer.zemris.java.hw17.galerija.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.galerija.model.Image;
import hr.fer.zemris.java.hw17.galerija.model.ImageDB;

/**
 * REST resource class which handles GET requests by reading the given 'name'
 * parameter, reading the corresponding {@link Image} from the {@link ImageDB}
 * and returning the generated JSON.
 * 
 * @author Mate Gasparini
 */
@Path("/image")
public class ImageInfoJSON {
	
	/**
	 * Returns a {@link Response} containing JSON text of the {@link Image}
	 * with the given name.
	 * 
	 * @param name The given name.
	 * @return Corresponding {@link Image} JSON text {@link Response}.
	 */
	@GET
	@Produces("application/json")
	public Response getImageInfo(@QueryParam("name") String name) {
		Image image = ImageDB.getImage(name);
		if (image == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(image);
		return Response.status(Status.OK).entity(jsonText).build();
	}
}

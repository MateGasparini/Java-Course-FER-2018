package hr.fer.zemris.java.hw17.galerija.rest;

import java.util.Set;

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
 * REST resource class which handles GET requests by reading the given 'tag'
 * parameter, reading the corresponding {@link Image}s from the {@link ImageDB}
 * and returning the generated JSON.
 * 
 * @author Mate Gasparini
 */
@Path("/thumbs")
public class ThumbsJSON {
	
	/**
	 * Returns a {@link Response} containing JSON text of the {@link Image}s
	 * with the given tag.
	 * 
	 * @param tag The given tag.
	 * @return Corresponding {@link Image} {@code Set} JSON text {@link Response}.
	 */
	@GET
	@Produces("application/json")
	public Response getThumbs(@QueryParam("tag") String tag) {
		Set<String> imageNames = ImageDB.getImageNames(tag);
		if (imageNames == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(imageNames);
		return Response.status(Status.OK).entity(jsonText).build();
	}
}

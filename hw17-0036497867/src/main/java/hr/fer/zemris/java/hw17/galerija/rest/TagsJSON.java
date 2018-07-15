package hr.fer.zemris.java.hw17.galerija.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.galerija.model.ImageDB;

/**
 * REST resource class which handles GET requests by reading the {@code Set}
 * of all image tags from the {@link ImageDB} and returning the generated JSON.
 * 
 * @author Mate Gasparini
 */
@Path("/tags")
public class TagsJSON {
	
	/**
	 * Returns a {@link Response} containing JSON text of all existing tags.
	 * 
	 * @return Tag {@code Set} JSON text {@link Response}.
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		Gson gson = new Gson();
		String jsonText = gson.toJson(ImageDB.getTags());
		return Response.status(Status.OK).entity(jsonText).build();
	}
}

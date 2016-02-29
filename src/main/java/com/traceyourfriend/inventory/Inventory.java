package com.traceyourfriend.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.traceyourfriend.beans.User;
import com.traceyourfriend.dao.UsersDAO;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


/**
 * This class is used to manage users inventory.
 *
 * @author Aniss
 */

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class Inventory {


	private final UsersDAO dao = new UsersDAO();

    /**
     * This method will return all users that are listed
     * in users table.
     *
     * Example would be:
     * http://localhost:8080/TraceYourFriends/api/users/
     *
     * @return - JSON array string
     * @throws Exception
     */
    @GET
    public String findAll() throws SQLException {
		List<User> users = dao.findAll();
		return new Gson().toJson(users);
    }

    /**
     * This method will return the specific email of user is looking for.
     * It is very similar to the method findAll except this method uses the
     * PathParam to bring in the data.
     *
     * Example would be:
     * http://localhost:8080/TraceYourFriends/api/users/searchMail/aniss.cherkaoui@gmail.com
     *
     * @param email - product email user
     * @return - json array results list from the database
     * @throws Exception
     */
    @Path("searchMail/{email}")
    @GET
    public String findWithEmail(@PathParam("email") String email) throws SQLException {
		if (StringUtils.isEmpty(email)) {
			throw new NotFoundException();
		}
		User user = dao.findWithEmail(email);
		if (user == null) {
			throw new NotFoundException();
		}
		return new Gson().toJson(user);
    }

	/**
	 * This method will return the specific name of user is looking for.
	 * It is very similar to the method findAll except this method uses the
	 * PathParam to bring in the data.
	 *
	 * Example would be:
	 * http://localhost:8080/TraceYourFriends/api/users/searchName/aniss
	 *
	 * @param name - product name user
	 * @return - json array results list from the database
	 * @throws Exception
	 */
	@Path("searchName/{name}")
	@GET
	public String findWithName(@PathParam("name") String name) throws SQLException {
		if (StringUtils.isEmpty(name)) {
			throw new NotFoundException();
		}
		User user = dao.findWithName(name);
		if (user == null) {
			throw new NotFoundException();
		}
		return new Gson().toJson(user);
	}

	/**
	 * This method will allow you to insert data the users table.
	 * Using Jackson Processor
	 *
	 * Note: If you look, this method add using the same URL as a GET method findWithName.
	 * 			We can do this because we are using different HTTP methods for the same URL string.
	 *
	 * @param incomingData - must be in JSON format
	 * @return String
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String add (String incomingData) throws Exception {

		try {
			System.out.println("incomingData: " + incomingData);

			/*
			 * ObjectMapper is from Jackson Parser framework
			 * http://jackson.codehaus.org/
			 *
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();
			UserEntry userEntry = mapper.readValue(incomingData, UserEntry.class);

			int http_code = dao.add(userEntry.name,
					userEntry.email,
					userEntry.password);

			if( http_code == 200 ) {
				return "User inserted";
			} else {
				return "Unable to process User";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Server was not able to process your request";
		}
	}
}

class UserEntry{
	public String name;
	public String email;
	public String password;
}

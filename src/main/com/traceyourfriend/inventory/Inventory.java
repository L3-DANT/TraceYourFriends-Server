package main.com.traceyourfriend.inventory;

import main.com.traceyourfriend.dao.UsersDAO;
import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * This class is used to manage users inventory.
 *
 * @author Aniss
 */

@Path("/users")
public class Inventory {


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
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() throws Exception {

        String returnString;
        Response rb = null;
        JSONArray json;
        try {
            UsersDAO dao = new UsersDAO();

            json = dao.findAll(); //Erreur ne recupere pas les ligne dans la base de données
            returnString = json.toString();

            rb =  Response.ok(returnString).build();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return rb;
    }

    /**
     * This method will return the specific email of user is looking for.
     * It is very similar to the method findAll except this method uses the
     * PathParam to bring in the data.
     *
     * Example would be:
     * http://localhost:8080/TraceYourFriends/api/users/aniss.cherkaoui@gmail.com
     *
     * @param email - product email name
     * @return - json array results list from the database
     * @throws Exception
     */
    @Path("/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithEmail(
            @PathParam("email") String email)
            throws Exception {

        String returnString;

        JSONArray json;

        try {

            UsersDAO dao = new UsersDAO();

            json = dao.findWithEmail(email);
            returnString = json.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to process your request").build();
        }

        return Response.ok(returnString).build();
    }



    /**
     * This method will return the specific name of user is looking for.
     * It is very similar to the method findAll except this method uses the
     * PathParam to bring in the data.
     *
     * Example would be:
     * http://localhost:8080/TraceYourFriends/api/users/search/aniss
     *
     * @param name - product email name
     * @return - json array results list from the database
     * @throws Exception
     */
    @Path("search/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findWithName(
            @PathParam("name") String name)
            throws Exception {

        String returnString;

        JSONArray json;

        try {

            UsersDAO dao = new UsersDAO();

            json = dao.findWithName(name);
            returnString = json.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("Server was not able to process your request").build();
        }

        return Response.ok(returnString).build();
    }

}

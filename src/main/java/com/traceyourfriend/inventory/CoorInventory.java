package com.traceyourfriend.inventory;

import com.google.gson.Gson;

import com.pusher.rest.Pusher;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * This class manage Coordinates
 *
 * Created by romann on 17/03/2016.
 */
@Path("/coor")
@Produces(MediaType.APPLICATION_JSON)
public class CoorInventory {

    @GET
    @Path("Connexion/{login}/{mdp}")
    public String Connexion(@PathParam("login") final String login, @PathParam("mdp") final String mdp) throws SQLException
    {

        String url = "http://"+"272ee489a902c2f6a96f"+":"+"efb0b30a6239f96d1e95"+"@api.pusherapp.com:80/apps/"+"195526";
        Pusher pusher = new Pusher(url);
        pusher.trigger("test_channel", "my_event", Collections.singletonMap("message", "Hello World"));

        return new Gson().toJson("");
    }
}

package controllers;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import io.dropwizard.jersey.sessions.Session;

// For a Java class to be eligible to receive ANY requests
// it must be annotated with at least @Path
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class NetIDController {

    // You can specify additional @Path steps; they will be relative
    // to the @Path defined at the class level
    @GET
    @Path("/netid")
    public String netID() {
        return "sa2265";
    }

    @GET
    public String getNetIdDefault() {
        return "sa2265";
    }
}

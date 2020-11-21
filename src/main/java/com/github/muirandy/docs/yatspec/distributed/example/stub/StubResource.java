package com.github.muirandy.docs.yatspec.distributed.example.stub;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/reverse")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StubResource {

    @POST
    public String reverseString(String input) {
        return "";
    }
}

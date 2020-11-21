package com.github.muirandy.docs.yatspec.distributed.example.stub;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Path("/reverse")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StubResource {

    @POST
    public String reverseString(String input) {
        List<String> letters = Arrays.asList(input.split(""));
        Collections.reverse(letters);
        return letters.stream().collect(Collectors.joining(""));
    }
}

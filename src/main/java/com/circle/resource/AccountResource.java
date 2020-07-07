package com.circle.resource;

import com.circle.core.DBManager;
import com.circle.data.Account;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/account")
public class AccountResource {

    private DBManager DBManager;

    public AccountResource(DBI dbi) {
        DBManager = new DBManager(dbi);
    }

    @GET
    @Path("/get/{id}")
    public Response getAccount(@PathParam("id") String id) {
        Optional<Account> accountOption = DBManager.getAccount(id);
        if (accountOption.isPresent()) {
            return Response.ok().entity(accountOption.get()).type(MediaType.APPLICATION_JSON).build();
        } else {
            return Response.ok().entity("\"" + "Unable to find account" + "\"").type(MediaType.APPLICATION_JSON).build();
        }
    }

    @POST
    @Path("/create")
    public Response createAccount(@QueryParam("accountId") String accountId,
                                  @QueryParam("balance") int balance) {
        Account account = new Account(accountId, balance);
        DBManager.addNewAccount(account);
        return Response.ok().entity(account).type(MediaType.APPLICATION_JSON).build();
    }
}

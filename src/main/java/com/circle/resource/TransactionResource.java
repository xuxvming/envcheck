package com.circle.resource;

import com.circle.core.DBManager;
import com.circle.core.TransactionManager;
import com.circle.data.Account;
import com.circle.data.Transaction;
import com.circle.exception.InsufficientBalanceException;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/transfer")
public class TransactionResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionResource.class);
    private DBManager DBManager;

    public TransactionResource(DBI dbi) {
        DBManager = new DBManager(dbi);
    }

    @POST
    public Response makeTransaction(@QueryParam("senderID") String senderID, @QueryParam("receiverID") String receiverID, @QueryParam("amount") int amount) {
        Optional<Account> sender = DBManager.getAccount(senderID);
        Optional<Account> receiver = DBManager.getAccount(receiverID);
        if (sender.isPresent() && receiver.isPresent()) {
            Account s = sender.get();
            Account r = receiver.get();
            TransactionManager transactionManager = new TransactionManager(s, r, amount);
            try {
                Transaction t = transactionManager.makeTransaction();
                DBManager.addNewTransaction(t);
                DBManager.updateAccountInfo(r);
                DBManager.updateAccountInfo(s);
                return Response.ok().entity(t).type(MediaType.APPLICATION_JSON).build();
            } catch (Exception e) {
                if (e instanceof InsufficientBalanceException) {
                    return Response.ok().entity(e.getMessage()).build();
                } else {
                    LOGGER.error("Unable to make transaction", e);
                }
            }
        }
        return Response.serverError().build();
    }
}

package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import controllers.ReceiptController;
import generated.tables.records.ReceiptsRecord;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.jooq.Result;
import org.jooq.Record;

import static java.util.stream.Collectors.toList;

@Path("/tags/{tag}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TagController {
    final ReceiptDao receipts;
    final TagDao tags;

    public TagController(ReceiptDao receipts, TagDao tags) {
        this.receipts = receipts;
        this.tags = tags;
    }


    @PUT
    public Response toggleTag(@PathParam("tag") String tagName, Integer receiptId) {
        
        if (!receipts.idExists(receiptId)) {
            throw new WebApplicationException("Receipt does not exist", Response.Status.NOT_FOUND);
        }
        
        List<Integer> rId = tags.getReceiptIdFromName(tagName); // receiptId in rId
        if (rId.size() >= 1) {
            if(rId.contains(receiptId)){
                receipts.toggleTagReceipt(receiptId, tagName);
            }
            if (!rId.contains(receiptId)){
                receipts.toggleTagReceipt(receiptId, tagName);

            }

        }
        else if (rId.size() == 0) {
            receipts.toggleTagReceipt(receiptId, tagName);
        }
        return Response.ok().build();
    }

    @GET
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptsRecords = receipts.getReceiptsForTag(tagName);
        return receiptsRecords.stream().map(ReceiptResponse::new).collect(toList());
    }
}

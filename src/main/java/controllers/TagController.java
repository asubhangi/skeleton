package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import api.TagResponse;
import dao.ReceiptDao;
import dao.TagDao;
import controllers.ReceiptController;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import resource.ReceiptResource;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
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
    public List<ReceiptResource> getReceipts(@PathParam("tag") String tagName) {
        List<ReceiptResource> resources = new ArrayList<>();
        List<ReceiptsRecord> receiptsRecords = receipts.getReceiptsForTag(tagName);
        for (ReceiptsRecord receiptsRecord : receiptsRecords) {
            List<String> tagsList = tags.getTags(receiptsRecord.getId());
            resources.add(ReceiptResource.create(receiptsRecord, tagsList));
        }
        return resources;

        /*List<ReceiptsRecord> receiptsRecords = receipts.getReceiptsForTag(tagName);
        return receiptsRecords.stream().map(ReceiptResponse::new).collect(toList());*/
    }


    
    /*@Path("/receipts")
    @PUT
    public List<String> getTags(Integer receiptId) {
        List<String> tagsNames = tags.getTags(receiptId);
        return tagsNames; //.stream().map(String::new).collect(toList())
    }*/
}

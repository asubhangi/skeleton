package controllers;

import api.CreateReceiptRequest;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import resource.ReceiptResource;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;
    final TagDao tags;

    public ReceiptController(ReceiptDao receipts, TagDao tags) {
        this.receipts = receipts;
        this.tags = tags;
    }

    @GET
    @Path("receipts/{id}")
    public ReceiptResource getReceipt(@PathParam("id") Integer id) {
        ReceiptsRecord receipt = receipts.getReceipt(id);
        List<String> tagsList = tags.getTags(id);
        return ReceiptResource.create(receipt, tagsList);

    }

    @POST
    @Path("/receipts")
    public ReceiptResource createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        if (receipt.images == null){
            return ReceiptResource.create(receipts.insert(receipt.merchant, receipt.amount, null), null);//receipts.insert(receipt.merchant, receipt.amount);
        }
        else{
            return ReceiptResource.create(receipts.insert(receipt.merchant, receipt.amount, receipt.images), null);//receipts.insert(receipt.merchant, receipt.amount);
    }
        }
        

    @GET
    @Path("/receipts")
    public List<ReceiptResource> getReceipts() {
        List<ReceiptResource> resources = new ArrayList<>();
        List<ReceiptsRecord> receiptsRecords = receipts.getAllReceipts();
        for (ReceiptsRecord receiptsRecord : receiptsRecords) {
            List<String> tagsList = tags.getTags(receiptsRecord.getId());
            resources.add(ReceiptResource.create(receiptsRecord, tagsList));
        }
        return resources;
        //return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());

    }


    
    
}

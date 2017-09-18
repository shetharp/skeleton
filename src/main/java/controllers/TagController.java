package controllers;

import api.CreateReceiptRequest;
import api.ReceiptResponse;
import dao.ReceiptDao;
import dao.TagDao;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
//
//    @POST
//    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
//        return receipts.insert(receipt.merchant, receipt.amount);
//    }

    @GET
    public List<ReceiptResponse> getReceipts(@PathParam("tag") String tagName) {
        List<ReceiptsRecord> receiptRecords = receipts.getReceiptsByTag(tagName);
        return receiptRecords.stream().map(ReceiptResponse::new).collect(toList());
    }

    @PUT
    public void toggleTag(@PathParam("tag") String tagName, @Valid @NotNull int receiptID) {
        if (receipts.exists(receiptID)) {
            tags.toggle(receiptID, tagName);
        }
        else {
            throw new NotFoundException("Receipt does not exist.");
        }
    }

}

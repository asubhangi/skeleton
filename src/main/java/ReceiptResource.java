package resource;

import generated.tables.records.ReceiptsRecord;

import java.math.BigDecimal;
import java.util.List;

public class ReceiptResource {

    private Integer id;
    private Long time;
    private String merchant;
    private BigDecimal amount;
    private List<String> tags;

    public static ReceiptResource create(ReceiptsRecord receipt, List<String> tags) {
        ReceiptResource resource = null;
        if (receipt != null) {
            resource = new ReceiptResource();
            resource.id = receipt.getId();
            resource.time = receipt.getUploaded() == null ? null : receipt.getUploaded().getTime();
            resource.merchant = receipt.getMerchant();
            resource.amount = receipt.getAmount();
            resource.tags = tags;
        }
        return resource;
    }

    private ReceiptResource() {
    }

    public Integer getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }

    public String getMerchant() {
        return merchant;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public List<String> getTags() {
        return tags;
    }
}
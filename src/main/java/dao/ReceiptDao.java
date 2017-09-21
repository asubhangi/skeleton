package dao;

import api.ReceiptResponse;
import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.Result;
import org.jooq.Record3;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public ReceiptsRecord insert(String merchantName, BigDecimal amount) {
        return addReceipt(merchantName, amount);
        
    }

    private ReceiptsRecord addReceipt(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning()
                .fetchOne();

                /*.insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT, RECEIPTS.UPLOADED)
                .values(merchantName, amount, new Timestamp(System.currentTimeMillis()))
                .returning()
                .fetchOne();*/

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord;
    }

    public List<ReceiptsRecord> getAllReceipts() {
        return dsl.selectFrom(RECEIPTS).fetch();
    }

    public boolean idExists(Integer receiptId){
        return dsl.fetchExists(RECEIPTS, RECEIPTS.ID.eq(receiptId));
    }

    public void toggleTagReceipt(Integer receiptId, String tagName) {
        List<TagsRecord> tagsRecords = dsl.selectFrom(TAGS)
                .where(TAGS.ID.eq(receiptId).and(TAGS.NAME.eq(tagName))).fetch();

        
        if (tagsRecords.size() > 0) {
            dsl.delete(TAGS)
                    .where(TAGS.ID.eq(receiptId))
                    .and(TAGS.NAME.eq(tagName))
                    .execute();
        }
        
        else {
            dsl.insertInto(TAGS, TAGS.NAME, TAGS.ID)
                    .values(tagName,receiptId).execute();
        }
    }


    public List<ReceiptsRecord> getReceiptsForTag(String tagName) {
        Result<Record3<Integer, String, BigDecimal>> result = dsl.select(RECEIPTS.ID, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT).from(RECEIPTS)
                .innerJoin(TAGS).on(RECEIPTS.ID.eq(TAGS.ID))
                .where(TAGS.NAME.eq(tagName))
                .fetch();

        List<ReceiptsRecord> receiptsRecords = new ArrayList<>();

        for (Record3 r: result) {
            ReceiptsRecord receiptsRecord = new ReceiptsRecord();
            receiptsRecord.setId((Integer)r.getValue(0));
            receiptsRecord.setMerchant((String)r.getValue(1));
            receiptsRecord.setAmount((BigDecimal) r.getValue(2));
            receiptsRecords.add(receiptsRecord);
        }

        return receiptsRecords;
    }
    

    public ReceiptsRecord getReceipt(Integer id) {
        ReceiptsRecord receipt = null;
        if (id != null) {
            receipt = dsl
                    .selectFrom(RECEIPTS)
                    .where(RECEIPTS.ID.eq(id))
                    .fetchOne();
        }
        return receipt;
    }

    public List<String> getTags(Integer receiptId) {
        List<String> tags = null;
        if (receiptId != null) {
            List<Integer> tagIds = null;
            tagIds = dsl
                    .selectFrom(TAGS)
                    .where(TAGS.ID.eq(receiptId))
                    .fetch(TAGS.PID);
            if (tagIds != null && !tagIds.isEmpty()) {
                tags = dsl
                        .selectFrom(TAGS)
                        .where(TAGS.ID.in(tagIds))
                        .fetch(TAGS.NAME);
            }
        }
        return tags;
    }
}

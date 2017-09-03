package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.tables.Tags.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String tagName, Integer receiptId) {
        TagsRecord tagsRecord = dsl
                .insertInto(TAGS, TAGS.NAME, TAGS.ID)
                .values(tagName, receiptId)
                .returning(TAGS.PID)
                .fetchOne();

        checkState(tagsRecord != null && tagsRecord.getId() != null, "Insert into Tags failed");

        System.out.println("tagId after insertion is " + tagsRecord.getPid());
        return tagsRecord.getPid();
    }


    public List<Integer> getReceiptIdFromName(String tagName) {
        return dsl.selectFrom(TAGS)
                .where(TAGS.NAME.eq(tagName))
                .fetch(TAGS.ID);
    }
}
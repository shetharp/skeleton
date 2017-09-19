package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class TagDao {
    DSLContext dsl;

    public TagDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public List<TagsRecord> getAllTags() {
        return dsl.selectFrom(TAGS).fetch();
    }

    public void toggle(Integer receiptID, String tagName){
        TagsRecord tagsRecord = dsl
                .selectFrom(TAGS)
                .where(TAGS.RECEIPT_ID.eq(receiptID))
                .and(TAGS.NAME.eq(tagName))
                .fetchOne();

        if (tagsRecord != null) {
            tagsRecord.delete();
            return;
        }
        else {
            tagsRecord = dsl
                    .insertInto(TAGS, TAGS.RECEIPT_ID, TAGS.NAME)
                    .values(receiptID, tagName)
                    .returning(TAGS.ID)
                    .fetchOne();

            checkState(tagsRecord != null && tagsRecord.getId() != null, "Insert Tag failed");
        }
    }

//    public List<String> getTagsByReceipt(Integer receiptID) {
//       List<TagsRecord> tagRecs = dsl
//                .select(TAGS.NAME)
//                .from(TAGS)
//                .where(RECEIPTS.ID.eq(receiptID))
//                .fetchInto(TAGS);
//
//       return tagRecs;
//    }

}

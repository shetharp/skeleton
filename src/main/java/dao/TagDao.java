package dao;

import generated.tables.records.ReceiptsRecord;
import generated.tables.records.TagsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
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

    public int insert(String tagName) {
        TagsRecord tagsRecord = dsl
                .insertInto(TAGS, TAGS.NAME)
                .values(tagName)
                .returning(TAGS.ID)
                .fetchOne();

        checkState(tagsRecord != null && tagsRecord.getId() != null, "Inserting Tags failed");

        return tagsRecord.getId();
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
}

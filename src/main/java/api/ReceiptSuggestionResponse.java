package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * Represents the result of an OCR parse
 */
public class ReceiptSuggestionResponse {
    @JsonProperty
    public final String merchant;

    @JsonProperty
    public final String amount;


    public ReceiptSuggestionResponse(String merchant, String amount) {
        this.merchant = merchant;
        this.amount = amount;
    }
}

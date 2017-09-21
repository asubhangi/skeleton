package api;

import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This is an API Object.  It's job is to model and document the JSON API that we expose
 *
 * Fields can be annotated with Validation annotations - these will be applied by the
 * Server when transforming JSON requests into Java objects IFF you specify @Valid in the
 * endpoint.  See {@link controllers.ReceiptController#createReceipt(CreateReceiptRequest)} for
 * and example.
 */
public class CreateReceiptRequest {
    @NotEmpty
    public String merchant;

    @NotNull
    @Min(0)
    public BigDecimal amount;
}

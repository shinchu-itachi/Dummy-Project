package RupeekBank.Loan.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bank Details")
@Data
@Getter
@Setter
public class BankModel {

    @Id
    private int customerId;

    @Indexed(unique = true)
    @NotBlank
    private String BankAccountNo;

    @NotBlank
    private String IFSC;

    @NotBlank
    private String BankBranch;

    @NotBlank
    private String BankName;

    @NotBlank
    private String AccountHolderName;

    @NotBlank
    private String AccountHolderPhone;


}

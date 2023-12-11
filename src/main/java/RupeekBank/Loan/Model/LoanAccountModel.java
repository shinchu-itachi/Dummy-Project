package RupeekBank.Loan.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "Loan Account")
@Data
@Getter
@Setter
public class LoanAccountModel {

    @Id
    private int customerId ;

    private String loanAccount;
    private Double loanAmount;
    private String loanDate;

    @DBRef
    private CustomerModel customerDetails;

    @DBRef
    private KYCModel kycDetails;

    @DBRef
    private BankModel bankDetails;

    @DBRef
    private JwelleryModel jwelleryDetails;


    public void setLoanDate() {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        this.loanDate = date;

    }

    public Double purity(Integer carat){
        if ( carat == 22) return 5000.0;
        return 5400.0;

    }

    public void setLoanAmount(Double netWeight , Integer carat){
        this.loanAmount = netWeight * purity(carat) ;
    }


}

package RupeekBank.Loan.Model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.UUID;

@Document(collection = "KYC")
@Data
@Getter
@Setter
public class KYCModel {
    @Id
    private int customerId;

    @Indexed( unique = true )
    private String addharNo;

    @Indexed( unique = true )
    private String panNo;

    @Indexed( unique = true )
    private String kycNo;

    public void setKycNo(String kycNo) {
        this.kycNo = kycNo;
    }

    public String generateKYCNo(){
       String uniqueNo =  addharNo.substring(8 , addharNo.length()) + panNo.substring(5 , panNo.length()-1);
       return uniqueNo;
    }
}

package RupeekBank.Loan.Model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Customers")
@Data
@Getter
@Setter
public class CustomerModel {
    @Id
    private int customerId;

    private String Name;
    private String DOB;
    private String cibil;

    @Indexed(unique = true)
    private String phoneNo;

    @Indexed(unique = true)
    private String email;

    private String gender;
    private String age;
    private String maritalStatus;
    private String occupation;

}

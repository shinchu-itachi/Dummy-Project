package RupeekBank.Loan.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Document(collection = "Jwellery Details")
@Data
@Getter
@Setter
public class JwelleryModel {
    @Id
    private int customerId;

    @NotBlank
    private List<String> JwelleryType;

    @NotBlank
    private Integer Carat;

    @NotBlank
    private Double NetWeight;
}

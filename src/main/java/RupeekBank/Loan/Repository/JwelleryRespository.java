package RupeekBank.Loan.Repository;

import RupeekBank.Loan.Model.JwelleryModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwelleryRespository extends MongoRepository<JwelleryModel , Integer> {
}

package RupeekBank.Loan.Repository;

import RupeekBank.Loan.Model.KYCModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends MongoRepository<KYCModel, Integer> {
}

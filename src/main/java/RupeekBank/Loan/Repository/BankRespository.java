package RupeekBank.Loan.Repository;

import RupeekBank.Loan.Model.BankModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRespository extends MongoRepository<BankModel, Integer> {
}

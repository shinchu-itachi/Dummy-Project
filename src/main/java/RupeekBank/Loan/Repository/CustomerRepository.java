package RupeekBank.Loan.Repository;

import RupeekBank.Loan.Model.CustomerModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerModel , Integer> {
}

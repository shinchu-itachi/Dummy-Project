package RupeekBank.Loan.Repository;

import RupeekBank.Loan.Model.LoanAccountModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountRepository extends MongoRepository<LoanAccountModel,Integer> {

}

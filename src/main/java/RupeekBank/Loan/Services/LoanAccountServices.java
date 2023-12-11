package RupeekBank.Loan.Services;

import RupeekBank.Loan.Model.LoanAccountModel;
import RupeekBank.Loan.Repository.LoanAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanAccountServices {
    @Autowired
    private LoanAccountRepository loanAccountRepository;

    public void addAccountDetails(LoanAccountModel loanAccountModel){
        loanAccountRepository.save(loanAccountModel);
    }
    public LoanAccountModel findCustomerDetails(int customerId) {
        return loanAccountRepository.findById(customerId).orElse(null);
    }

    public void deleteAccountDetails(int customerId){
        loanAccountRepository.deleteById(customerId);
    }
}

package RupeekBank.Loan.Services;

import RupeekBank.Loan.Model.BankModel;
import RupeekBank.Loan.Repository.BankRespository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServices extends Exception{
    @Autowired
    private BankRespository bankRespository;

    public BankModel addCustomerBank(BankModel bankDetails){
        return bankRespository.save(bankDetails);
    }

    public void deleteCustomerBank(int customerId){
        bankRespository.deleteById(customerId);
    }

    public BankModel getBankDetails(int customerId) {
        return bankRespository.findById(customerId).orElse(null);
    }
}

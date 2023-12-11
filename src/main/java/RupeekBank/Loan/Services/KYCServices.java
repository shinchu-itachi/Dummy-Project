package RupeekBank.Loan.Services;

import RupeekBank.Loan.Model.KYCModel;
import RupeekBank.Loan.Repository.CustomerRepository;
import RupeekBank.Loan.Repository.KYCRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KYCServices extends  Exception {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private KYCRepository kycRepository;


    public KYCModel addKYCDetails(KYCModel kycDetails) {
        return kycRepository.save(kycDetails);
    }

    public void deleteKYCDetails(int customerId){
        kycRepository.deleteById(customerId);
    }

    public KYCModel findKYCDetails(int customerId) {
        return kycRepository.findById(customerId).orElse(null);
    }
}

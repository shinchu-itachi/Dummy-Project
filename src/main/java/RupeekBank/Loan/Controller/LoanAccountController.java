package RupeekBank.Loan.Controller;

import RupeekBank.Loan.Model.*;
import RupeekBank.Loan.Repository.KYCRepository;
import RupeekBank.Loan.Repository.LoanAccountRepository;
import RupeekBank.Loan.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/api/account")
public class LoanAccountController {
    @Autowired
    private LoanAccountServices loanAccountServices;
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private KYCServices kycServices;
    @Autowired
    private JwelleryServices jwelleryServices;
    @Autowired
    private BankServices bankServices;

    @GetMapping("/details/{customerId}")
    public ResponseEntity<LoanAccountModel> getCustomerLoanAccount(@PathVariable int customerId){
        try{
            CustomerModel customer = customerServices.findCustomerById(customerId);
            KYCModel kyc = kycServices.findKYCDetails(customerId);
            BankModel bank = bankServices.getBankDetails(customerId);
            JwelleryModel jwellery = jwelleryServices.getJwelleryDetails(customerId);

            if ( customer == null || kyc == null || bank == null || jwellery == null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);


            LoanAccountModel customerDetails = new LoanAccountModel();
            customerDetails.setCustomerId(customerId);
            customerDetails.setLoanAccount(customer.getPhoneNo());
            customerDetails.setLoanAmount(jwellery.getNetWeight() , jwellery.getCarat());
            customerDetails.setLoanDate();
            customerDetails.setCustomerDetails(customer);
            customerDetails.setBankDetails(bank);
            customerDetails.setKycDetails(kyc);
            customerDetails.setJwelleryDetails(jwellery);
            loanAccountServices.addAccountDetails(customerDetails);
            return new ResponseEntity<LoanAccountModel>(customerDetails , HttpStatus.OK);


        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

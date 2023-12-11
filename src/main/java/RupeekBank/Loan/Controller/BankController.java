package RupeekBank.Loan.Controller;

import RupeekBank.Loan.Model.BankModel;
import RupeekBank.Loan.Model.CustomerModel;
import RupeekBank.Loan.Services.BankServices;
import RupeekBank.Loan.Services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api/bankdetail")
public class BankController extends Exception{
    @Autowired
    private BankServices bankServices;
    @Autowired
    private CustomerServices customerServices;

    @GetMapping("/{customerId}")
    public ResponseEntity<BankModel> bankDetail(@PathVariable int customerId){
        try{
            BankModel bankDetails = bankServices.getBankDetails(customerId);
            if( bankDetails == null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<BankModel>(bankDetails, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<BankModel> addBankDetail(@PathVariable int customerId , @RequestBody BankModel bankDetail){

        try{
            CustomerModel isCustomer = customerServices.findCustomerById(customerId);
            if ( isCustomer == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            BankModel bank = new BankModel();
            bank.setCustomerId(customerId);
            bank.setBankAccountNo(bankDetail.getBankAccountNo());
            bank.setIFSC(bankDetail.getIFSC());
            bank.setBankBranch(bankDetail.getBankBranch());
            bank.setAccountHolderName(bankDetail.getAccountHolderName());
            bank.setAccountHolderPhone(bankDetail.getAccountHolderPhone());

            BankModel currentBankDetails = bankServices.addCustomerBank(bank);
            return new ResponseEntity<BankModel>(currentBankDetails , HttpStatus.CREATED);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{customerId}")
    public ResponseEntity<BankModel> updateBankDetails(@PathVariable int customerId , @RequestBody BankModel updatedBank){
        try{
            BankModel bankDetailExist = bankServices.getBankDetails(customerId);
            if ( bankDetailExist == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            bankDetailExist.setCustomerId(customerId);
            updateCustomerInfo(bankDetailExist, updatedBank);
            return new ResponseEntity<BankModel>(bankServices.addCustomerBank(bankDetailExist) , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateCustomerInfo(BankModel bankDetailExist, BankModel updatedBankDetail) {
        if( updatedBankDetail.getBankBranch() != null )
            bankDetailExist.setBankBranch(updatedBankDetail.getBankBranch());

        if ( updatedBankDetail.getBankAccountNo() != null )
            bankDetailExist.setBankAccountNo(updatedBankDetail.getBankAccountNo());

        if ( updatedBankDetail.getIFSC() != null )
            bankDetailExist.setIFSC(updatedBankDetail.getIFSC());

        if ( updatedBankDetail.getAccountHolderName() != null )
            bankDetailExist.setAccountHolderName(updatedBankDetail.getAccountHolderName());

        if ( updatedBankDetail.getAccountHolderPhone() != null )
            bankDetailExist.setAccountHolderPhone(updatedBankDetail.getAccountHolderPhone());
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteBankDetail(@PathVariable int customerId) {
        try {
            BankModel bankDetailsExist = bankServices.getBankDetails(customerId);
            if (bankDetailsExist != null) {
                bankServices.deleteCustomerBank(customerId);
                return new ResponseEntity<String>("Deleted the bank details of the customer!", HttpStatus.OK);
            } else return new ResponseEntity<String>("Customer not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>("Could not delete the customer's bank details.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

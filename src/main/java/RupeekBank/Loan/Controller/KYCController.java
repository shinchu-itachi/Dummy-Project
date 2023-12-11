package RupeekBank.Loan.Controller;

import RupeekBank.Loan.Model.CustomerModel;
import RupeekBank.Loan.Model.KYCModel;
import RupeekBank.Loan.Repository.CustomerRepository;
import RupeekBank.Loan.Repository.KYCRepository;
import RupeekBank.Loan.Services.CustomerServices;
import RupeekBank.Loan.Services.KYCServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/KYC")
public class KYCController extends Exception{
    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private KYCServices kycServices;

    @GetMapping("/{customerId}")
    public ResponseEntity<KYCModel> viewKYCDetails(@PathVariable int customerId){
        try{
            KYCModel kycDetails = kycServices.findKYCDetails(customerId);
            if ( kycDetails == null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<KYCModel>(kycDetails , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/{customerId}")
    public ResponseEntity<KYCModel> addKYCDetails(@PathVariable int customerId, @RequestBody KYCModel kycDetails){
        try{
            KYCModel isKYCDone = kycServices.findKYCDetails(customerId);
            CustomerModel isCustomer = customerServices.findCustomerById(customerId);
            if ( isKYCDone != null || isCustomer == null  )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            KYCModel kyc = new KYCModel();
            kyc.setCustomerId(customerId);
            kyc.setAddharNo(kycDetails.getAddharNo());
            kyc.setPanNo(kycDetails.getPanNo());
            kyc.setKycNo(kyc.generateKYCNo());
            return new ResponseEntity<KYCModel>(kycServices.addKYCDetails(kyc) , HttpStatus.CREATED);
        } catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteKYCDetails(@PathVariable int customerId){
        try{
            KYCModel isKYCDone = kycServices.findKYCDetails(customerId);
            CustomerModel isCustomer = customerServices.findCustomerById(customerId);
            if ( isKYCDone == null || isCustomer == null  )
                return new ResponseEntity<String>("Customer does not exist!",HttpStatus.BAD_REQUEST);

            kycServices.deleteKYCDetails(customerId);
            return new ResponseEntity<String>("Deleted the KYC Details for the customer !", HttpStatus.OK);

        }catch (Exception e ){
            return new ResponseEntity<String>("Something went wrong, please try later!",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{customerId}")
    public ResponseEntity<KYCModel> updateKYCDetails(@PathVariable int customerId , @RequestBody KYCModel updatedKYC){
        try{
            KYCModel isKYCDone = kycServices.findKYCDetails(customerId);
            CustomerModel isCustomer = customerServices.findCustomerById(customerId);
            if ( isKYCDone == null || isCustomer == null  )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            isKYCDone.setCustomerId(customerId);
            updateKYCDetails(isKYCDone , updatedKYC);
            return  new ResponseEntity<KYCModel>(kycServices.addKYCDetails(isKYCDone) , HttpStatus.OK);
        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateKYCDetails( KYCModel isKYCDone , KYCModel updatedKYC){
        if ( updatedKYC.getAddharNo() != null)
            isKYCDone.setAddharNo(updatedKYC.getAddharNo());

        if ( updatedKYC.getPanNo() != null )
            isKYCDone.setPanNo(updatedKYC.getPanNo());

        isKYCDone.setKycNo(updatedKYC.generateKYCNo());
    }
}

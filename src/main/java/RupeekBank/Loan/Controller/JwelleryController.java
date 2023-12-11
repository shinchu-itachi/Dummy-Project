package RupeekBank.Loan.Controller;

import RupeekBank.Loan.Model.CustomerModel;
import RupeekBank.Loan.Model.JwelleryModel;
import RupeekBank.Loan.Services.CustomerServices;
import RupeekBank.Loan.Services.JwelleryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwellerydetail")
public class JwelleryController {
    @Autowired
    private JwelleryServices jwelleryServices;
    @Autowired
    private CustomerServices customerServices;

    @PostMapping("/{customerId}")
    public ResponseEntity<JwelleryModel> addJwelleryDetails(@PathVariable int customerId , @RequestBody JwelleryModel jwelleryDetail){
        try{
            CustomerModel isCustomer = customerServices.findCustomerById(customerId);
            if ( isCustomer == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            JwelleryModel jwellery = new JwelleryModel();
            jwellery.setCustomerId(customerId);
            jwellery.setJwelleryType(jwelleryDetail.getJwelleryType());
            jwellery.setCarat(jwelleryDetail.getCarat());
            jwellery.setNetWeight(jwelleryDetail.getNetWeight());

            JwelleryModel jwelleryDetails = jwelleryServices.createJwelleryDetails(jwellery);
            return new ResponseEntity<JwelleryModel>(jwelleryDetails , HttpStatus.CREATED);

        }catch (Exception e ){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<JwelleryModel> getJwelleryDetails(@PathVariable int customerId){
        try{
            JwelleryModel jwelleryDetails = jwelleryServices.getJwelleryDetails(customerId);
            if ( jwelleryDetails != null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<JwelleryModel>(jwelleryDetails, HttpStatus.OK);

        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteJwelleryDetails(@PathVariable int customerId) {
        try {
            JwelleryModel jwelleryDetailExist = jwelleryServices.getJwelleryDetails(customerId);
            if (jwelleryDetailExist != null) {
                jwelleryServices.deleteJwelleryDetails(customerId);
                return new ResponseEntity<String>("Deleted the jwellery details for the respected customer!", HttpStatus.OK);
            } else return new ResponseEntity<String>("Customer not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<String>("Could not delete the customer's jwellery details.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{customerId}")
    public ResponseEntity<JwelleryModel> updateJwelleryDetails(@PathVariable int customerId , @RequestBody JwelleryModel updatedJwelleryDetails){
        try{
            JwelleryModel jwelleryDetailExist = jwelleryServices.getJwelleryDetails(customerId);
            if ( jwelleryDetailExist == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            jwelleryDetailExist.setCustomerId(customerId);
            updateJwelleryDetails(jwelleryDetailExist , updatedJwelleryDetails);
            return new ResponseEntity<JwelleryModel>(jwelleryServices.createJwelleryDetails(jwelleryDetailExist) , HttpStatus.OK);

        }catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateJwelleryDetails(JwelleryModel jwelleryDetailExist , JwelleryModel updatedJwelleryDetails){
        if ( updatedJwelleryDetails.getJwelleryType() != null )
            jwelleryDetailExist.setJwelleryType(updatedJwelleryDetails.getJwelleryType());

        if ( updatedJwelleryDetails.getCarat() != null )
            jwelleryDetailExist.setCarat(updatedJwelleryDetails.getCarat());

        if ( updatedJwelleryDetails.getNetWeight() != null)
            jwelleryDetailExist.setNetWeight(updatedJwelleryDetails.getNetWeight());
    }
}

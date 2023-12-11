package RupeekBank.Loan.Controller;

import RupeekBank.Loan.Model.CustomerModel;
import RupeekBank.Loan.Services.*;
import org.apache.catalina.util.CustomObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CustomerController extends Exception{

    @Autowired
    private CustomerServices customerServices;
    @Autowired
    private KYCServices kycServices;
    @Autowired
    private BankServices bankServices;
    @Autowired
    private JwelleryServices jwelleryServices;
    @Autowired
    private LoanAccountServices loanAccountServices;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerModel>> allCustomers(){
        try {
            return new ResponseEntity<List<CustomerModel>>(customerServices.allCustomers(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerModel> findCustomer(@PathVariable int customerId){
        try{
            CustomerModel foundCustomer = customerServices.findCustomerById(customerId);
            if ( foundCustomer != null)
                return new ResponseEntity<CustomerModel>(foundCustomer,HttpStatus.FOUND);
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerModel> addCustomer(@RequestBody CustomerModel customer){
        try {
            int customerId = customer.getCustomerId();
            CustomerModel customerExist = customerServices.findCustomerById(customerId);
            if ( customerExist != null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            CustomerModel createdCustomer = customerServices.createCustomer(customer);
            return new ResponseEntity<CustomerModel>(createdCustomer , HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<CustomerModel>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/customer/update/{customerId}")
    public ResponseEntity<CustomerModel> updateCustomer(@PathVariable int customerId , @RequestBody CustomerModel updatedCustomer){
        try{
            CustomerModel customerExist = customerServices.findCustomerById(customerId);
            if ( customerExist == null )
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            customerExist.setCustomerId(customerId);
            updateCustomerInfo(customerExist , updatedCustomer);
            return new ResponseEntity<CustomerModel>(customerServices.createCustomer(customerExist) , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateCustomerInfo(CustomerModel customerExist, CustomerModel updatedCustomer) {

        if ( updatedCustomer.getName() != null)
            customerExist.setName(updatedCustomer.getName());

        if ( updatedCustomer.getDOB() != null)
            customerExist.setDOB(updatedCustomer.getDOB());

        if ( updatedCustomer.getCibil() != null) {
            customerExist.setCibil(updatedCustomer.getCibil());
        }

        if ( updatedCustomer.getPhoneNo() != null)
            customerExist.setPhoneNo(updatedCustomer.getPhoneNo());

        if ( updatedCustomer.getEmail() != null)
            customerExist.setEmail(updatedCustomer.getEmail());

        if ( updatedCustomer.getGender() != null)
            customerExist.setGender(updatedCustomer.getGender());

        if ( updatedCustomer.getAge() != null)
            customerExist.setAge(updatedCustomer.getAge());

        if ( updatedCustomer.getMaritalStatus() != null)
            customerExist.setMaritalStatus(updatedCustomer.getMaritalStatus());

        if ( updatedCustomer.getOccupation() != null)
            customerExist.setOccupation(updatedCustomer.getOccupation());


    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId){
        try{
            CustomerModel customerExist = customerServices.findCustomerById(customerId);
            if ( customerExist != null ) {
                customerServices.deleteCustomerById(customerId);
                kycServices.deleteKYCDetails(customerId);
                bankServices.deleteCustomerBank(customerId);
                jwelleryServices.deleteJwelleryDetails(customerId);
                loanAccountServices.deleteAccountDetails(customerId);
                return new ResponseEntity<String>("Deleted the customer and its details !", HttpStatus.OK);
            }
            else return new ResponseEntity<String>("Customer not found!", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<String>("Could not delete the customer." , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

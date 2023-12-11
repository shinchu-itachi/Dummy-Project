package RupeekBank.Loan.Services;

import RupeekBank.Loan.Model.CustomerModel;
import RupeekBank.Loan.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerModel createCustomer(CustomerModel customer){
        return customerRepository.save(customer);
    }
    public List<CustomerModel> allCustomers(){
        return customerRepository.findAll();
    }

    public CustomerModel findCustomerById(int customerId){
        return customerRepository.findById(customerId).orElse(null);
    }

    public void deleteCustomerById(int customerId) {
        customerRepository.deleteById(customerId);
    }
}

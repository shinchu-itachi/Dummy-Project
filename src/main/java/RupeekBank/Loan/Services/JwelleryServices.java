package RupeekBank.Loan.Services;

import RupeekBank.Loan.Model.JwelleryModel;
import RupeekBank.Loan.Repository.JwelleryRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwelleryServices {
    @Autowired
    private JwelleryRespository jwelleryRespository;

    public JwelleryModel createJwelleryDetails(JwelleryModel jwellery) throws Exception {
        try{
            return jwelleryRespository.save(jwellery);
        }catch (Exception e ){
            throw new Exception("Something went wrong. Please try later");
        }
    }

    public void deleteJwelleryDetails(int customerId){
        jwelleryRespository.deleteById(customerId);
    }

    public JwelleryModel getJwelleryDetails(int customerId) {
        return jwelleryRespository.findById(customerId).orElse(null);
    }
}

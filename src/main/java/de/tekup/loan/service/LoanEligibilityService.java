package de.tekup.loan.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.tekup.loan.soap.ws.loaneligebilty.CustomerRequest;
import de.tekup.loan.soap.ws.loaneligebilty.ObjectFactory;
import de.tekup.loan.soap.ws.loaneligebilty.WsResponse;

@Service
public class LoanEligibilityService {

    public WsResponse checkLoanEligebilty(CustomerRequest customerRequest) {
        WsResponse wsResponse = new ObjectFactory().createWsResponse();

        List<String> mismatchs = wsResponse.getCriteriaMismatch();

        if(!(customerRequest.getAge() >= 30 && customerRequest.getAge() <= 50)) {
            mismatchs.add("Client age must be between  30 and 50.");
        }
        if(!(customerRequest.getYearlyIncome() >= 25000)) {
            mismatchs.add("Client yearly income must be over 25000.");
        }
        if(!(customerRequest.getCibilScore() >= 500)) {
            mismatchs.add("Client cibil score must be over 500.");
        }

        if (mismatchs.isEmpty()) {
            wsResponse.setIsEligeble(true);
            long amount = (long)((60 - customerRequest.getAge())*customerRequest.getYearlyIncome()*0.4);
            wsResponse.setApprovedAmount(amount);
        }else {
            wsResponse.setIsEligeble(false);
            wsResponse.setApprovedAmount(0);
        }

        return wsResponse;
    }
}

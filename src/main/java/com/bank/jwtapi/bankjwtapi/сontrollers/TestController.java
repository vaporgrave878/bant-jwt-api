package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.InvalidCodeException;
import com.bank.jwtapi.bankjwtapi.exceptions.NoAvailableAmountException;
import com.bank.jwtapi.bankjwtapi.models.CardRequest;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.repos.CardRequestRepository;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.service.DebitCardService;
import com.bank.jwtapi.bankjwtapi.service.impl.CardRequestServiceImpl;
import com.bank.jwtapi.bankjwtapi.service.impl.DebitCardServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.mail.MessagingException;
import java.util.List;

@RestController
public class TestController {

    private final CardRequestRepository cardRequestRepository;
    private final DebitCardRepository debitCardRepository;
    private final CardRequestServiceImpl cardRequestService;
    private final DebitCardServiceImpl debitCardService;

    public TestController(CardRequestRepository cardRequestRepository, DebitCardRepository debitCardRepository, CardRequestServiceImpl cardRequestService, DebitCardServiceImpl debitCardService) {
        this.cardRequestRepository = cardRequestRepository;
        this.debitCardRepository = debitCardRepository;
        this.cardRequestService = cardRequestService;
        this.debitCardService = debitCardService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 410, message = "Invalid card"),
            @ApiResponse(code = 411, message = "Not enough money")
    })
    @GetMapping("/pay-item")
    public ResponseEntity<CardRequest> getRequest(@RequestBody CardRequest cardRequest) throws MessagingException, CardNotFoundException, NoAvailableAmountException {
        DebitCard card = cardRequest.getCard();
        List<CardRequest> cardRequests = cardRequestRepository.findAll();
        if (!debitCardRepository.existsByNumberAndCvvAndEndDate(card.getNumber(), card.getCvv(), card.getEndDate())){
            throw new CardNotFoundException("The card data is invalid");
        }
        DebitCard foundCard = debitCardRepository.findByNumberAndCvvAndEndDate(card.getNumber(), card.getCvv(), card.getEndDate());
        if (foundCard.getBalance() < cardRequest.getSum() ){
            throw new NoAvailableAmountException("Not enough money");
        }
        String randomCode = RandomString.make(4);
        for (CardRequest request:
             cardRequests) {
            if (request.getCode() == null || !request.getCode().equals(randomCode))
                cardRequest.setCode(randomCode);
        }

        cardRequestRepository.save(cardRequest);
        cardRequestService.sendVerificationCode(cardRequest.getEmail(), cardRequest.getCode());
        return new  ResponseEntity<>(cardRequest, HttpStatus.OK);
    }

    @PostMapping("/pay-item/verify")
    public void verify(@RequestParam String code) throws InvalidCodeException {
        if (!cardRequestRepository.existsByCode(code))
            throw new InvalidCodeException("Your code is invalid");
        CardRequest cardRequest = cardRequestRepository.findByCode(code);
        DebitCard card = cardRequest.getCard();
        DebitCard foundCard = debitCardRepository.findByNumberAndCvvAndEndDate(card.getNumber(), card.getCvv(), card.getEndDate());
        debitCardService.withdrawFromCard(foundCard, cardRequest.getSum());
        cardRequest.setCode(null);
        cardRequestRepository.save(cardRequest);
    }
}

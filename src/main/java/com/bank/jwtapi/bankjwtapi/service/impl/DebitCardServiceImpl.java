package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.dto.DebitCardDto;
import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.models.User;
import com.bank.jwtapi.bankjwtapi.repos.DebitCardRepository;
import com.bank.jwtapi.bankjwtapi.repos.UserRepository;
import com.bank.jwtapi.bankjwtapi.service.DebitCardService;
import com.bank.jwtapi.bankjwtapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class DebitCardServiceImpl implements DebitCardService {

    private final DebitCardRepository debitCardRepository;
    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    public void withdrawFromCard(DebitCardDto paymentDto, String userId) throws Exception, CardNotFoundException {
        User user = userService.findById(userId);
        if (paymentDto.getSum() == null){
            throw new Exception();
            //TODO add exception
        }
        DebitCard debitCard = debitCardRepository.findByNumberAndUser(paymentDto.getCardNumber(), user).orElseThrow(() -> new CardNotFoundException("Card not found"));
        debitCard.setBalance(debitCard.getBalance() - new Double(paymentDto.getSum()));
        debitCardRepository.save(debitCard);
    }

    @Override
    public DebitCard addCard(String userId) throws UserNotFoundException {
        User user = userService.findById(userId);
        System.out.println(user);
        return debitCardRepository.save(DebitCard.builder()
                .number(generateCardNumber())
                .balance(0.0)
                .cvv(generateCVV())
                .endDate(generateEndDate())
                .user(user)
                .build());

    }

    @Override
    public List<DebitCard> userCardsList(String userId) throws UserNotFoundException {
        return debitCardRepository.findAllByUser(userService.findById(userId));
    }

    @Override
    public DebitCard userCard(String number, String userId) throws UserNotFoundException, CardNotFoundException {
        return debitCardRepository.findByNumberAndUser(number, userService.findById(userId)).orElseThrow(() -> new CardNotFoundException("Card not found"));
    }

    @Override
    public Double changeBalance(DebitCardDto debitCardDto, String userId) throws UserNotFoundException, CardNotFoundException {

        DebitCard card = debitCardRepository.findByNumberAndUser(debitCardDto.getCardNumber(), userService.findById(userId)).orElseThrow(() -> new CardNotFoundException("Card not found"));
        double balance = card.getBalance();
        if (debitCardDto.getAction().equals("add"))
            balance += new Double(debitCardDto.getSum());
        else if (debitCardDto.getAction().equals("subtract") || balance - new Double(debitCardDto.getSum()) >= 0)
            balance -= new Double(debitCardDto.getSum());
        card.setBalance(balance);
        debitCardRepository.save(card);
        return balance;
    }

    @Override
    public DebitCard upBalance(String userId, String cardNumber) throws CardNotFoundException {
        DebitCard debitCard = debitCardRepository.findByNumberAndUserId(cardNumber, userId).orElseThrow(() -> new CardNotFoundException("Card not found"));
        return null;
    }

    private String generateCardNumber(){
        Random random = new Random(System.currentTimeMillis());
        int randomNumberLength = 12;
        StringBuilder builder = new StringBuilder("5435");

        do {
            for (int i = 0; i < randomNumberLength; i++) {
                int digit = random.nextInt(10);
                builder.append(digit);
            }
        }
        while (debitCardRepository.existsByNumber(builder.toString()));


//        System.out.println(builder);
        return builder.toString();
    }

    private String generateEndDate(){
        return LocalDateTime.now().getMonthValue() + "/" + (LocalDateTime.now().getYear() - 2000 + 4);
    }

    private String generateCVV(){
        Random random = new Random(System.currentTimeMillis());
        StringBuilder cvv = new StringBuilder();
        do {
            for (int i = 0; i < 3; i++) {
                cvv.append(random.nextInt(10));
            }

        }
        while (debitCardRepository.existsByCvv(String.valueOf(cvv)));
        return cvv.toString();
    }
}

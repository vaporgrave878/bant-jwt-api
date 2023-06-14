package com.bank.jwtapi.bankjwtapi.сontrollers;

import com.bank.jwtapi.bankjwtapi.dto.DebitCardDto;
import com.bank.jwtapi.bankjwtapi.exceptions.CardNotFoundException;
import com.bank.jwtapi.bankjwtapi.exceptions.UserNotFoundException;
import com.bank.jwtapi.bankjwtapi.models.DebitCard;
import com.bank.jwtapi.bankjwtapi.security.jwt.JwtUser;
import com.bank.jwtapi.bankjwtapi.service.DebitCardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/debit-card")
public class DebitCardController {
    private final DebitCardService debitCardService;

    @PostMapping("/withdraw-from-card")
    public void withdrawFromCard(@RequestBody DebitCardDto paymentDto,
                                 JwtUser jwtUser) throws Exception, CardNotFoundException {
        debitCardService.withdrawFromCard(paymentDto, jwtUser.getId());
    }

    @PostMapping("/add-card")
    public DebitCard addCard(JwtUser jwtUser) throws UserNotFoundException {
        return debitCardService.addCard(jwtUser.getId());
    }

    @GetMapping("/cards")
    public List<DebitCard> cardList(JwtUser jwtUser) throws UserNotFoundException {
        return debitCardService.userCardsList(jwtUser.getId());
    }

    @GetMapping("/user-card")
    public DebitCard getCard(JwtUser jwtUser, @RequestBody DebitCardDto debitCardDto) throws UserNotFoundException, CardNotFoundException {
        return debitCardService.userCard(debitCardDto.getCardNumber(), jwtUser.getId());
    }

    @PostMapping("/change-balance")
    public Double changeBalance(JwtUser jwtUser, @RequestBody DebitCardDto debitCardDto) throws UserNotFoundException, CardNotFoundException {
        return debitCardService.changeBalance(debitCardDto, jwtUser.getId());
    }

}

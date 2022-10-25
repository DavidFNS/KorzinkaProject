package uz.nt.cashbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackCardService cashbackCardService;


    @PostMapping("add-cashback")
    public ResponseDto<CashbackCardDto> addCashback(@RequestBody CashbackCardDto cashbackDto){
        return cashbackCardService.addCashback(cashbackDto);
    }

    @PutMapping("/subtract")
    public ResponseDto<Boolean> subtractCashback(@RequestParam Integer userId, @RequestParam Double cashbackAmount){
        return cashbackCardService.subtractUserCashback(userId, cashbackAmount);
    }


    @PutMapping("/increase")
    public ResponseDto<Boolean> increase(@RequestParam Integer userid, @RequestParam Double total){
        return cashbackCardService.increaseCashbackForUser(userid, total);
    }


}

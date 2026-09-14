package com.multi.mlpenterpriseapprovalsystem.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 결제 화면용 컨트롤러
 *
 * @author : 이지헌
 * @filename : ViewPaymentMethodController
 * @since : 25. 12. 17. 수요일
 */

@Controller
public class ViewPaymentController {

    // 결제 수단 등록 화면 반환
    @GetMapping("/payment-methods/register")
    public String viewPaymentMethodRegister(Model model) {
        return "payment/method/register";
    }

    // 결제 수단 목록 화면 반환
    @GetMapping("/payment-methods")
    public String viewPaymentMethods(){
        return "payment/method/list";
    }

    // 결제 내역 목록 화면 반환
    @GetMapping("/payment-historys")
    public String viewPaymentHistorys(Model model) {
        return "payment/history/list";
    }
}

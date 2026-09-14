package com.multi.mlpenterpriseapprovalsystem.subscription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 요금제 화면용 컨트롤러
 *
 * @author : 이지헌
 * @filename : ViewSubscriptionController
 * @since : 25. 12. 16. 화요일
 */

@Controller
public class ViewSubscriptionController {

    // 요금제 목록 화면 반환
    @GetMapping("/subscriptions")
    public String viewSubscriptions() {
        return "subscription/list";
    }

    // 요금제 소개 화면 반환
    @GetMapping("/subscriptions/intro")
    public String viewSubscriptionsIntro() {
        return "subscription/intro";
    }



}

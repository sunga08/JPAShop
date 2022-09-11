package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createFrom(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String createFrom(@RequestParam("memberId") Long memberId,
                             @RequestParam("itemId") Long itemId,
                             @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count); //여러개 상품 선택할 수 있도록 변경해보기

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch); //단순히 조회하는거면 컨트롤러에서 리포지토리 호출해도 ㄱㅊ
        model.addAttribute("orders", orders);

        return "order/orderList";

    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancleOrder(@PathVariable("orderId") Long orderId){
        orderService.candelOrder(orderId);
        return "redirect:/orders";
    }
}

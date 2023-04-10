package inu.appcenter.study3.service;

import inu.appcenter.study3.domain.Member;
import inu.appcenter.study3.domain.Order;
import inu.appcenter.study3.domain.Product;
import inu.appcenter.study3.exception.MemberException;
import inu.appcenter.study3.model.order.OrderSaveRequest;
import inu.appcenter.study3.repository.MemberRepository;
import inu.appcenter.study3.repository.OrderRepository;
import inu.appcenter.study3.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional      // 더티 체킹 변경 감지
    public Long createOrder(Long memberId, OrderSaveRequest orderSaveRequest) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원입니다."));

        Product findProduct = productRepository.findById(orderSaveRequest.getProdctId())
                .orElseThrow(() -> new RuntimeException("이 부분 커스텀에러로 만들어서 구현하세용"));

        Order order = Order.createOrder(orderSaveRequest.getCount(), findMember, findProduct);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }
}

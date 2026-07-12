package hello.springadv1.proxy.app.v3;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceV3Proxy {

  private final OrderRepositoryV3Proxy orderRepository;

  public OrderServiceV3Proxy(OrderRepositoryV3Proxy orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void orderItem(String itemId) {
    orderRepository.save(itemId);
  }

}

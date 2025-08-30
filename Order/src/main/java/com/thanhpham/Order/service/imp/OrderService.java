package com.thanhpham.Order.service.imp;

import ch.qos.logback.core.util.StringUtil;
import com.thanhpham.Order.dto.request.OrderCreateRequest;
import com.thanhpham.Order.dto.request.OrderItemRequest;
import com.thanhpham.Order.dto.response.OrderResponse;
import com.thanhpham.Order.entity.Order;
import com.thanhpham.Order.entity.OrderItem;
import com.thanhpham.Order.entity.OrderStatus;
import com.thanhpham.Order.exception.BadRequestException;
import com.thanhpham.Order.exception.ResourceNotFoundException;
import com.thanhpham.Order.repository.OrderRepository;
import com.thanhpham.Order.service.IOrderService;
import com.thanhpham.Order.service.InventoryEventProducer;
import com.thanhpham.Order.service.httpClient.ProductClient;
import com.thanhpham.Order.service.httpClient.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryEventProducer inventoryEventProducer;
    private final UserClient userClient;

    @Override
    @Transactional
    public String createOrder(OrderCreateRequest request) {
        if(!userClient.doesUserExist(request.getUserId())){
            throw new BadRequestException("User does not exist");
        }
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if(request.getItems().isEmpty())
        {
            throw new BadRequestException("Order items cannot be blank");
        }

        for (OrderItemRequest itemRequest : request.getItems()) {

            BigDecimal price = productClient.getPrice(itemRequest.getVariantId());
            OrderItem item = new OrderItem();
            item.setVariantId(itemRequest.getVariantId());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(price);
            item.setOrder(order);

            total = total.add(price.multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            orderItems.add(item);
        }

        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        order = orderRepository.save(order);
        inventoryEventProducer.sendReservedInventory(order.getId(), orderItems);
        return "Order is processing";
    }

    @Transactional
    @Override
    public String cancelOrder(Long id) {

        Order order = findOrderByIdEntity(id);
        if(order.getStatus() == OrderStatus.CANCELLED)
        {
            return "Order already cancelled";
        }
        else if(order.getStatus() == OrderStatus.SHIPPED || order.getStatus() == OrderStatus.DELIVERED)
        {
            return "Order cannot be cancelled because it has already been shipped or delivered";
        }

        order.setStatus(OrderStatus.CANCELLED);

        List<OrderItem> list = order.getOrderItems();
        inventoryEventProducer.sendCancelInventory(order.getId(), list);
        orderRepository.save(order);
        return "Order is processing";
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findOrderById(Long id) {
        return OrderResponse.fromEntity(
                findOrderByIdEntity(id)
        );
    }

    @Transactional(readOnly = true)
    private Order findOrderByIdEntity(Long id)
    {
        return orderRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Order","OrderId",id.toString())
        );
    }

}

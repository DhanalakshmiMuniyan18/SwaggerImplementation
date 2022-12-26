package com.ideas2it.groceryshop.service;

import com.ideas2it.groceryshop.dto.OrderDetailResponseDto;
import com.ideas2it.groceryshop.dto.OrderRequestDto;
import com.ideas2it.groceryshop.dto.OrderResponseDto;
import com.ideas2it.groceryshop.dto.SuccessResponseDto;
import com.ideas2it.groceryshop.exception.NotFoundException;
import com.ideas2it.groceryshop.mapper.OrderDetailMapper;
import com.ideas2it.groceryshop.mapper.OrderMapper;
import com.ideas2it.groceryshop.model.*;
import com.ideas2it.groceryshop.repository.OrderRepository;
import com.ideas2it.groceryshop.repository.ProductRepository;
import com.ideas2it.groceryshop.repository.StockRepository;
import com.ideas2it.groceryshop.service.impl.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {OrderService.class})
public class OrderServiceTest {

    private final CartServiceImpl cartService = mock(CartServiceImpl.class);

    private final AddressServiceImpl addressService = mock(AddressServiceImpl.class);
    private final ProductServiceImpl productService = mock(ProductServiceImpl.class);
    private final UserServiceImpl userService = mock(UserServiceImpl.class);
    private final StockServiceImpl stockService = mock(StockServiceImpl.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final StockRepository stockRepository = mock(StockRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);

    @InjectMocks
    private OrderServiceImpl orderService = new OrderServiceImpl(orderRepository,
            addressService, cartService, productService, userService, stockService);



    @Test
    public void placeOrder_Cart_not_empty() throws NotFoundException {
        OrderRequestDto orderRequestDto = new OrderRequestDto(0, 0, 1);
        Role role = new Role(1, "Customer", true);
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,"dhana@gmail.com",
                true,role,new Date(2022/12/11),1,new Date(2022/12/12),1);
        Category category = new Category(1,"fruits & vegetables", null, true);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100, true, subCategory, "kg",
                1);
        List<CartDetail> cartDetails = Collections.singletonList(new CartDetail(1, 5,
                50f, product,true,
                new Cart(1,50f,1, null,true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,true, user);
        List<OrderDetail> orderDetails = Collections.singletonList(new OrderDetail(1,1,
                50f,product));
        Address shippingAddress = new Address(1,"1st street","Guindy",600012,
                "Hospital",true,true,user);
        OrderDelivery orderDelivery = new OrderDelivery(1,false,new Date(2022/12/12),
                new Date(2022/12/12),new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,cart,user, new OrderDelivery()),shippingAddress);
        Order order = new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,cart,user,orderDelivery);

        SuccessResponseDto successResponseDto = new SuccessResponseDto(200, "Order Placed Successfully");
        when(cartService.getActiveCartOfCurrentUser()).thenReturn(cart);
        when(addressService.getAddressByAddressId(orderRequestDto.getAddressId())).thenReturn (Optional.of(shippingAddress));
        when(orderRepository.save(order)).thenReturn(order);
        when(cartService.removeCart()).thenReturn(successResponseDto);
        when(orderService.placeOrder(orderRequestDto)).thenReturn(successResponseDto);
        Mockito.doNothing().when(stockService).removeStockByOrderDetails(order, 600012);
        SuccessResponseDto orderPlaced = orderService.placeOrder(orderRequestDto);
        assertEquals(successResponseDto.getStatusCode(), 200);
    }

    @Test
    public void buyNow() throws NotFoundException {
        OrderRequestDto orderRequestDto = new OrderRequestDto(1, 1,
                1);
        Role role = new Role(1, "Customer", true);
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,
                "dhana@gmail.com",true, role, new Date(2022/12/11),
                1, new Date(2022/12/12),1);
        Address shippingAddress = new Address(1,"1st street","Guindy",
                600012,"Hospital",true,true, user);
        Category category = new Category(1,"fruits & vegetables", null,
                true);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100, true,
                subCategory, "kg",1);
        List<CartDetail> cartDetailList = new ArrayList<>();
        List<OrderDetail> orderDetails = Collections.singletonList(new OrderDetail(1,1,
                50f,product));
        List<CartDetail> cartDetails = Collections.singletonList(new CartDetail(1, 5,
                50f, product,true,
                new Cart(1,50f,1, null,true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,true, user);
        OrderDelivery orderDelivery = new OrderDelivery(1,false,new Date(2022/12/12),
                new Date(2022/12/12),new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,cart,user, new OrderDelivery()),shippingAddress);
        Order order = new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,cart,user,orderDelivery);
        stockService.removeStockByOrderDetails(order,
                orderDelivery.getShippingAddress().getPinCode());
        SuccessResponseDto successResponseDto = new SuccessResponseDto(200,
                "Order Placed Successfully");
        when(orderService.buyNow(orderRequestDto)).thenReturn(successResponseDto);
        when(orderRepository.save(any())).thenReturn(order);
        orderRepository.save(order);
    }

    @Test
    public void viewAllActiveOrders() throws NotFoundException {
//        List<OrderResponseDto>
        Category category = new Category(1,"fruits & vegetables",
                null, true);
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,
                "dhana@gmail.com",
                true,null,new Date(2022/12/11),1,
                new Date(2022/12/12),1);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100, true,
                subCategory, "kg",1);
        List<OrderDetail> orderDetails = Collections.singletonList(
                new OrderDetail(1,1,50f,product));
        OrderDelivery orderDelivery = new OrderDelivery(1,
                false,new Date(2022/12/12), new Date(2022/12/12),
                new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,null, user,
                        new OrderDelivery()),null);
        List<CartDetail> cartDetails = Collections.singletonList(
                new CartDetail(1, 5,50f, product,
                        true,
                new Cart(1,50f,1, null,
                        true, user)));
        Cart cart = new Cart(1,50f,5, cartDetails,
                true, user);
        Order order = new Order(1, new Date(2022/12/12),50f,
                1,
                true,orderDetails,cart,user,orderDelivery);
        List<Order> orders = Collections.singletonList(order);
        List<OrderResponseDto> orderResponseDto = OrderMapper.toOrdersDtoList(orders);
        when(orderRepository.findByIsActive(true)).thenReturn(orders);
        when(orderService.viewAllActiveOrders()).thenReturn(orderResponseDto);
        assertEquals(true, orderResponseDto.get(0).getOrderStatus());
    }

    @Test
    public void viewAllCancelledOrders() throws NotFoundException {
        Category category = new Category(1,"fruits & vegetables",
                null, true);
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,
                "dhana@gmail.com",
                true,null,new Date(2022/12/11),1,
                new Date(2022/12/12),1);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100,
                true, subCategory, "kg",
                1);
        List<OrderDetail> orderDetails = Collections.singletonList(
                new OrderDetail(1,1,50f,product));
        OrderDelivery orderDelivery = new OrderDelivery(1,
                false,new Date(2022/12/12),
                new Date(2022/12/12),new Order(1, new Date(2022/12/12),
                50f,1,
                true,orderDetails,null,user, new OrderDelivery()),
                null);
        List<CartDetail> cartDetails = Collections.singletonList(
                new CartDetail(1, 5,
                50f, product,true,
                new Cart(1,50f,1, null,
                        true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,
                true, user);
        Order order = new Order(1, new Date(2022/12/12),50f,
                1,
                false,orderDetails,cart,user,orderDelivery);
        List<Order> orders = Collections.singletonList(order);
        List<OrderResponseDto> orderResponseDto = OrderMapper.toOrdersDtoList(orders);
        when(orderRepository.findByIsActive(false)).thenReturn(orders);
        when(orderService.viewAllCancelledOrders()).thenReturn(orderResponseDto);
        assertEquals(false, orderResponseDto.get(0).getOrderStatus());
    }

    @Test
    public void viewOrderByUserId() throws NotFoundException {
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,"dhana@gmail.com",
                true,null,new Date(2022/12/11),1,new Date(2022/12/12),1);
        Category category = new Category(1,"fruits & vegetables", null, true);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100, true, subCategory, "kg",
                1);
        List<OrderDetail> orderDetails = Collections.singletonList(new OrderDetail(1,1,
                50f,product));
        OrderDelivery orderDelivery = new OrderDelivery(1,false,new Date(2022/12/12),
                new Date(2022/12/12),new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,null,user, new OrderDelivery()),null);
        List<CartDetail> cartDetails = Collections.singletonList(new CartDetail(1, 5,
                50f, product,true,
                new Cart(1,50f,1, null,true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,true, user);
        Order order = new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,cart,user,orderDelivery);
        List<Order> orders = Collections.singletonList(order);
        List<OrderResponseDto> orderResponseDto = OrderMapper.toOrdersDtoList(orders);
        Integer id = 1;
        when(orderRepository.findByUserId(id)).thenReturn(orders);
        when(orderService.viewOrderByUserId(id)).thenReturn(orderResponseDto);
        assertEquals(1, orderResponseDto.get(0).getUserId());
    }

    @Test
    public void viewOrdersByProductId() throws NotFoundException {
        Category category = new Category(1,"fruits & vegetables", null, true);
        Category subCategory = new Category(1,"vegetables", category, true);
        Product product = new Product(1,"Tomato", 100, true, subCategory, "kg",
                1);
        List<OrderDetail> orderDetails = Collections.singletonList(new OrderDetail(1,1,
                50f,product));
        List<OrderDetailResponseDto> orderDetailsResponseDto = OrderDetailMapper.
                toOrderDetailDtoList(orderDetails);
        when(orderRepository.findByProductId(1)).thenReturn(orderDetails);
        when(orderService.viewOrdersByProductId(1)).thenReturn(orderDetailsResponseDto);
        assertEquals("Tomato", orderDetailsResponseDto.get(0).getProductName());
    }

    @Test
    public void viewOrdersByDate() throws Exception {
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,
                "dhana@gmail.com",true,null,
                new Date(2022/12/11),1, new Date(2022/12/12),
                1);
        Category category = new Category(1,"fruits & vegetables",
                null, true);
        Category subCategory = new Category(1,"vegetables",
                category, true);
        Product product = new Product(1,"Tomato", 100,
                true, subCategory, "kg",
                1);
        List<OrderDetail> orderDetails = Collections.singletonList(
                new OrderDetail(1,1,50f, product));
        OrderDelivery orderDelivery = new OrderDelivery(1,false,
                new Date(2022/12/12), new Date(2022/12/12),
                new Order(1, new Date(2022/12/12),50f,1,
                true,orderDetails,null,user, new OrderDelivery()),
                null);
        List<CartDetail> cartDetails = Collections.singletonList(
                new CartDetail(1, 5,50f, product,
                        true, new Cart(1,50f,
                        1, null,true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,
                true, user);
        Order order = new Order(1, new Date(2022/12/12),50f,
                1,true, orderDetails, cart,
                user,orderDelivery);
        List<Order> orders = Collections.singletonList(order);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
        Date date = formatter.parse("2022/12/11");
        List<OrderResponseDto> orderResponseDto = OrderMapper.toOrdersDtoList(orders);
        when(orderRepository.findByOrderedDate(date)).thenReturn(orders);
        when(orderService.viewOrdersByDate(date)).thenReturn(orderResponseDto);
        assertEquals(date, orderResponseDto.get(0).getOrderedDate().getDate());
    }

    @Test
    public void viewOrdersByIdAndDate() throws Exception {
        User user = new User(1,"dhana","M",
                "M","dhana@123",9941819923L,
                "dhana@gmail.com",true,null,
                new Date(2022/12/11),1,new Date(2022/12/12),1);
        Category category = new Category(1,"fruits & vegetables",
                   null, true);
        Category subCategory = new Category(1,"vegetables",
                               category, true);
        Product product = new Product(1,"Tomato", 100,
                  true, subCategory, "kg",1);
        List<OrderDetail> orderDetails = Collections.singletonList(
                new OrderDetail(1,1, 50f,product));
        OrderDelivery orderDelivery = new OrderDelivery(1,false,
                                      new Date(2022/12/12), new Date(2022/12/12),
                                      new Order(1, new Date(2022/12/12),
                                              50f,1, true,
                                              orderDetails,null, user,
                                              new OrderDelivery()),null);
        List<CartDetail> cartDetails = Collections.singletonList(
                                       new CartDetail(1, 5,
                                  50f, product,true,
                                       new Cart(1,50f,1,
                                               null,true,user)));
        Cart cart = new Cart(1,50f,5, cartDetails,
                true, user);
        Order order = new Order(1, new Date(2022/12/12),
                50f,1,true,
                orderDetails, cart, user, orderDelivery);
        List<Order> orders = Collections.singletonList(order);
        List<OrderResponseDto> orderResponseDto = OrderMapper.toOrdersDtoList(orders);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/M/yyyy");
        Date date = formatter.parse("2022/12/11");
        when(orderRepository.findByOrderedDateAndUserId(date, 1)).thenReturn(orders);
        when(orderService.viewOrdersByIdAndDate(date, 1)).thenReturn(orderResponseDto);
        assertEquals(date, orderResponseDto.get(0).getOrderedDate());
    }
}








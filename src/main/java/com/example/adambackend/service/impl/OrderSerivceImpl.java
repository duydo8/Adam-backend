package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.entities.HistoryOrder;
import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.detailOrder.DetailOrderAdminPayBack;
import com.example.adambackend.payload.order.OrderAdmin;
import com.example.adambackend.payload.order.OrderFindAll;
import com.example.adambackend.payload.order.OrderFindAllResponse;
import com.example.adambackend.payload.order.OrderPayBackResponse;
import com.example.adambackend.payload.order.OrderReturn;
import com.example.adambackend.payload.order.OrderUpdatePayBack;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.satistic.Dashboard;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import com.example.adambackend.service.CartItemService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.DiscountOrderService;
import com.example.adambackend.service.EventService;
import com.example.adambackend.service.HistoryOrderService;
import com.example.adambackend.service.OrderService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSerivceImpl implements OrderService {

	private static final double ORDER_RETURN_PRICE = 30000;

	private static final double ORDER_MAX_PRICE = 5000000;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private DetailProductService detailProductService;

	@Autowired
	private DiscountOrderService discountOrderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DetailOrderService detailOrderService;

	@Autowired
	private HistoryOrderService historyOrderService;

	@Autowired
	private EventService eventService;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public void deleteById(Integer id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public List<Double> sumTotalPriceByTime(Integer year) {
		return orderRepository.sumTotalPriceByTime(year);
	}

	@Override
	public List<Order> findByAccountId(Integer accountId, Integer status) {
		return orderRepository.findOrderByAccountId(accountId, status);
	}

	@Override
	public Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
		return orderRepository.countAllOrderByTime(startDate, endDate);

	}

	@Override
	public Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
		return orderRepository.countCancelOrderByTime(startDate, endDate);
	}

	@Override
	public Integer countsuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate) {
		return orderRepository.countSuccessOrderByTime(startDate, endDate);
	}

	@Override
	public List<Double> sumSuccessOrderByTime(Integer year) {
		return orderRepository.sumSuccessOrderByTime(year);
	}

	@Override
	public List<Double> sumCancelOrderByTime(Integer year) {
		return orderRepository.sumCancelOrderByTime(year);
	}

	@Override
	public List<Double> sumPaybackOrderByTime(Integer year) {
		return orderRepository.sumPaybackOrderByTime(year);
	}

	@Override
	public boolean getErrorsFindAll(Integer page, Integer size) {
		if (CommonUtil.isNotNull(page) && CommonUtil.isNotNull(size) && page > 0 && size > 0) {
			return true;
		}
		return false;
	}

	@Override
	public OrderAdmin findAllOrderAdmin(Integer page, Integer size, Integer status) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		List<OrderFindAll> orderFindAlls = orderRepository.findByStatus(pageable, status);
		List<OrderFindAllResponse> orderFindAllResponses = new ArrayList<>();
		for (OrderFindAll order : orderFindAlls) {
			OrderFindAllResponse orderFindAllResponse = new OrderFindAllResponse();
			BeanUtils.copyProperties(order, orderFindAllResponse);
			orderFindAllResponse.setAddress(addressService.findByAddressId(order.getAddressId()));
			orderFindAllResponse.setDetailOrders(detailOrderService.findByOrderId(order.getId()));
		}
		return new OrderAdmin(orderFindAllResponses, orderFindAllResponses.size());
	}

	@Override
	public IGenericResponse updateStatusOrder(Integer orderId, Integer status) {
		Optional<Order> orderOptional = orderRepository.findById(orderId);
		if (orderOptional.isPresent()) {
			orderOptional.get().setStatus(status);
			List<DetailOrder> detailOrderList = orderOptional.get().getDetailOrders();
			if (status == 2) {
				// handle history order
				HistoryOrder historyOrder = historyOrderService.findLastHistoryOrderByOrderId(orderId);
				if (historyOrder.getStatus() == 1 || historyOrder.getStatus() == 5) {
					for (DetailOrder detailOrder : detailOrderList) {
						DetailProduct detailProduct = detailOrder.getDetailProduct();
						detailProduct.setQuantity(detailProduct.getQuantity() - detailOrder.getQuantity());
						detailProductService.save(detailProduct);
					}
				} else {
					return new IGenericResponse(400, "History order not allowed");
				}
			} else if (status == 0) {
				HistoryOrder historyOrder = historyOrderService.findLastHistoryOrderByOrderId(orderId);
				if (historyOrder.getStatus() != 6) {
					for (DetailOrder detailOrder : detailOrderList
					) {
						DetailProduct detailProduct = detailOrder.getDetailProduct();
						detailProduct.setQuantity(detailProduct.getQuantity() + detailOrder.getQuantity());
						detailProductService.save(detailProduct);
					}
				}
				// handle account priority
				if (historyOrder.getStatus() != 1) {
					Account account = orderOptional.get().getAccount();
					account.setPriority(account.getPriority() - 1);
					if (account.getPriority() <= -5) {
						account.setStatus(0);
					}
					accountService.save(account);
				}
			} else if (status == 6) {
				// handle account priority
				Account account = orderOptional.get().getAccount();
				if (account.getPriority() <= 5) {
					double priority = account.getPriority() + 0.5;
					account.setPriority(priority);
				}
				accountService.save(account);
			}
			HistoryOrder historyOrder = new HistoryOrder(orderOptional.get());
			historyOrderService.save(historyOrder);
			return new IGenericResponse(orderRepository.save(orderOptional.get()), 200, "successfully");
		}
		return new IGenericResponse(400, "not found order");
	}

	@Override
	public Order updateOrder(Order order) {
		int status = order.getStatus();
		List<DetailOrder> detailOrderList = order.getDetailOrders();
		if (status == 2) {
			// handle history order
			HistoryOrder historyOrder = historyOrderService.findLastHistoryOrderByOrderId(order.getId());
			if (historyOrder.getStatus() == 1 || historyOrder.getStatus() == 5) {
				for (DetailOrder detailOrder : detailOrderList) {
					DetailProduct detailProduct = detailOrder.getDetailProduct();
					detailProduct.setQuantity(detailProduct.getQuantity() - detailOrder.getQuantity());
					detailProductService.save(detailProduct);
				}
			}
		} else if (status == 0) {
			HistoryOrder historyOrder = historyOrderService.findLastHistoryOrderByOrderId(order.getId());
			if (historyOrder.getStatus() != 6) {
				for (DetailOrder detailOrder : detailOrderList
				) {
					DetailProduct detailProduct = detailOrder.getDetailProduct();
					detailProduct.setQuantity(detailProduct.getQuantity() + detailOrder.getQuantity());
					detailProductService.save(detailProduct);
				}
			}
			// handle account priority
			if (historyOrder.getStatus() != 1) {
				Account account = order.getAccount();
				account.setPriority(account.getPriority() - 1);
				if (account.getPriority() <= -5) {
					account.setStatus(0);
				}
				accountService.save(account);
			}
		} else if (status == 6) {
			// handle account priority
			Account account = order.getAccount();
			if (account.getPriority() <= 5) {
				double priority = account.getPriority() + 0.5;
				account.setPriority(priority);
			}
			accountService.save(account);
		}
		HistoryOrder historyOrder = new HistoryOrder(order);
		historyOrderService.save(historyOrder);
		return orderRepository.save(order);
	}

	@Override
	public Order updateSuccess(Order order) {
		order.setStatus(6);
		Account account = order.getAccount();
		if (account.getPriority() <= 5) {
			account.setPriority(account.getPriority() + 0.5);
		}
		accountService.save(account);
		HistoryOrder historyOrder = new HistoryOrder();
		historyOrder.setOrder(order);
		historyOrder.setDescription("order success");
		historyOrder.setUpdateTime(LocalDateTime.now());
		historyOrder.setTotalPrice(order.getTotalPrice());
		historyOrder.setStatus(6);
		historyOrderService.save(historyOrder);
		return orderRepository.save(order);
	}

	@Override
	public IGenericResponse getOrderReturn(OrderReturn orderReturn) {
		Optional<Order> orderOptional = orderRepository.findByCode(orderReturn.getOrderCode());
		if (orderOptional.isPresent()) {
			List<DetailOrderAdminPayBack> detailOrderCode = orderReturn.getDetailOrderAdminPayBacks();
			if (LocalDate.now().minusDays(3).isAfter(orderOptional.get().getCreateDate().toLocalDate())) {
				return new IGenericResponse<>(200, "Payback deadline is overdue");
			}
			Integer totalQuantity = 0;
			Double returnPrice = 0.0;
			Double amountPrice = 0.0;
			for (DetailOrderAdminPayBack detailOrderPayBack : detailOrderCode) {
				DetailOrder detailOrder = detailOrderService.findByCode(detailOrderPayBack.getDetailOrderCode());
				if (detailOrder.getQuantity() < detailOrderPayBack.getQuantity()) {
					return new IGenericResponse<>(200, "Do not deduct quantities larger than the purchase quantity");
				}
				if (detailOrderPayBack.getQuantity() < 0) {
					return new IGenericResponse<>(200, "Invalid quantity");
				}
				detailOrderService.updateReason(orderReturn.getReason(), detailOrder.getId());
				returnPrice += detailOrder.getPrice() * detailOrderPayBack.getQuantity();
				amountPrice += detailOrder.getPrice() * detailOrder.getQuantity();
				totalQuantity += detailOrderPayBack.getQuantity();
				DetailProduct detailProduct = detailOrder.getDetailProduct();
				detailProduct.setQuantity(detailProduct.getQuantity() + detailOrderPayBack.getQuantity());
				detailProductService.save(detailProduct);
				if (detailOrder.getQuantity() == detailOrderPayBack.getQuantity()) {
					detailOrderService.deleteById(detailOrder.getId());
				}
			}
			returnPrice += ORDER_RETURN_PRICE;
			Double totalAmountPrice = orderOptional.get().getAmountPrice() - amountPrice;
			Double salePrice = getSalePrice(totalAmountPrice);
			Double totalPrice = totalAmountPrice - salePrice + returnPrice;
			HistoryOrder historyOrder = new HistoryOrder();
			historyOrder.setOrder(orderOptional.get());
			historyOrder.setUpdateTime(LocalDateTime.now());
			historyOrder.setStatus(orderReturn.getStatus());
			historyOrder.setStatus(1);
			historyOrder.setDescription("order payback");
			historyOrder.setTotalPrice(totalPrice);
			historyOrderService.save(historyOrder);
			orderRepository.updateReturnOrder(returnPrice, totalAmountPrice, totalPrice, orderReturn.getStatus(), orderOptional.get().getId());
			return new IGenericResponse<>(new OrderPayBackResponse(totalQuantity, totalPrice), 200, "successfully");
		} else {
			return new IGenericResponse<>(200, "not found");
		}
	}

	@Override
	public IGenericResponse updateOrderPayBack(OrderUpdatePayBack orderUpdatePayBack) {
		Optional<Order> orderOptional = orderRepository.findById(orderUpdatePayBack.getOrderId());
		List<CartItems> cartItemsList = orderOptional.get().getCartItems();
		Double ammountPrice = orderOptional.get().getAmountPrice();
		for (Integer cartId : orderUpdatePayBack.getCartItemIds()
		) {
			Optional<CartItems> cartItemsOptional = cartItemService.findById(cartId);
			if (cartItemsOptional.isPresent()) {

				cartItemsList.add(cartItemsOptional.get());
				DetailProduct detailProduct = cartItemsOptional.get().getDetailProduct();

				if (detailProduct.getQuantity() - cartItemsOptional.get().getQuantity() < 0) {
					return new IGenericResponse(400, "Không đủ số lượng ");
				}

				ammountPrice += cartItemsOptional.get().getTotalPrice();
				detailProduct.setQuantity(detailProduct.getQuantity() - cartItemsOptional.get().getQuantity());
				detailProductService.save(detailProduct);
				cartItemService.updateStatus(0, cartId);
				DetailOrder detailOrder = new DetailOrder();
				detailOrder.setQuantity(cartItemsOptional.get().getQuantity());
				detailOrder.setTotalPrice(cartItemsOptional.get().getTotalPrice());
				detailOrder.setCreateDate(LocalDateTime.now());
				detailOrder.setPrice(detailProduct.getPriceExport());
				detailOrder.setStatus(1);
				detailOrder.setDetailProduct(detailProduct);
				detailOrder.setOrder(orderOptional.get());
				String x1 = RandomString.make(64) + orderOptional.get().getId();
				detailOrder.setDetailOrderCode(x1);
				detailOrderService.save(detailOrder);
			}
		}
		orderOptional.get().setAmountPrice(ammountPrice);
		orderOptional.get().setCartItems(cartItemsList);
		Double salePrice = this.getSalePrice(ammountPrice);
		System.out.println(salePrice);
		orderOptional.get().setSalePrice(salePrice);
		orderOptional.get().setTotalPrice(ammountPrice - salePrice);
		Order order = orderRepository.save(orderOptional.get());
		HistoryOrder historyOrder = new HistoryOrder();
		historyOrder.setOrder(order);
		historyOrder.setDescription("create time");
		historyOrder.setUpdateTime(LocalDateTime.now());
		historyOrder.setTotalPrice(order.getTotalPrice());
		historyOrder.setStatus(1);
		historyOrderService.save(historyOrder);
		for (Integer x : orderUpdatePayBack.getCartItemIds()
		) {
			Optional<CartItems> cartItemsOptional = cartItemService.findById(x);
			if (cartItemsOptional.isPresent()) {
				cartItemsOptional.get().setOrder(order);
				cartItemService.save(cartItemsOptional.get());
			}
		}
		return new IGenericResponse<>(order, 200, "successfully");
	}

	@Override
	public List<Order> findOrderByAccountId(Integer accountId, Integer status) {
		return orderRepository.findOrderByAccountId(accountId, status);
	}

	@Override
	public Double getSalePrice(Double ammountPrice) {
		List<Integer> listId = new ArrayList<>();
		List<DiscountOrder> discountOrders = new ArrayList<>();
		List<Event> events = eventService.findAllByTime();
		for (Event e : events
		) {
			discountOrders = discountOrderService.findByTotalPriceAndTime(ammountPrice, e.getId());
			for (DiscountOrder d : discountOrders
			) {
				listId.add(d.getId());
			}
		}

		Double salePrice = 0.0;
		Double salePricePercent = 0.0;
		for (Integer x : listId) {
			DiscountOrder discountOrder = discountOrderService.getById(x);

			if (discountOrder.getSalePrice() < 1) {
				salePricePercent += discountOrder.getSalePrice();

			} else {
				salePrice += discountOrder.getSalePrice();
			}
		}
		return salePrice + (salePricePercent * ammountPrice);

	}

	@Override
	public IGenericResponse createOder(OrderWebsiteCreate orderWebsiteCreate, Account account, Address address) {
		Double ammountPrice = 0.0;
		Order order = new Order();
		order.setAccount(account);
		order.setCreateDate(LocalDateTime.now());
		order.setStatus(1);
		order.setAddress(address);
		order.setFullName(orderWebsiteCreate.getFullName());
		order.setPhoneNumber(orderWebsiteCreate.getPhoneNumber());
		order.setAmountPrice(ammountPrice);
		order.setAddressDetail(orderWebsiteCreate.getAddressDetail());
		order.setTotalPrice(0.0);
		order.setSalePrice(0.0);
		order = orderRepository.save(order);

		List<CartItems> cartItemsList = new ArrayList<>();
		for (Integer x : orderWebsiteCreate.getCartItemIdList()) {
			Optional<CartItems> cartItemsOptional = cartItemService.findByIds(x);
			if (cartItemsOptional.isPresent()) {
				cartItemsList.add(cartItemsOptional.get());
				DetailProduct detailProduct = cartItemsOptional.get().getDetailProduct();
				if (detailProduct.getQuantity() - cartItemsOptional.get().getQuantity() < 0) {
					return new IGenericResponse(400, "Không đủ số lượng ");
				}

				ammountPrice += cartItemsOptional.get().getTotalPrice();
				detailProduct.setQuantity(detailProduct.getQuantity() - cartItemsOptional.get().getQuantity());
				detailProductService.save(detailProduct);
				cartItemService.updateStatus(0, x);
				DetailOrder detailOrder = new DetailOrder();
				detailOrder.setQuantity(cartItemsOptional.get().getQuantity());
				detailOrder.setTotalPrice(cartItemsOptional.get().getTotalPrice());
				detailOrder.setCreateDate(LocalDateTime.now());
				detailOrder.setPrice(detailProduct.getPriceExport());
				detailOrder.setStatus(1);
				detailOrder.setDetailProduct(detailProduct);
				detailOrder.setOrder(order);
				String x1 = RandomString.make(64) + order.getId();
				detailOrder.setDetailOrderCode(x1);
				detailOrderService.save(detailOrder);
			}
		}
		order.setAmountPrice(ammountPrice);
		order.setCartItems(cartItemsList);
		Double totalPrice = 0.0;
		String code = RandomString.make(64) + order.getId();
		Double totalSalePrice = this.getSalePrice(ammountPrice);
		order.setSalePrice((double) Math.round(totalSalePrice));
		totalPrice = ammountPrice - totalSalePrice;
		order.setOrderCode(code);
		order.setTotalPrice(totalPrice);
		if (totalPrice > ORDER_MAX_PRICE) {
			return new IGenericResponse(400, "đơn hàng không được quá 5tr, vui lòng liên hệ admin hoặc đến cửa hàng gần nhất ");
		}
		for (Integer x : orderWebsiteCreate.getCartItemIdList()) {
			Optional<CartItems> cartItemsOptional = cartItemService.findById(x);
			if (cartItemsOptional.isPresent()) {
				cartItemsOptional.get().setOrder(order);
				cartItemService.save(cartItemsOptional.get());
			}
		}
		return new IGenericResponse<>(order, 200, "Thành công");
	}

	@Override
	public List<Dashboard> getOrderSatistic(List<String> months) {
		List<Dashboard> dashboardList = new ArrayList<>();
		Dashboard dashboard = new Dashboard();
		dashboard.setName("Tổng đơn hàng");
		dashboard.setLabels(months);
		List<Double> doubleList = orderRepository.sumTotalPriceByTime(LocalDate.now().getYear());
		dashboard.setData(doubleList);

		Dashboard dashboard1 = new Dashboard();
		dashboard1.setName("Đơn thành công");
		dashboard1.setLabels(months);
		List<Double> doubleList1 = orderRepository.sumSuccessOrderByTime(LocalDate.now().getYear());
		dashboard1.setData(doubleList1);

		Dashboard dashboard2 = new Dashboard();
		dashboard2.setName("Đơn Hủy");
		dashboard2.setLabels(months);
		List<Double> doubleList2 = orderRepository.sumCancelOrderByTime(LocalDate.now().getYear());
		dashboard2.setData(doubleList2);

		Dashboard dashboard3 = new Dashboard();
		dashboard3.setName("Đơn Đổi Trả");
		dashboard3.setLabels(months);
		List<Double> doubleList3 = orderRepository.sumPaybackOrderByTime(LocalDate.now().getYear());
		dashboard3.setData(doubleList3);

		dashboardList.add(dashboard);
		dashboardList.add(dashboard1);
		dashboardList.add(dashboard2);
		dashboardList.add(dashboard3);
		return dashboardList;
	}
}
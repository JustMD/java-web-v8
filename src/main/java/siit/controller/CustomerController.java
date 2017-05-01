package siit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import siit.dao.CustomerDao;
import siit.model.Customer;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerDao customerDao;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView displayForm() {
		ModelAndView mav = new ModelAndView("customer-list");
		List<Customer> customers = customerDao.getAllCustomers();
		mav.addObject("customers", customers);
		return mav;
	}
	
	@RequestMapping("/{customerId}/edit")
	public ModelAndView editCustomer(@PathVariable("customerId") int customerId){
		ModelAndView mav = new ModelAndView("customer-edit");
		Customer customer = customerDao.getCustomerById(customerId);
		mav.addObject("customer", customer);
		return mav;
	}
	
	@RequestMapping(path = "/{customerId}/edit", method = RequestMethod.POST)
	public String saveCustomer(@PathVariable("customerId") int customerId, 
			@RequestParam String name){
		customerDao.updateCustomer(customerId, name);
		return "redirect:/customers";
	}
	
	@RequestMapping("/{customerId}/orders/list")
	public ModelAndView listOrders(@PathVariable("customerId") int customerId){

		ModelAndView mav = new ModelAndView("/customers-orders-list");
		mav.addObject("customer", customerDao.getCustomerById(customerId));

		return mav;
	}
	
	@RequestMapping("/{customerId}/order/add")
	public ModelAndView addOrder(@PathVariable("customerId") int customerId){
		ModelAndView mav = new ModelAndView("/order-add");
		mav.addObject("customer", customerDao.getCustomerById(customerId));
		return mav;
	}
	

	@RequestMapping(path = "/{customerId}/order/add", method = RequestMethod.POST)
	public String addOrder(@PathVariable("customerId") int customerId, 
			@RequestParam Integer idOrder){
		customerDao.addOrder(customerId, idOrder);
		return "redirect:/customers/" +customerId+ "/orders/list";
	}
}

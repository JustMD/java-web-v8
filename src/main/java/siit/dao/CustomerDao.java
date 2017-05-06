package siit.dao;

import java.util.List;

import siit.model.Customer;

public interface CustomerDao {
	Customer getCustomerById(int id);
	void updateCustomer(int id, String newName);
	List<Customer> getAllCustomers();
	void addOrder(int idCust, int idOrder);
	void removeOrder(int idOrder);
	boolean verifyOrder(int idOrder);
}

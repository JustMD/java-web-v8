package siit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import siit.model.Customer;
import siit.model.Order;
import siit.model.Product;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	
	private JdbcTemplate db;
	
	@Autowired
	 public void setDataSource(DataSource dataSource) {
		db = new JdbcTemplate(dataSource);
	}


	private static final RowMapper<Customer> CUSTOMER_MAPPER = new RowMapper<Customer>() {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer customer = new Customer();
			customer.setId(rs.getInt("id"));
			customer.setName(rs.getString("name"));
			customer.setPhone(rs.getString("phone"));
			return customer;
		}
	};

	private static final RowMapper<Order> ORDER_MAPPER = new RowMapper<Order>() {

		@Override
		public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setId(rs.getInt("id"));
			return order;
		}
	};

	private static final RowMapper<Product> PRODUCT_MAPPER = new RowMapper<Product>() {

		@Override
		public Product mapRow(ResultSet rs, int i) throws SQLException {
			Product product = new Product();
			product.setId(rs.getInt("id"));
			return product;
		}
	};

	@Override
	public Customer getCustomerById(int id) {
		Customer customer =  db.queryForObject("SELECT * FROM CUSTOMERS WHERE ID=?",
				CUSTOMER_MAPPER,
				id);
		List<Order> orders = db.query("SELECT * FROM ORDERS WHERE ID_CUST=?",
				ORDER_MAPPER,
				id);
		customer.setOrders(orders);
		
		return customer;
	}
	

	@Override
	public void updateCustomer(int idCust, String newName) {
			db.update("UPDATE CUSTOMERS SET NAME=? WHERE ID=?", newName, idCust);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return db.query("SELECT * FROM CUSTOMERS", CUSTOMER_MAPPER);
	}

	@Override
	public void addOrder(int idCust, int idOrder) {
		db.update("INSERT INTO ORDERS(ID, ID_CUST) VALUES (?,?)", idOrder, idCust);
	}

	@Override
	public void removeOrder(int idOrder){
		db.update("DELETE FROM orders WHERE id = ?", idOrder);
	}

	@Override
	public boolean verifyOrder(int idOrder) {
		int order = db.queryForObject("SELECT COUNT(*) FROM orders o where o.id = ?", Integer.class, idOrder);
		return order > 0;
	}
}

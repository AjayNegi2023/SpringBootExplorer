# Fetch Modes: Eager and Lazy Loading in Spring Boot

In Spring Boot, managing the loading strategy of related entities is crucial for optimizing application performance and ensuring data consistency. JPA (Java Persistence API) provides two primary fetch modes: **eager loading** and **lazy loading**. Understanding how and when to use these fetch modes can significantly impact the efficiency of your application.

## Overview of Fetch Modes

### Eager Loading

Eager loading is a strategy where related entities are loaded immediately along with their parent entity. This approach is beneficial when you know that you will need the related entities right away, as it avoids multiple queries and reduces the number of database hits.

### Lazy Loading

In contrast, lazy loading delays the loading of related entities until they are explicitly accessed. This can enhance performance by minimizing the initial number of queries and reducing the amount of data fetched from the database, especially when related entities are not always required.

## Fetch Modes in Action

To illustrate the differences between eager and lazy loading, let’s consider a real-world example from an e-commerce application involving `Order` and `Customer` entities. In this scenario, a customer can place multiple orders, establishing a one-to-many relationship between `Customer` and `Order`.

### Entity Definitions

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    // Getters and setters
}

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Getters and setters
}
```

## Eager Loading Example

If you want to retrieve a customer along with their orders simultaneously—because you know you will need both pieces of information—you can use eager loading by setting the fetch attribute to `FetchType.EAGER`.

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Order> orders;

    // Getters and setters
}
```

### Usage Example

```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {}

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerWithOrders(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
```

*Description*: When you call `getCustomerWithOrders`, the associated orders are loaded immediately along with the customer, resulting in a single query to the database.

## Lazy Loading Example

Now, consider a scenario where you often need to fetch customers without their orders. In this case, loading orders only when necessary can improve performance by reducing the initial data load.

```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders;

    // Getters and setters
}
```

### Usage Example

```java
public interface CustomerRepository extends JpaRepository<Customer, Long> {}

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Order> getCustomerOrders(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getOrders(); // Orders are fetched lazily here
    }
}
```

*Description*: In this case, the `getCustomer` method retrieves the customer without loading the orders. The orders are only fetched when `getCustomerOrders` is called, allowing for more efficient data handling.

## Real-Life Scenario

Imagine an e-commerce dashboard where administrators frequently view customer details without needing order information. However, when analyzing a specific customer’s purchasing history, they require detailed order data.

- **Eager Loading**: When an admin views a detailed report of a customer’s purchase history, eager loading can fetch the customer and their orders in a single query, providing all necessary information at once.

- **Lazy Loading**: When an admin views a list of customers, lazy loading avoids fetching order data initially, reducing the load of the initial query. If the admin clicks on a customer to view detailed order information, the orders are fetched at that moment.

## Conclusion

Choosing between eager and lazy loading depends on your application’s specific needs. Eager loading is suitable when you know you’ll need related entities immediately, while lazy loading is beneficial for reducing initial query load and improving performance when related data is not always required. By understanding and strategically applying these fetch modes, you can optimize your Spring Boot application’s performance and resource utilization.
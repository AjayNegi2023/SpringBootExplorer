### ✅ Question 1

In a ride-booking system, we have a `User` class that can be either a **Driver** or a **Passenger**, represented by an `enum` field.

```java
public class User {
    @Enumerated(EnumType.STRING)
    private Role role; // DRIVER or PASSENGER

    @OneToMany
    private List<Booking> bookings;
}

public enum Role {
    DRIVER,
    PASSENGER
}
```

The `Booking` class has two `ManyToOne` relationships referencing the `User` class:

```java
public class Booking {
    @ManyToOne
    private User driver;

    @ManyToOne
    private User passenger;
}
```

Now the question is:

> **How can the field `private List<Booking> bookings;` in the `User` class distinguish whether the user is a `driver` or a `passenger` in each booking? Since both relationships refer to the same `User` entity, how does JPA know which side to map?**

---

### ✅ Solution:

JPA does **not** automatically know whether the user is a **driver** or **passenger** unless you explicitly tell it via `mappedBy`.

The `bookings` list in `User` needs to be split into **two separate collections**:

#### ✅ Updated `User` class:

```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role; // DRIVER or PASSENGER

    @OneToMany(mappedBy = "driver")
    private List<Booking> drivingBookings; // bookings where user is the driver

    @OneToMany(mappedBy = "passenger")
    private List<Booking> passengerBookings; // bookings where user is the passenger
}
```

#### ✅ `Booking` class:

```java
@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private User passenger;
}
```

---

### ✅ Explanation:

* `mappedBy = "driver"` tells JPA to populate `drivingBookings` with all bookings where the user is referenced as the `driver`.
* `mappedBy = "passenger"` does the same for `passengerBookings`.
* This way, the role of the user in a booking is **explicitly distinguished**.

---

### 🔍 Bonus: When to Use Enum?

The `Role` enum can still be useful for general logic like UI control, access management, or business logic, **but should not be used to determine the relationship mapping**. The mappings should always rely on `mappedBy`.


# ✅ Question
Sure! Understanding **owning side** vs **inverse side** (also called **non-owning** side) is **crucial in JPA** for mapping relationships correctly.

---

### 🧠 Core Concept:

In JPA **bidirectional relationships**, **one side must be the "owning side"**, and the other side is the **inverse (non-owning) side**.

---

### ✅ Owning Side:
- **Controls the relationship** in the database.
- **Has the foreign key column**.
- **Defines `@JoinColumn`**.
- Any updates to the relationship should be made on the owning side.

### ✅ Inverse Side:
- **Refers back to the owning side** using `mappedBy`.
- **Does not have the foreign key**.
- Used to map the relationship in the object model, not the database.

---

### `mappedBy` is **always used on the **inverse (non-owning)** side of a bidirectional association**, and in JPA, the **inverse side is typically the `One` side** in a `OneToMany` ↔ `ManyToOne` relationship.

---

### 🔁 Understanding Ownership

| Annotation | Role | Can `mappedBy` be used here? | Explanation |
|-----------|------|------------------------------|-------------|
| `@ManyToOne` | **Owning side** | ❌ **No** | Always the owning side (holds the foreign key) |
| `@OneToMany` | **Inverse side** | ✅ **Yes** | Must use `mappedBy` to point to the owning side |
| `@OneToOne` | Either side | ✅ **Yes** | One side must use `mappedBy` |
| `@ManyToMany` | Either side | ✅ **Yes** | One side must use `mappedBy`, the other defines the `@JoinTable` |

---

### 🧠 So the rule is:
> 🔸 **Only the inverse side uses `mappedBy`**, and it does **not own** the foreign key.  
> 🔸 **The owning side defines the actual database relationship** — like `@JoinColumn`.

---

### ✅ Example: `User ↔ Booking`

```java
// Owning side (Many side) - contains @JoinColumn
@Entity
public class Booking {
    @ManyToOne
    @JoinColumn(name = "driver_id")  // foreign key column in booking table
    private User driver;
}
```

```java
// Inverse side (One side) - uses mappedBy
@Entity
public class User {
    @OneToMany(mappedBy = "driver")  // refers to the field 'driver' in Booking
    private List<Booking> drivingBookings;
}
```

---

### ❗ If you put `mappedBy` on the wrong side:

- JPA will ignore it (if it's on the owning side),
- or worse, it might throw an error or not map the relationship properly.

---

### ✅ Summary:

| Relationship      | Owning Side (`@JoinColumn`) | Inverse Side (`mappedBy`) |
|------------------|-----------------------------|----------------------------|
| `OneToMany`/`ManyToOne` | `ManyToOne`                   | `OneToMany(mappedBy)`       |
| `OneToOne`        | One with `@JoinColumn`      | Other with `mappedBy`      |
| `ManyToMany`      | One with `@JoinTable`       | Other with `mappedBy`      |

---


# Custom object (DTO) using HQL with @Query in a Spring Data JPA repository.

## User : Entity  UserDTO: that contains only id, name.

### 1. User Entity

```Java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;

    // Getters and setters
}
```

### 2. UserDTO

```java
public class UserDTO {

    private Long id;
    private String name;

    // Constructor (important for HQL)
    public UserDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters (or use Lombok)
}
```

### 3. Repository with HQL using @Query

```java
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.example.demo.dto.UserDTO(u.id, u.name) FROM User u WHERE u.role = :role")
    List<UserDTO> findUsersByRole(@Param("role") String role);
}
```

### 4. Usage in Service

```java
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAdmins() {
        return userRepository.findUsersByRole("ADMIN");
    }
}

```

#  `@Param` in Spring Data JPA

`@Param` is an annotation provided by **Spring Data JPA** that is used to bind method parameters to **named parameters** in your `@Query` annotations.

---

###  Example:

```java
@Query("SELECT u FROM User u WHERE u.role = :role")
List<User> findByUserRole(@Param("role") String role);
```

###  What's happening?

* `:role` → refers to a **named parameter** inside the HQL/JPQL query.
* `@Param("role")` → binds the method parameter (`String role`) to the named query parameter `:role`.

---

###  Why is it necessary?

Spring needs to know **which variable in your method** corresponds to **which placeholder in the query**.

Without `@Param`, Spring won’t know how to substitute `:role`.

---

###  You can also use multiple parameters:

```java
@Query("SELECT u FROM User u WHERE u.name = :name AND u.role = :role")
List<User> findByNameAndRole(@Param("name") String name, @Param("role") String role);
```

---

###  When can you skip `@Param`?

If you use **Spring Data JPA method naming conventions**, you don’t need to write `@Query` or `@Param`:

```java
List<User> findByRole(String role); // Works without @Query or @Param
```

## But for **custom HQL or native SQL queries**, `@Param` is required.

---

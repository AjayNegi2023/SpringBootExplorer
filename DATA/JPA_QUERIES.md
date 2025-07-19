# Querying Techniques in Spring Boot Applications Using JPA Repositories

In this article, we will explore various querying techniques in Spring Boot applications using JPA (Java Persistence API) repositories. We will cover JPA queries, raw SQL queries, Hibernate queries, and ORM methods, providing detailed examples and descriptions for each approach.

## JPA Queries Using Repositories

JPA repositories allow you to create custom queries using the `@Query` annotation and JPQL (Java Persistence Query Language). Below are several examples demonstrating how to use JPA queries.

### Example 1: Simple Select Query

This example demonstrates how to retrieve a user by their name using a simple JPQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> findByName(@Param("name") String name);
}
```
*Description*: The `findByName` method retrieves a user entity where the name matches the provided parameter.

### Example 2: Select with Multiple Conditions

This example shows how to retrieve a user based on both their name and email.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.name = :name AND u.email = :email")
    Optional<User> findByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
```
*Description*: The `findByNameAndEmail` method fetches a user entity that matches both the name and email criteria.

### Example 3: Update Query

This example illustrates how to update a user's email based on their name.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.name = :name")
    int updateUser Email(@Param("name") String name, @Param("email") String email);
}
```
*Description*: The `updateUser Email` method updates the email of a user whose name matches the provided parameter.

### Example 4: Delete Query

This example demonstrates how to delete a user based on their name.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query("DELETE FROM User u WHERE u.name = :name")
    int deleteUser ByName(@Param("name") String name);
}
```
*Description*: The `deleteUser ByName` method removes a user entity where the name matches the specified parameter.

### Example 5: Aggregation Query

This example shows how to count the number of users with a specific email.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
    Long countUsersByEmail(@Param("email") String email);
}
```
*Description*: The `countUsersByEmail` method returns the count of user entities that have the specified email.

## Raw SQL Queries Using Repositories

Spring Data JPA allows executing native SQL queries by annotating methods with `@Query` and setting the `nativeQuery` attribute to `true`. Below are examples of raw SQL queries.

### Example 1: Simple Select Query

This example retrieves a user by their name using a native SQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM users WHERE name = :name", nativeQuery = true)
    Optional<User> findByNameNative(@Param("name") String name);
}
```
*Description*: The `findByNameNative` method executes a native SQL query to fetch a user entity based on the name.

### Example 2: Select with Multiple Conditions

This example retrieves a user based on both their name and email using a native SQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT * FROM users WHERE name = :name AND email = :email", nativeQuery = true)
    Optional<User> findByNameAndEmailNative(@Param("name") String name, @Param("email") String email);
}
```
*Description*: The `findByNameAndEmailNative` method fetches a user entity that matches both the name and email criteria using a native SQL query.

### Example 3: Update Query

This example updates a user's email based on their name using a native SQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query(value = "UPDATE users SET email = :email WHERE name = :name", nativeQuery = true)
    int updateUser EmailNative(@Param("name") String name, @Param("email") String email);
}
```
*Description*: The `updateUser EmailNative` method updates the email of a user whose name matches the provided parameter using a native SQL query.

### Example 4: Delete Query

This example deletes a user based on their name using a native SQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query(value = "DELETE FROM users WHERE name = :name", nativeQuery = true)
    int deleteUser ByNameNative(@Param("name") String name);
}
```
*Description*: The `deleteUser ByNameNative` method removes a user entity where the name matches the specified parameter using a native SQL query.

### Example 5: Aggregation Query

This example counts the number of users with a specific email using a native SQL query.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
    Long countUsersByEmailNative(@Param("email") String email);
}
```
*Description*: The `countUsersByEmailNative` method returns the count of user entities that have the specified email using a native SQL query.

## Hibernate Queries Using Repositories

Hibernate queries can be executed using the Hibernate Query Language (HQL). These queries are similar to JPQL but are specific to Hibernate. Below are examples of using HQL in repositories.

### Example 1: Simple Select Query

This example retrieves a user by their name using HQL.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    default List<User> findByNameUsingHQL(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("FROM User u WHERE u.name = :name");
        query.setParameter("name", name);
        return query.getResultList();
    }
}
```
*Description*: The `findByNameUsingHQL` method uses HQL to fetch a user entity based on the name.

### Example 2: Select with Multiple Conditions

This example retrieves a user based on both their name and email using HQL.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    default List<User> findByNameAndEmailUsingHQL(String name, String email) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("FROM User u WHERE u.name = :name AND u.email = :email");
        query.setParameter("name", name);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
```
*Description*: The `findByNameAndEmailUsingHQL` method fetches a user entity that matches both the name and email criteria using HQL.

### Example 3: Update Query

This example updates a user's email based on their name using HQL.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional
    default int updateUser EmailUsingHQL(String name, String email) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("UPDATE User u SET u.email = :email WHERE u.name = :name");
        query.setParameter("name", name);
        query.setParameter("email", email);
        return query.executeUpdate();
    }
}
```
*Description*: The `updateUser EmailUsingHQL` method updates the email of a user whose name matches the provided parameter using HQL.

### Example 4: Delete Query

This example deletes a user based on their name using HQL.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    @Transactional
    default int deleteUser ByNameUsingHQL(String name) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("DELETE FROM User u WHERE u.name = :name");
        query.setParameter("name", name);
        return query.executeUpdate();
    }
}
```
*Description*: The `deleteUser ByNameUsingHQL` method removes a user entity where the name matches the specified parameter using HQL.

### Example 5: Aggregation Query

This example counts the number of users with a specific email using HQL.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    default Long countUsersByEmailUsingHQL(String email) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        return (Long) query.getSingleResult();
    }
}
```
*Description*: The `countUsersByEmailUsingHQL` method returns the count of user entities that have the specified email using HQL.

## ORM Methods Using Repositories

ORM (Object-Relational Mapping) methods allow you to perform CRUD operations using the repository interfaces provided by Spring Data JPA. Below are examples of common ORM methods.

### Example 1: Save Entity

This example demonstrates how to save a user entity.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    // No additional methods required for save
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser (User user) {
        return userRepository.save(user);
    }
}
```
*Description*: The `saveUser ` method in the `User Service` class saves a user entity to the database using the `save` method provided by `JpaRepository`.

### Example 2: Find Entity by ID

This example shows how to find a user entity by its ID.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    // No additional methods required for findById
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUser ById(UUID id) {
        return userRepository.findById(id);
    }
}
```
*Description*: The `findUser ById` method retrieves a user entity based on its ID using the `findById` method provided by `JpaRepository`.

### Example 3: Find All Entities

This example demonstrates how to retrieve all user entities.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    // No additional methods required for findAll
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
```
*Description*: The `findAllUsers` method retrieves all user entities from the database using the `findAll` method provided by `JpaRepository`.

### Example 4: Delete Entity by ID

This example shows how to delete a user entity by its ID.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    // No additional methods required for deleteById
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void deleteUser ById(UUID id) {
        userRepository.deleteById(id);
    }
}
```
*Description*: The `deleteUser ById` method removes a user entity from the database based on its ID using the `deleteById` method provided by `JpaRepository`.

### Example 5: Count All Entities

This example demonstrates how to count all user entities.

```java
public interface UserRepository extends JpaRepository<User, UUID> {
    // No additional methods required for count
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public long countAllUsers() {
        return userRepository.count();
    }
}
```
*Description*: The `countAllUsers` method returns the total count of user entities in the database using the `count` method provided by `JpaRepository`.

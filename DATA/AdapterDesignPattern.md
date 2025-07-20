
### **Adapter Design Pattern - Simplified**

The **Adapter Design Pattern** allows **two incompatible interfaces** to work together. It acts as a **bridge** between two classes or systems that can’t directly communicate due to different structures or formats.

#### ✅ Why Use Adapter Pattern?

* To **connect mismatched systems** without changing their core logic.
* To **integrate third-party services** without affecting your main codebase.
* To make the system **easier to maintain and extend** in the future.

---

### **Real-Life Example: PhonePe and Banks**

Let’s say **PhonePe** initially integrates with **ICICI Bank**. Their business logic is written based on ICICI’s interface.

Now, they want to integrate **SBI Bank**. But SBI might have a different interface or structure than ICICI.

Instead of changing the entire **business logic** inside PhonePe, they can create an **Adapter Layer** – an intermediate layer that:

* Converts SBI's interface into a format PhonePe understands.
* Handles communication between PhonePe and different banks.

This way, the **core business logic stays the same**, and only the adapter changes for each new bank integration.

---

### **Visual Summary**

* **Before Adapter:** PhonePe talks directly to each bank → complex and hard to manage.
* **After Adapter:** PhonePe → Adapter Layer → Any bank (ICICI, SBI, Axis, etc.)

# Example

Here's a **Java example** of the **Adapter Design Pattern** based on the **PhonePe and Banks** scenario.

---

### 🧠 Scenario

* **PhonePe** wants to send money using a standard interface.
* **ICICIBank** and **SBIBank** have **different methods** to process payments.
* We will use an **adapter** to make them work with a common interface.

---

### ✅ Step-by-step Java Implementation

#### 1. **Target Interface** — What PhonePe expects

```java
// Target interface
public interface BankAPI {
    void sendMoney(String accountNumber, double amount);
}
```

---

#### 2. **Existing Banks with Different Interfaces (Incompatible APIs)**

```java
// Adaptee 1 - ICICI Bank
public class ICICIBank {
    public void doTransaction(String accNo, double amt) {
        System.out.println("ICICI Transaction: " + amt + " to " + accNo);
    }
}

// Adaptee 2 - SBI Bank
public class SBIBank {
    public void makePayment(String account, double value) {
        System.out.println("SBI Payment: " + value + " to " + account);
    }
}
```

---

#### 3. **Adapter Classes** — Adapting ICICI and SBI to BankAPI

```java
// Adapter for ICICI Bank
public class ICICIBankAdapter implements BankAPI {
    private ICICIBank icici = new ICICIBank();

    @Override
    public void sendMoney(String accountNumber, double amount) {
        icici.doTransaction(accountNumber, amount);
    }
}

// Adapter for SBI Bank
public class SBIBankAdapter implements BankAPI {
    private SBIBank sbi = new SBIBank();

    @Override
    public void sendMoney(String accountNumber, double amount) {
        sbi.makePayment(accountNumber, amount);
    }
}
```

---

#### 4. **Client (PhonePe) Using Adapter**

```java
public class PhonePe {
    private BankAPI bankAPI;

    public PhonePe(BankAPI bankAPI) {
        this.bankAPI = bankAPI;
    }

    public void transfer(String acc, double amount) {
        bankAPI.sendMoney(acc, amount);
    }

    public static void main(String[] args) {
        // Using ICICI Bank
        BankAPI iciciAdapter = new ICICIBankAdapter();
        PhonePe phonePeICICI = new PhonePe(iciciAdapter);
        phonePeICICI.transfer("ICICI123", 5000);

        // Using SBI Bank
        BankAPI sbiAdapter = new SBIBankAdapter();
        PhonePe phonePeSBI = new PhonePe(sbiAdapter);
        phonePeSBI.transfer("SBI456", 7000);
    }
}
```

---

### ✅ Output:

```
ICICI Transaction: 5000.0 to ICICI123
SBI Payment: 7000.0 to SBI456
```

---

### 💡 Summary:

* `PhonePe` only knows `BankAPI` (the **target interface**).
* It works with **any bank** via its respective **adapter**.
* If a new bank is added, only a new adapter is needed — no changes in the core logic.


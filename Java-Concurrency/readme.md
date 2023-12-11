# Java Concurrency

Let’s first consider the following scenario,
Any bank named ABC for example, has a total bank balance. This bank has a lot of users who are continuously depositing and withdrawing money. Each deposit/withdrawal takes a little time for example 2 seconds. If we want to execute these operations sequentially, then in peak time user will have to wait for a longer time to complete his transaction. If 10 people are trying to do any transaction together, then the last user has to wait for 18 seconds, what will happen if it is 1 million users? So we have to do it concurrently using **multi-threading**. This way we can efficiently provide services to all users without hassle.

Well, multi-threading can fix this type of issue but generate another issue while using a common resource like bank balance. For example, Bank ABC has a total balance of BDT 1000, and 1 user wants to deposit BDT 100 and another user wants to withdraw BDT 50. After all this successful operation, the bank balance should be BDT 1050. Now consider the case when two operations are done concurrently, which means that each will have a total balance of BDT 1000, when a depositor deposits BDT 100 he will be able to see total bank balance is BDT 1100, and again when a user withdraw BDT 50, he will be able to see bank balance is BDT 950. So the final bank balance will be either BDT 1100 or BDT 950 instead of 1050. This problem is called **Race Condition**.

So how can we design our problem so that no race condition will even occur? Well it’s a pretty straightforward solution, when we use any common resources in multi-threading, we won’t allow multiple threads to access it. This means that only one thread can operate at a time, when one thread is finished its operation then another thread will operate. We can do it using a **synchronized block**. Each synchronized block can be able to accessed by only one thread. Using synchronized blocks may prevent race conditions, but it can reduce performance and throughput as a large number of threads have to wait to access this block of instructions instead of accessing a critical section. Java concurrency provides another class named **Atomic class such as AtomicInteger, AtomicLongs, DoubleAdder**, and others to avoid race conditions. But it is limited to simple operations like updating a variable or counting numbers etc. They aren’t well-suited for complex operations that involve multiple operations or steps. If we are looking for something well-suited to do complex operations, **the Locks are the best option that Java concurrency provides**.

These are some common ways to fix problems provided above. Java concurrency provides a few other cool functionalities like **CounDownLatch, CyclicBarrier, Semaphore, BlockingQueue, DelayQueue, Locks, Phaser**, etc. Briefly explaining them below,

### CountDownLatch: 
CountDownLatch can forcibly block a thread until others have completed their jobs. When we initialize a CountDownLatch we will have to provide the number of dependant threads. CountDownLatch provides a `countdown()` method that indicates that this thread has finished its execution, so the latch count is reduced by one. Once the latch count becomes zero, the waiting threads are released. If we use the `await()` method of the **CountDownLatch** class, the program will wait for the latch count to become zero to execute the next statement.

### CyclicBarrier: 

It is the same as the CountDownLatch, but the main difference is **re-usability**. By using the `await()` method, CyclicBarrier makes all threads wait for each other at a predefined barrier. Once a specified number of threads reach the barrier by calling the `await()` method, the barrier is tripped and all the threads are released to cyclic. CyclicBarrier provides a `reset()` method to reset it when the barrier is tripped and reuse it.

### Semaphore: 

The Semaphore **checks permits for threads to access critical sections**. When initializing the semaphore, we will have to provide the number of permits. When a thread wants to access a critical section, the Semaphore provides a method `tryAcquire()` to check if access to a critical section is permitted. If a permit isn’t available then the thread can’t able to jump into the critical section and again if a permit is available then the thread can access that by reducing the number of permits by one. When any threads complete its execution of the critical section, the Semaphore provides a `release()` method to release permits and the number of permits is increased by one. To find the number of available permits, it provides the `availablePermits()` method as well.

### BlockingQueue: 
In asynchronous programming, **BlockingQueue is the most used data structure in producer-consumer patterns**. It can be one of two types **bounded(Maximum capacity is defined)** and **unbounded(Can be increased indefinitely)** blocking queues. It provides a few powerful methods for adding and removing items to it. 
* The `add()` method returns true if insertion is possible, otherwise throws an exception. 
* The `offer()` method returns true if insertion is successful otherwise returns false. 
* The `put()` method inserts elements into the queue and waits for a free slot if needed. 
* The `offer(timeout, time-unit)` method is responsible for inserting an element into the queue and waiting for a specific time if needed. 

For retrieving elements from the queue,
* The `take()` method which retrieves the head element from the queue if found, otherwise, it blocks and waits for the head element to be available. 
* The `pull(long timeout, TimeUnit unit)` method retrieves the head element from the queue if found, otherwise, it blocks and waits for a specified time. It returns null if no head element is found after timeout.

### DelayQueue: 

This is also a blocking queue used in producer-consumer programs. The key feature of this queue is that **we can set a delay time for each element of that queue to wait to be retrieved from the queue** by the consumer. That means the consumer will be able to retrieve an element from that queue only if the delay time for that element has expired. Each element of that queue needs to implement the **Delayed** interface. The Delayed interface has two methods to override, `getDelay(Timeunit unit)` and `compareTo(Delayed o)`. 
* The `getDelay(Timeunit unit)` method **returns the delay time left** in the time unit for this element. If it returns -1 meaning that the delay time is expired and the consumer can retrieve this element from the queue. 
* The `compareTo(Delayed o)` method **sorts the elements of that queue based on their delay time**. The element with the shortest delay time will be kept at the head of the queue.

### Locks: 

Locks are the most useful that the Java concurrency package provides. **We can avoid race conditions and implement thread safety using locks and those are well-suited for complex operations as well**. Java concurrency package provides **Lock** and **ReadWriteLock** interfaces for locking.

The **Lock** interface provides a few key methods to handle concurrency.
* `void lock()`: Blocking method to acquire the lock.
* `void lockInterruptibly()`: Acquire lock if possible, else allow the blocked thread to be interrupted by throwing an exception.
* `boolean tryLock()`: Non-blocking method to acquire lock immediately, returns true if locking is possible.
* `boolean tryLock(long timeout, TimeUnit unit)`: Same as tryLock() but it waits for a timeout to acquire lock.
* `void unlock()`: Release the lock by thread.

The **ReadWriteLock** interface provides a few methods as well,
* Lock readLock(): Returns lock to read critical resources.
* Lock writeLock(): Returns lock to write only.
* 
**Multiple threads can acquire read locks on the same critical section if there isn’t any write lock. If there is a write lock, no other lock can be possible on the same critical section.**
There are a few implementations of these interfaces like **ReentrantLock, ReentrantReadWriteLock, and StampedLock**.

### Phaser:
We saw how CountDownLatch and CyclicBarrier work, both need to be initialized with a predefined number of threads. But in Phaser, **this can be done dynamically meaning that we don’t need to know the total number of threads in advance**. Each thread can register itself to a Phaser dynamically. The **Phaser** class provide a method `register()` to dynamically register for threads. It provides another method `arriveAndAwaitAdvance()` for each registered thread to reach this barrier and after completing their job, threads can deregister from the phaser using the `arriveAndDeregister()` method provided by the Phaser.

Reference: [Baeldung](https://www.baeldung.com/java-util-concurrent)

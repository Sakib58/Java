# Java Threading

Have a look few real-world scenarios,
1. When we work with web servers, we know that there are many incoming requests to the web servers. How can a web server manage those requests at a time without unwanted responses?
2. While downloading a file from a website, how can we interact with the application to browse movies?
3. In video streaming websites, how do video and audio playback stream together?
4. How to handle multiple database connections simultaneously?

In a single word, **Threads** can handle all of those scenarios. For example, 
web servers can handle each request in a separate thread, downloading files 
in a separate thread in the background, audio and video playback can be 
performed in separate threads, and each database connection is handled by 
a thread. Well, then **what is thread?**

**A thread is a sequence of instructions that can be scheduled and executed by the CPU independently of the parent process.** 

Each thread has a lifecycle to utilize resources properly. In Java, the life 
cycle of a thread consists of five states: **New**, **Runnable**, **Running**, **Blocked**, and **Terminated**.

* **New:** Whenever a thread is created, it is always in a new state.

* **Runnable:** When a thread is ready to run, it is in a runnable state.

* **Running:** When a thread is assigned to a CPU for execution, it will go to a running state from a runnable state.

* **Blocked or Waiting:** When a thread is waiting for some time, it will be either in a blocked or waiting state. 

* **Terminated:** When a thread completes its executions or is stopped abruptly due to some errors, it will be in a terminated state.

**How can we implement threads in Java?**

In Java, we can implement threads in one of **two ways**, extending the `Thread` 
class and implementing the `Runnable` interface (or `Callable` interface - 
an improved version of Runnable interface). There is no big difference 
between these two implementations. When we extend the Thread class, we can’t 
extend any other classes even if we need to, on the other hand, when we 
implement the Runnable we will have space to extend or implement other 
classes.

In a real-world scenario, we will have lots of threads to manage for a single 
application. To implement **safely starting, closing down, submitting, executing and blocking huge numbers of threads** isn’t feasible. To make this simpler, Java provides frameworks named `ExecutorService` and `ForkJoinPool` to manage threads.

### **Executor Services:**
Along with managing threads, ExecutorService provides a few other advantages. **It provides a pool of threads and an API for assigning tasks to each thread**. 
It abstracts away many complexities associated with lower-level abstractions. 
It also provides lifecycle support and hooks for adding **statistics gathering**, 
**application management** and **monitoring**. 

There are a few types of executor services, I’m briefly explaining a few of them with their use cases below,

* **SingleThreadExecutor:** When we want to execute **one task at a time** in the background, we can use this kind of thread executor. It creates a **single thread** to execute tasks **sequentially**.

* **FixedThreadPool:** This type of executor creates a **fixed number of threads** to execute tasks **concurrently**. We will use this kind of executor when we have a **fixed number of tasks to run concurrently**.

* **CashedThreadPool:** It creates **as many threads as needed** to execute tasks **concurrently**. It is useful when we have a **large number of short-lived tasks to run concurrently**.

* **ScheduledThreadPool:** This type of executor service creates **a fixed number of threads to execute tasks at a scheduled time**. We will use it when we want to **execute tasks at a specific time or regular interval**.

Executor service provides a few methods like `execute()`, `submit()`, `invokeAny()`, and `invokeAll()` to assign tasks to execute services, we will use one of them depending on our requirements. To understand this, we need to understand the `Future` type of Java threading. **The Future type is used to get the result of the task executions or to check the task’s status**. The Future type provides a special blocking method `get()`**, which returns an actual result of a Callable task but null for Runnable tasks**. It is called the blocking method because calling this method to a running task can block the execution until the task properly executes and the result is available. We can check task status using the Future by calling `isDone()` method, the Future also provides `cancel()` and `isCancelled()` methods to cancel execution and to check the cancellation.

The `execute()` method is void and **returns nothing**.

The `submit()` method **returns Future or null**, which we can use to monitor the status of the tasks.

The `invokeAny()` assigns a collection of tasks to the executor services, causing each to execute and **return the result of any successful task** (If any).

The `invokeAll()` assigns a collection of tasks to the executor services, causing each to execute and **return the result of all tasks in the form of a list of Future types**.

Okay, now it’s time to **shut down** our executor services. To do that, executor services provide two important methods called `shutdown()` and `shutDownNow()`. The `shutdown()` method **stops accepting new tasks** and waits to complete the execution of the running threads whereas the `shutDownNow()` method tries to **stop immediately** but doesn’t guarantee that all the threads will stop at the same time.

### **Fork/Join:**
Fork/Join Pool is a specialized implementation of Executor Service **to solve recursive problems using divide and conquer** efficiently. The architecture behind this pool is a **Work-stealing algorithm**. The work-stealing algorithm means free workers compete to steal work from busy workers. In this case, each worker thread has its **double-ended queue**, and it generally takes work from it, but when a worker thread is free, then it will steal work from a busy thread or global queue.

ForkJoinPool provides a `commonPool()` method which is a **common reference** for every ForkJoinTask. But we can make a common reference using a **static ForkJoinPool variable**, and if we want to use two core processors (two levels of parallelism) all we need is to send 2 as a parameter to ForkJoinPool.

Unlike Runnable, Callable or Thread task, `ForkJoinTask<V>` is the **base task** of ForkJoinPool. ForkJoinTask can be created by extending either the `RecursiveAction` class or `RecursiveTask<V>` class. The key difference between these two is that the **RecursiveAction is a void class**, but the **RecursiveTask<V> returns a value**. Both have the same abstract method called `compute()` which contains business logic. We can divide a single task into multiple tasks, and using `invokeAll()` method, add all subtasks to the main ForkJoinPool. We can also use the `join()` method for each sub-task to trigger execution.

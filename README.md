
### Connection Pooling Comparison (MySQL)

## Setup
- **MySQL 8.0** in Docker
- Query: `SELECT SLEEP(2)`
- Threads: 5

## Results

### SingleConnectionDataSource
All threads share a single connection → sequential execution.

```bash
Using SingleConnectionDataSource 
Worker-0 finished sleep.
Worker-4 finished sleep.
Worker-3 finished sleep.
Worker-2 finished sleep.
Worker-1 finished sleep.
Total time: 10069 ms
```

**Total time: ~10 seconds**

---

### HikariCP (Pool size = 5)
Each thread gets its own connection from the pool → true parallelism.

```bash
Using HikariCP Connection Pool 
[main] INFO com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
[main] INFO com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@11fc564b
[main] INFO com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
Worker-0 finished sleep.
Worker-2 finished sleep.
Worker-1 finished sleep.
Worker-3 finished sleep.
Worker-4 finished sleep.
Total time: 2163 ms
```

**Total time: ~2 seconds**

---

## Why Connection Pooling?

### Advantages
- Reduces connection overhead
- Enables concurrent DB access
- Manages resources efficiently
- Reduces latency in high-load systems

### Disadvantages
- Slight memory overhead
- Misconfigurations can exhaust DB
- More moving parts to manage

---

## Conclusion
**HikariCP** significantly improves performance in multi-threaded workloads by enabling parallel DB access.

1. [55 Marks]
Wikipedia defines a connection pool as follows:

“In software engineering, a connection pool is a cache of database connections maintained so that the connections can be reused when future requests to the database are required. Connection pools are used to enhance the performance of executing commands ona database.(Opening and maintaining a database connection for each user, especially requests made to a dynamic database-driven website application, is costly and wastes resources) In connection pooling, after a connection is created, it is placed in the pool and it is used again so that a new connection does not have to be established. If all the connections are being used, a new connection is made and is added to the pool. Connection pooling also cuts down on the amount of time a user must wait to establish a connection to the database.

Applications:

Web-based and enterprise applications use an application server to handle connection pooling. Dynamic web pages without connection pooling open connections to database services as required and close them when the page is done servicing a particular request. Pages that use connection pooling, on the other hand, (maintain open connections ina poo). When the page requires access to the database, it simply uses an existing connection from the pool, and establishes a new connection only if no pooled connections are available. This reduces the overhead associated with connecting to the database to service individual requests.

Local applications that need frequent access to databases can also benefit from connection pooling. Open connections can be maintained in local applications that don't need to service separate remote requests like application servers, but implementations of connection pooling can become complicated) A number of available libraries implement connection pooling and related SQL query pooling, simplifying the implementation of connection pools in database-intensive applications.

Administrators can configure connection pools with restrictions on the numbers of minimum connections, maximum connections and idle connections to optimize the performance of pooling in specific problem contexts and in specific environments."

Design and implement a connection Pool. The connection pool must be generic and support connections to any type of database, List all the classes and write working code.

Describe the design patterns and design principles that you have used with reasons for using them.

2. Apply "Code Reading" techniques to following piece of code, [25 Marks]

package java.util;

import java.util.function. Consumer;
import java.util. function. Function;
import java.util. function. Predicate;
import java.util. function. supplier;

public final class Optional<T> {
	private static final Optional<?> EMPTY = new
Optional<>();
	private final T value;
	private Optional() {
		this.value = null;
	}

	Public Static<T> Optional<T> empty() (
		Optional<T> t = (Optional<T>) EMPTY;
		return t;
	}

	private Optional (T value) {
		4 this.value = value;
	}

	public static <1> optional<r> of(T value) (
		return new Optional<> (value) ;
	}

	public static <T> Optional<?> ofNullable(T value) {
		return value == null ? empty() : of (value);
	}
	public T get() {
		if (value null) {
			throw new NoSuchElementException("No value present"); *
		} (|
		return value;
	}
	public boolean isPresent() {
		return value != null;
	}
}

3. What are your learnings from the CV in Excel guest lectures? [10 Marks]

4. What is your favourite programming paradigm? Why? [10 Marks]



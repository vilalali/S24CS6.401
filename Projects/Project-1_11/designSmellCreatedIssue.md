```Intentionality Issue```

https://github.com/serc-courses/se-project-1--_11/issues/10

Intentionality Issue 1
# Add a private constructor to hide the implicit public one.
### Where is the Issue?
```bash
books-core/src/main/java/com/sismics/books/core/constant/Constants.java
```
### Why is this an Issue?
Whenever there are portions of code that are duplicated and do not depend on the state of their container class, they can be centralized inside a "utility class". A utility class is a class that only has static members, hence it should not be instantiated.

####  Exceptions:
When a class contains *public static void main(String[] args)* method it is not considered as a utility class and will be ignored by this rule.

### How can fix it?
To prevent the class from being instantiated, you should define a non-public constructor. This will prevent the compiler from implicitly generating a *public parameterless constructor*.

#Noncompliant code example
```bash
class StringUtils { // Noncompliant
  public static String concatenate(String s1, String s2) {
    return s1 + s2;
  }
}
```

#Compliant solution
```bash
class StringUtils { // Compliant
  private StringUtils() {
    throw new IllegalStateException("Utility class");
  }
  public static String concatenate(String s1, String s2) {
    return s1 + s2;
  }
}
```
![intentionalityIssue1](https://github.com/serc-courses/se-project-1--_11/assets/46487934/e69b6c19-ec3f-49d3-b639-76fbbb0531ed)

```bash
GPT Chat: https://chat.openai.com/share/ed72ac19-2e73-494a-8023-2fa0a924fed2
Phind Screen Shot:
Sonar Screen Shot:
```

---
https://github.com/serc-courses/se-project-1--_11/issues/11

Intentionality Issue2
# Use try-with-resources or close this "CSVReader" in a "finally" clause.
### Where is the Issue?
```
books-core/src/main/java/com/sismics/books/core/listener/async/BookImportAsyncListener.java
```
### Why is this an Issue?
Connections, streams, files, and other classes that implement the Closeable interface or its super-interface, AutoCloseable, needs to be closed after use. Further, that close call must be made in a finally block otherwise an exception could keep the call from being made. Preferably, when class implements AutoCloseable, resource should be created using "try-with-resources" pattern and will be closed automatically.

Failure to properly close resources will result in a resource leak which could bring first the application and then perhaps the box the application is on to their knees.
#### Noncompliant code example
```bash
private void readTheFile() throws IOException {
  Path path = Paths.get(this.fileName);
  BufferedReader reader = Files.newBufferedReader(path, this.charset);
  // ...
  reader.close();  // Noncompliant
  // ...
  Files.lines("input.txt").forEach(System.out::println); // Noncompliant: The stream needs to be closed
}

private void doSomething() {
  OutputStream stream = null;
  try {
    for (String property : propertyList) {
      stream = new FileOutputStream("myfile.txt");  // Noncompliant
      // ...
    }
  } catch (Exception e) {
    // ...
  } finally {
    stream.close();  // Multiple streams were opened. Only the last is closed.
  }
}
```

#### Compliant solution
```bash
private void readTheFile(String fileName) throws IOException {
    Path path = Paths.get(fileName);
    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      reader.readLine();
      // ...
    }
    // ..
    try (Stream<String> input = Files.lines("input.txt"))  {
      input.forEach(System.out::println);
    }
}

private void doSomething() {
  OutputStream stream = null;
  try {
    stream = new FileOutputStream("myfile.txt");
    for (String property : propertyList) {
      // ...
    }
  } catch (Exception e) {
    // ...
  } finally {
    stream.close();
  }
}
```
#### Exceptions
```bash
Instances of the following classes are ignored by this rule because close has no effect:

    java.io.ByteArrayOutputStream
    java.io.ByteArrayInputStream
    java.io.CharArrayReader
    java.io.CharArrayWriter
    java.io.StringReader
    java.io.StringWriter

Java 7 introduced the try-with-resources statement, which implicitly closes Closeables. All resources opened in a try-with-resources statement are ignored by this rule.

try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
  //...
}
catch ( ... ) {
  //...
}
```
### How can fix it?
```bash
```
----

```Consistency Issue```

https://github.com/serc-courses/se-project-1--_11/issues/12

Consistency Issue 1
# Change this "try" to a try-with-resources.
## Where is the Issue?
books-core/src/main/java/com/sismics/util/HttpUtil.java
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/bc46a2ca-c6e7-430a-8879-77fb2fbad4c4)

## Why is this an Issue?
Many resources in Java need be closed after they have been used. If they are not, the garbage collector cannot reclaim the resources' memory, and they are still considered to be in use by the operating system. Such resources are considered to be leaked, which can lead to performance issues.

Java 7 introduced the try-with-resources statement, which guarantees that the resource in question will be closed.
```bash
try (InputStream input = Files.newInputStream(path)) {
  // "input" will be closed after the execution of this block
}
```
This syntax is safer than the traditional method using try, catch, and finally and hence should be preferred.
This rule raises an issue if a closeable resources is not opened using a try-with-resources statement.
This rule is automatically disabled when the project’s sonar.java.source is lower than 7 as the close-with-resources statement was unavailable prior to Java 7.

## How can fix it?
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/8248a8a4-1af5-4f11-965e-0f46ad3d3d8b)
---

https://github.com/serc-courses/se-project-1--_11/issues/13

Consistency Issue 2
# Use "java.nio.file.Files#delete" here for better messages on error conditions.
## Where is the Issue?
```bash
books-web/src/main/java/com/sismics/books/rest/resource/BookResource.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/d5515a47-ea59-4191-9a45-b4c2c5aac3c8)

## Why is this an Issue?
When java.io.File delete fails, this boolean method simply returns false with no indication of the cause. On the other hand, when java.nio.file.Files delete fails, this void method returns one of a series of exception types to better indicate the cause of the failure. And since more information is generally better in a debugging situation, java.nio.file.Files delete is the preferred option.

## How can fix it?
```bash
# Noncompliant code example
public void cleanUp(Path path) {
  File file = new File(path);
  if (!file.delete()) {  // Noncompliant
    //...
  }
}
```

```bash
# Compliant solution
public void cleanUp(Path path) throws NoSuchFileException, DirectoryNotEmptyException, IOException {
  Files.delete(path);
}
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/70432730-ed24-4f5b-91d3-e9514fc1def7)

---
https://github.com/serc-courses/se-project-1--_11/issues/17

IntentionalityIssue4|Maintainability
# Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
## Where is the Issue?
```bash
books-core/src/main/java/com/sismics/books/core/service/FacebookService.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/071cc6ca-8058-4690-8e8c-a93f1a42eebd)

## Why is this an Issue?
An empty method is generally considered bad practice and can lead to confusion, readability, and maintenance issues. Empty methods bring no functionality and are misleading to others as they might think the method implementation fulfills a specific and identified requirement.
### There are several reasons for a method not to have a body:
```bash
    It is an unintentional omission, and should be fixed to prevent an unexpected behavior in production.
    It is not yet, or never will be, supported. In this case an exception should be thrown.
    The method is an intentionally-blank override. In this case a nested comment should explain the reason for the blank override.
```
#Exceptions
This does not raise an issue in the following cases:
```bash
    Non-public default (no-argument) constructors
    Public default (no-argument) constructors when there are other constructors in the class
    Empty methods in abstract classes
    Methods annotated with @org.aspectj.lang.annotation.Pointcut()
```
```bash
public abstract class Animal {
  void speak() {  // default implementation ignored
  }
}
```
## How can fix it?
- Noncompliant code example
```bash
public void shouldNotBeEmpty() {  // Noncompliant - method is empty
}
public void notImplemented() {  // Noncompliant - method is empty
}
@Override
public void emptyOnPurpose() {  // Noncompliant - method is empty
}
```
- Compliant solution
```bash
public void doSomething() {
  doSomething();
}
public void notImplemented() {
  throw new UnsupportedOperationException("notImplemented() cannot be performed because ...");
}
@Override
public void emptyOnPurpose() {
  // comment explaining why the method is empty
}
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/03bbe98c-2c0d-4667-ab3b-437596de3642)

---

---
intentionalityIssue5|Maintainability
# Change the visibility of this constructor to "protected".
## Where is the Issue?
books-core/src/main/java/com/sismics/util/jpa/DbOpenHelper.java

![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/bccb7ea5-1c6f-47bc-9e23-10307a37d36e)

## Why is this an Issue?
Abstract classes should not have public constructors. Constructors of abstract classes can only be called in constructors of their subclasses. So there is no point in making them public. The protected modifier should be enough.

- Noncompliant code example
```bash
public abstract class AbstractClass1 {
    public AbstractClass1 () { // Noncompliant, has public modifier
        // do something here
    }
}
```
- Compliant solution
```bash
public abstract class AbstractClass2 {
    protected AbstractClass2 () {
        // do something here
    }
}
```
---

Intentionality Issue 6 | Not clear |Maintainability

# This file having multiple issue, fix the all of them:
1. Rename "principal" which hides the field declared at line 37.
2. Remove this unnecessary null check; "instanceof" returns false for nulls.
3. Remove the declaration of thrown exception 'org.codehaus.jettison.json.JSONException', as it cannot be thrown from method's body.
4. Remove this unnecessary null check; "instanceof" returns false for nulls.

## Where is the Issue?
```bash
books-web/src/main/java/com/sismics/books/rest/resource/BaseResource.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/ca2b2397-65e9-45e4-a2d1-6743bd73e057)


## Why is this an Issue?
Shadowing occurs when a local variable has the same name as a variable or a field in an outer scope.
This can lead to three main problems:

- Confusion: The same name can refer to different variables in different parts of the scope, making the code hard to read and understand.
- Unintended Behavior: You might accidentally use the wrong variable, leading to hard-to-detect bugs.
- Maintenance Issues: If the inner variable is removed or renamed, the code’s behavior might change unexpectedly because the outer variable is now being used.

To avoid these problems, rename the shadowing, shadowed, or both identifiers to accurately represent their purpose with unique and meaningful names.

This rule focuses on variables in methods that shadow a field.

## How can fix it?
### Noncompliant code example
```bash
class Foo {
  public int myField;

  public void doSomething() {
    int myField = 0; // Noncompliant
    // ...
  }
}
```
### Documentation

CERT - [DCL51-J. Do not shadow or obscure identifiers in subscopes](https://wiki.sei.cmu.edu/confluence/display/java/DCL51-J.+Do+not+shadow+or+obscure+identifiers+in+subscopes)

#### Related rules

[S2176](http://localhost:9000/coding_rules#rule_key=java%3AS2176) - Class names should not shadow interfaces or superclasses
[S2387](http://localhost:9000/coding_rules#rule_key=java%3AS2387) - Child class fields should not shadow parent class fields
[S4977](http://localhost:9000/coding_rules#rule_key=java%3AS4977) - Type parameters should not shadow other type parameters




```Adaptability Issue```


https://github.com/serc-courses/se-project-1--_11/issues/16

### Adaptability Issue 3
# Refactor this method to reduce its Cognitive Complexity from 19 to the 15 allowed.
## Where is the Issue?
```bash
books-core/src/main/java/com/sismics/books/core/util/TransactionUtil.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/c516b305-782b-4614-906b-725012f73a26)

## Why is this an Issue?
Cognitive Complexity is a measure of how hard the control flow of a method is to understand. Methods with high Cognitive Complexity will be difficult to maintain.
## Exceptions:
equals and hashCode methods are ignored because they might be automatically generated and might end up being difficult to understand, especially in the presence of many fields.
## How can fix it?
Resources
Documentation
    [Cognitive Complexity](https://www.sonarsource.com/docs/CognitiveComplexity.pdf)
Articles & blog posts
    [5 Clean Code Tips for Reducing Cognitive Complexity](https://www.sonarsource.com/blog/5-clean-code-tips-for-reducing-cognitive-complexity/)

---

https://github.com/serc-courses/se-project-1--_11/issues/14

Adaptability Issue 4
# Refactor this method to reduce its Cognitive Complexity from 52 to the 15 allowed.
## Where is the Issue?
books-core/src/main/java/com/sismics/books/core/listener/async/BookImportAsyncListener.java
----
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/d5a61213-e769-4900-a7b1-6093008594ee)

## Why is this an Issue?
Cognitive Complexity is a measure of how hard the control flow of a method is to understand. Methods with high Cognitive Complexity will be difficult to maintain.
## Exceptions:
equals and hashCode methods are ignored because they might be automatically generated and might end up being difficult to understand, especially in the presence of many fields.
## How can fix it?
Resources
Documentation
    [Cognitive Complexity](https://www.sonarsource.com/docs/CognitiveComplexity.pdf)
Articles & blog posts
    [5 Clean Code Tips for Reducing Cognitive Complexity](https://www.sonarsource.com/blog/5-clean-code-tips-for-reducing-cognitive-complexity/)

---
https://github.com/serc-courses/se-project-1--_11/issues/15
Adaptability Issue 5
## A "Brain Method" was detected. Refactor it to reduce at least one of the following metrics: LOC from 77 to 64, Complexity from 19 to 14, Nesting Level from 3 to 2, Number of Variables from 24 to 6.
## Where is the Issue?
```bash
books-web/src/main/java/com/sismics/books/rest/resource/BookResource.java
```
---
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/7583211e-fd7d-4a34-abb9-38dc957c7776)

## Why is this an Issue?
```
This issue is raised when Sonar considers that a method is a 'Brain Method'.
A Brain Method is a method that tends to centralize its owner’s class logic and generally performs too many operations. This can include checking too many conditions, using lots of variables, and ultimately making it difficult to understand, maintain and reuse.
It is characterized by high LOC number, high cyclomatic and cognitive complexity, and a large number of variables being used.
```
## What is the potential impact?
```
Brain Methods are often hard to cover with tests, because of their deep nesting, and they are error-prone, because of the many local variables they usually introduce. Such methods will be very hard to read and understand for anyone outside who created them, and therefore hard to maintain and fix if bugs get spotted.
They also enable code duplication since the method itself can hardly be reused anywhere else.
```
## How can fix it?
The common approach is to identify fragments of the method’s code that deal with a specific responsibility and extract them to a new method. This will make each method more readable, easy to understand and maintain, easier to test, and more prone to be reused.
In this paper, the authors describe a systematic procedure to refactor this type of code smell: ["Assessing the Refactoring of Brain Methods"](https://dl.acm.org/doi/10.1145/3191314).
- Noncompliant code example
```bash
void farmDailyRoutine() {
    Crops southEastCrops = getCrops(1, -1);
    Crops eastCrops = getCrops(1, 0);
    WaterContainer waterContainer = new WaterContainer();
    List<Bottle> bottles = new ArrayList<>();
    for(int i = 0; i < 10; i++) {
        var bottle = new Bottle();
        bottle.addWater(10L);
        bottle.putCap();
        bottle.shake(2);
        bottles.add(bottle);
    }
    waterContainer.store(bottles);

    Truck t1 = new Truck(Truck.Type.TRANSPORT);
    t1.load(waterContainer);
    if(Weather.current != Weather.RAINY) {
        WaterContainer extraWaterContainer = new WaterContainer();
        List<Bottle> extraBottles = new ArrayList<>();
        if(southEastCrops.isDry()) {
            for(LandSlot ls : southEastCrops.lands()) {
                Bottle b = new Bottle();
                b.addWater(10L);
                b.putCap();
                extraBottles.add(b);
            }
        } else {
            extraBottles.add(new Bottle());
        }
        if(eastCrops.isDry()) {
            for(LandSlot ls : southEastCrops.lands()) {
                Bottle b = new Bottle();
                b.addWater(10L);
                b.putCap();
                extraBottles.add(b);
            }
        } else {
            extraBottles.add(new Bottle());
        }
        extraWaterContainer.store(extraBottles);
        t1.load(extraWaterContainer);
    } else {
        WaterContainer extraWaterContainer = WaterSource.clone(waterContainer);
        t1.load(extraWaterContainer)
    }
}
```
- Compliant solution
```bash
void farmDailyRoutine() { // Compliant: Simpler method, making use of extracted and distributed logic
    Crops southEastCrops = getCrops(1, -1);
    Crops eastCrops = getCrops(1, 0);
    WaterContainer waterContainer = new WaterContainer();
    List<Bottle> bottles = getWaterBottles(10, 10L, true);
    waterContainer.store(bottles);

    Truck t1 = new Truck(Truck.Type.TRANSPORT);
    t1.load(waterContainer);
    if(Weather.current != Weather.RAINY) {
        WaterContainer extraWaterContainer = new WaterContainer();
        fillContainerForCrops(extraWaterContainer, southEastCrops);
        fillContainerForCrops(extraWaterContainer, eastCrops);
        t1.load(extraWaterContainer);
    } else {
        WaterContainer extraWaterContainer = WaterSource.clone(waterContainer);
        t1.load(extraWaterContainer)
    }
}

private fillContainerForCrops(WaterContainer wc, Crops crops) { // Compliant: extracted readable and reusable method
    if(crops.isDry()) {
        wc.store(getWaterBottles(crops.lands().size(), 10L, false));
    } else {
        wc.store(Collections.singleton(new Bottle()));
    }
}

private List<Bottle> getWaterBottles(int qt, long liquid, boolean shake){ // Compliant: extracted readable and reusable method
    List<Bottle> bottles = new ArrayList<>();
    for(int i = 0; i < qt; i++) {
        Bottle b = new Bottle();
        b.addWater(liquid);
        b.putCap();
        if(shake) {
            b.shake();
        }
        bottles.add(b);
    }
    return bottles;
}
```
## How does this work?
In this case, the method farmDailyRoutine was taking care of performing many different tasks, with nested conditions and loops, it was long and had plenty of local variables. By separating its logic into multiple single-responsibility methods, it is reusing parts of its original duplicated code and each of the new methods is now readable and easy to understand. They are now also easier to cover with tests, and many other parts of the owner class could benefit from using these methods.

![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/c6140564-5c88-4d7f-975f-eca7e72ec7ff)

----
Adaptability Issue 6| Not distinct | Maintainability
### Define a constant instead of duplicating this literal "select u from User u where u.username = :username and u.deleteDate is null" 4 times.
### Where is the Issue?
```bash
books-core/src/main/java/com/sismics/books/core/dao/jpa/UserDao.java
```
![Screenshot from 2024-02-14 16-43-46](https://github.com/serc-courses/se-project-1--_11/assets/46487934/351f9d48-80bd-43ec-9f13-4ed5a365c8e7)

### Why is this an Issue?
Duplicated string literals make the process of refactoring complex and error-prone, as any change would need to be propagated on all occurrences.

####  Exceptions:
To prevent generating some false-positives, literals having less than 5 characters are excluded.

### How can fix it?
Instead, use constants to replace the duplicated string literals. Constants can be referenced from many places, but only need to be updated in a single place.

#Noncompliant code example

```bash
# With the default threshold of 3:
public void run() {
  prepare("action1");                              // Noncompliant - "action1" is duplicated 3 times
  execute("action1");
  release("action1");
}

@SuppressWarning("all")                            // Compliant - annotations are excluded
private void method1() { /* ... */ }
@SuppressWarning("all")
private void method2() { /* ... */ }

public String printInQuotes(String a, String b) {
  return "'" + a + "'" + b + "'";               // Compliant - literal "'" has less than 5 characters and is excluded
}
```

#Compliant solution
```bash
private static final String ACTION_1 = "action1";  // Compliant

public void run() {
  prepare(ACTION_1);                               // Compliant
  execute(ACTION_1);
  release(ACTION_1);
}
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/fc54132b-c5b1-4521-93a3-126301118f9a)

----
Adaptability Issue 7 | Not modular | Maintainability
### A Singleton implementation was detected. Make sure the use of the Singleton pattern is required and the implementation is the right one for the context.
### Where is the Issue?
```bash
books-core/src/main/java/com/sismics/books/core/model/context/AppContext.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/ad2a0f8d-663b-4d5f-908a-92379120fdad)


### Why is this an Issue?
While the Singleton pattern can be useful in certain situations, overusing it can have several drawbacks:

- Tight coupling: The Singleton pattern can create tight coupling between the Singleton class and other classes that use it, which can make the code difficult to maintain and modify.
- Global state: The Singleton pattern can create global state, which can make it difficult to manage the state of the application and can lead to unexpected behavior.
- Testing: The Singleton pattern can make it difficult to test classes that depend on the Singleton, as the Singleton cannot be easily substituted with a mock object.
- Scalability: The Singleton pattern can make it difficult to scale an application, as it can create a bottleneck if multiple threads try to access the Singleton concurrently.
- Dependency injection: The Singleton pattern can make it difficult to use dependency injection frameworks, as the Singleton instance is usually created statically.

In general, the Singleton pattern should be used sparingly and only in situations where it provides a clear benefit over other patterns or approaches. It is important to consider the drawbacks and tradeoffs of using the Singleton pattern before incorporating it into an application.

### How can fix it?
Instead, use constants to replace the duplicated string literals. Constants can be referenced from many places, but only need to be updated in a single place.

#### What is the potential impact?
- Public Static Field Implementation
```bash
public class PublicStaticSingleton {

    public static final PublicStaticSingleton INSTANCE = new PublicStaticSingleton();

    private PublicStaticSingleton() {}
}
```

#### Advantage:
- This implementation is thread-safe.
#### Disadvantage:
- This implementation does not allow lazy initialization: the constructor runs as soon as the class is initialized.

### Eager Initialization Implementation
```bash
public class EagerInitializedSingleton {

    private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();

    private EagerInitializedSingleton() {}

    public static EagerInitializedSingleton getInstance() {
        return instance;
    }
}
```
#### Advantage:
- This implementation is thread-safe, as the instance variable is initialized when the class is loaded.
#### Disadvantages:
- The instance is created even if it’s never used, which can be wasteful in terms of memory usage. However, if the Singleton is expected to be used frequently or is not too memory-intensive, Eager Initialization can be a good choice.
- This implementation doesn’t provide any options for exception handling.

### Static Block Initialization Implementation

```bash
public class StaticBlockSingleton {

    private static StaticBlockSingleton instance;

    private StaticBlockSingleton(){}

    static {
        try {
            instance = new StaticBlockSingleton();
        } catch (Exception e) {
            throw new RuntimeException("Exception while creating singleton instance");
        }
    }

    public static StaticBlockSingleton getInstance() {
        return instance;
    }
}
}
```
#### Advantage:
- Compared to the Eager Initialization, this implementation provides options for exception handling.
#### Disadvantage:
- The instance is created even if it’s never used, like for the Eager Initialization implementation.

### Lazy Initialization Implementation
```bash
public class LazyInitializedSingleton {
    private static LazyInitializedSingleton instance;
    private LazyInitializedSingleton(){}
    public static LazyInitializedSingleton getInstance() {
        if (instance == null) {
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }
}
```
#### Advantage:
- This implementation works fine in the case of the single-threaded environment.
#### Disadvantage:
- This implementation is not thread-safe if multiple threads are at the same time in the if condition.
### Thread Safe Implementation
```bash
public class ThreadSafeSingleton {

    private static ThreadSafeSingleton instance;

    private ThreadSafeSingleton(){}

    public static synchronized ThreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new ThreadSafeSingleton();
        }
        return instance;
    }
}
```
#### Advantage:
- This implementation is thread-safe.
#### Disadvantage:
- It reduces the performance because of the cost associated with the synchronized method. To avoid this extra overhead every time, double-checked locking principle should be used.

#### Bill Pugh Implementation
```bash
public class BillPughSingleton {

    private BillPughSingleton(){}

    private static class SingletonHelper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```
#### Advantages:
- The instance is created only at the first call of the getInstance() method.
- This implementation is thread-safe.

### Enum Implementation
```bash
public enum EnumSingleton {

    INSTANCE;

    private EnumSingleton() {
        // Initialization code here...
    }
}
```
#### Advantages:
- This implementation is thread-safe by default because the initialization of an Enum value is guaranteed to be thread-safe and atomic.

- The Enum Singleton implementation allows for lazy initialization while also providing thread-safety guarantees.
- 
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/624f2fc0-da83-49db-929a-714eb992df91)

----
Adaptability Issue -8 | Not focused |Maintainability

# This file having multiple issue, fix the all of them:
1. Refactor this method to reduce its Cognitive Complexity from 25 to the 15 allowed.
2. Add a private constructor to hide the implicit public one.
3. Remove this hard-coded path-delimiter.
4. Replace the type specification in this constructor call with the diamond operator ("<>").

## Where is the Issue?
```bash
books-core/src/main/java/com/sismics/util/ResourceUtil.java
```
![image](https://github.com/serc-courses/se-project-1--_11/assets/46487934/9072304c-44d8-4bc1-9d32-9e1a9ea57283)

## Why is this an Issue?
Cognitive Complexity is a measure of how hard the control flow of a method is to understand. Methods with high Cognitive Complexity will be difficult to maintain

### Exceptions
equals and hashCode methods are ignored because they might be automatically generated and might end up being difficult to understand, especially in the presence of many fields.

## How can fix it?
### Resources
### Documentation
[Cognitive Complexity](https://www.sonarsource.com/docs/CognitiveComplexity.pdf)
### Articles & blog posts
[5 Clean Code Tips for Reducing Cognitive Complexity](https://www.sonarsource.com/blog/5-clean-code-tips-for-reducing-cognitive-complexity/)


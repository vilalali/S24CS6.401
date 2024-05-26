### adaptabilityIssue1
```bash
Define a constant instead of duplicating this literal "select ua from UserApp ua where ua.id = :id and ua.deleteDate is null" 3 times.
# Where is the Issue?
books-core/src/main/java/com/sismics/books/core/dao/jpa/UserAppDao.java
# Why is this an Issue?
Duplicated string literals make the process of refactoring complex and error-prone, as any change would need to be propagated on all occurrences.
# Exceptions:
To prevent generating some false-positives, literals having less than 5 characters are excluded.
# How can fix it?
![Image]()
```

### adaptabilityIssue2
```bash
Define a constant instead of duplicating this literal "username" 4 times.
# Where is the Issue?
books-core/src/main/java/com/sismics/books/core/dao/jpa/UserDao.java
# Why is this an Issue?
Duplicated string literals make the process of refactoring complex and error-prone, as any change would need to be propagated on all occurrences.
# Exceptions
To prevent generating some false-positives, literals having less than 5 characters are excluded.
# How can fix it?
![Image]()
```



### adaptabilityIssue7
```bash

# Where is the Issue?
# Why is this an Issue?
# How can fix it?
![Image]()
```

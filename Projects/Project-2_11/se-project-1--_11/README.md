[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/tUdRNrT1)
Sismics Books - Project 1
=========================

**Do not modify this README. Use the docs directory for anything you might want to submit**


What is Books?
---------------

Books is an open source, lightweight book management system.

Books is written in Java, and may be run on any operating system with Java support.

Features
--------

- Responsive user interface
- Add book by barcode scanning
- Bookshelves system
- Multi-users
- Goodreads import
- Support Google Books API and Open Library API
- RESTful Web API

License
-------

Books is released under the terms of the GPL license. See `LICENSE` for more
information or see <http://opensource.org/licenses/GPL-2.0>.

How to build Books from the sources
-----------------------------------

Prerequisites: JDK 7, Maven 3, Tesseract 3.02

### Changing Java Version <br>
You will need two versions of Java to work on this project. Book require JDK 7 and Sonarqube requires Java 11. So you will have to change Java versions for working on different parts.

### For Globally Changing Java Version (Only on Ubuntu) 
* Run the following command and select the version of Java you want to use.
  ```
  sudo update-alternatives --config java
  ```
* Similarly for javac.
  ```
  sudo update-alternatives --config javac
  ```
> Make sure you set the same version for both.  

### Or you can follow this method for Updating in Specific Runtime  (Mac & Linux)
* Instead of globally updating your Java version, it is better to temporarily change the Java version i.e. for as long as the terminal is open.
* This is done by setting the path variable JAVA_HOME to the version of Java you want to use.
* The command would be ```export JAVA_HOME=<path to java installation>``` for Mac and Linux.

> The paths mentioned here are only sample paths. Make sure you find out the actual path for your JDK and use that.


**On Mac**  
This is an example command and the actual command would look something like this - 
```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/<jdk-version-something>/Contents/Home/
```

**On Linux**  
This is an example command and the actual command would look something like this -
```
export JAVA_HOME=/usr/lib/jvm/<jdk-version-something>
```


**On Windows**  
Windows users, this is your cross to bear. [Here's](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html) a guide that might be of use. Again, feel free to contact us for any help.  
 

### Books is organized in several Maven modules: ###

  - books-parent
  - books-core
  - books-web
  - books-web-common

First off, clone the repository: `git clone git://github.com/sismics/books.git`
or download the sources from GitHub.

#### Launch the build

From the `books-parent` directory:

    mvn clean -DskipTests install

#### Run a stand-alone version

From the `books-web` directory:

    mvn jetty:run
  
#### Navigate to the following URL on your browser:

  http://localhost:8080/books-web/#/login

Use default credentials as admin(username) and admin(password).

#### Navigate to the following URL on your browser:

  http://localhost:8080/books-web/#/login

Use default credentials as admin(username) and admin(password).
    
#### Build a .war to deploy to your servlet container

From the `books-web` directory:

    mvn -Pprod -DskipTests clean install

You will get your deployable WAR in the `target` directory.

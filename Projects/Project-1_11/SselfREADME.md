### 1-Analyze "se11BookParent" 1: sqp_fcbfd8dce457465e8a12968c9429d7d5e94facd7
```bash
mvn clean -DskipTests=true verify sonar:sonar \
  -Dsonar.projectKey=se11BookParent \
  -Dsonar.projectName='se11BookParent' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_fcbfd8dce457465e8a12968c9429d7d5e94facd7
```
### 2- Analyze "se11BooksWeb": sqp_3ec5a253c1604c3581fd839178e6f339668d4df3
```bash
mvn clean -DskipTests=true verify sonar:sonar \
  -Dsonar.projectKey=se11BooksWeb \
  -Dsonar.projectName='se11BooksWeb' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_3ec5a253c1604c3581fd839178e6f339668d4df3
```
### 3-booksCore:
```bash
mvn clean verify sonar:sonar
  -Dsonar.projectKey=se11BooksCore \ 
  -Dsonar.projectName='se11BooksCore' \ 
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_19f7b58456d5327252fdf736086a87a47a3ad083
```

### 4-booksWebCommon:
```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=se11BooksWebCommon \
  -Dsonar.projectName='se11BooksWebCommon' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_bc437e7bc14b39950da02cb7814ffff1160f1393
```

# Updating in the files when running first time:
## Default Updated
```bash
# books-android/gradle/wrapper/gradle-wrapper.properties

  #Fri Feb 09 17:26:08 IST 2024
  distributionBase=GRADLE_USER_HOME
  distributionUrl=https\://services.gradle.org/distributions/gradle-8.1.1-bin.zip
  distributionPath=wrapper/dists
  zipStorePath=wrapper/dists
  zipStoreBase=GRADLE_USER_HOME
```
```bash
# .project
- Updated this file as default.
```

```bash
# books-web/sismicsbooks/book
- Updated this file as DIR.
# books-web/sismicsbooks/log/books.log
- Updated this file as log file.
```
```bash
# .classpath
- Updated this file as default.
```

### Manual updated
```bash
# books-web/pom.xml
- Added the below line on line number 265.
  <version>3.3.2</version>
```

```bash
# books-parent/pom.xml
- Search 'maven-war-plugin' then Updated line number 49 for version 2.2 to 3.3.2:
  <org.apache.maven.plugins.maven-war-plugin.version>3.3.2</org.apache.maven.plugins.maven-war-plugin.version>  
- Updated line number 100 for version 2.2 to 3.3.2:
  <version>3.3.2</version>
```
### Failed to execute goal org.mortbay.jetty:jetty-maven-plugin:8.1.2.v20120308:run

```bash
# books-parent/pom.xml
- Search 'jetty-maven-plugin' then Updated line number 51 for version 8.1.2.v20120308 to 9.4.44.v20210927
```

```bash
# books-web/pom.xml
- Search 'jetty-maven-plugin' then Updated line number 130 for version 8.1.2.v20120308 to 9.4.44.v20210927

- Search 'jetty-maven-plugin' then Updated line number 217 for version 8.1.2.v20120308 to 9.4.44.v20210927
# Then run this command:
$ mvn org.eclipse.jetty:jetty-maven-plugin:9.4.44.v20210927:run

```

## Error k liye ye search kre:
- maven-war-plugin and change the version from 2.2 to 3.3.2
- jetty-maven-plugin and change the version to 9.4.44.v20210927.
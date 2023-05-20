## Objective

Create a standalone java application which allows users to uploading and getting a csv file. 
Additionally, users can be able to filter available exercises.

## Requirements
Please ensure that the first line of the file are the headers, also the field code is unique

All these requirements needs to be satisfied:

1. Upload the data
2. Fetch all data.
3. Fetch by code
4. Delete all data
-----------------------------------------

## Setup guide

#### Minimum Requirements

- Java 11
- Gradle 7.x

#### Install the application

1. Make sure you have [Java](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) and [Gradle](https://gradle.org/install/) installed

2. Open the command line in the source code folder

3. Build project

  ```
  ./gradlew clean build
  ```
Or

  ```
  gradle clean build
  ```

Run the project
 ```
 gradlew clean bootRun
 ```
Or

```
java -jar build/libs/csv.jar
```

4. Open the swagger-ui with the link below

```
http://localhost:3000/swagger-ui.html#/
```

-----------------------------------------
## The solution
I have built the project with a clean architected, and also I use the Gradle multi-module on the code structure.

One of the primary benefits of using CleanArchitecture is improved maintainability. By separating the concerns of the various components and enforcing the dependency rule, it becomes much easier to understand and modify the code.

Also, I used the H2 as a Database because it can be run in memory, and it is effective in small projects, with simple relations.

Besides, I added the swagger, so I can test the APIs.
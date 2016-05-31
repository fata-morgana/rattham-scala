# rattham-scala
Task management application implemented using Java, with memory storage and RESTful webservice. You don't need any database installed. However, everything will be reset when you restart the service. Also, we don't provide any UI to communicate with our service.

## Prerequisite
- Java 8 or higher
- scala 2.10.2
- Maven

## Quick start
- Clone this repository
- run `mvn clean install`
- run `mvn jetty:run`
- enjoy!

## Overview
You can access this application via port `8080` and context path `rattham`. ie. `http://localhost:8080/rattham`

A task concises of subject and content. A newly-created task will be given status `PENDING`. When you finish a task, you can change its status to `DONE`. You can also edit and delete a task.

However, once a task is done or deleted, you can't edit it. Furthermore, a task can't be moved to done if it's already move to done or deleted.

# Instructions

- view all task
`GET http://localhost:8080/rattham/task`

- view a specific task
`GET http://localhost:8080/rattham/task/{taskNumber}`

- insert a new task
`POST http://localhost:8080/rattham/task` with data `{"subject":"subjectName","content":"description"}`

- edit a task
`PUT http://localhost:8080/rattham/task/{taskNumber}` with data `{"subject":"subjectName","content":"description"}`

- move task to done
`GET http://localhost:8080/rattham/task/{taskNumber}/done`

- delete a task
`DELETE http://localhost:8080/rattham/task/{taskNumber}`

You can also consult `curl_command` file for curl usage.

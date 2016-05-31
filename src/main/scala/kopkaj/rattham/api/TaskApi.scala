package kopkaj.rattham.api

import net.liftweb.json.JsonDSL._
import net.liftweb.util.Helpers.AsInt
import net.liftweb.json.JsonAST._
import net.liftweb.json.Printer._

import net.liftweb.http.rest.RestHelper
import kopkaj.rattham.model.Task
import net.liftweb.common.Full
import kopkaj.rattham.model.TaskStatus
import net.liftweb.http.OkResponse
import net.liftweb.http.BadResponse
import net.liftweb.http.JsonResponse
import net.liftweb.http.NotFoundResponse
import net.liftweb.http.NoContentResponse

object TaskApi extends RestHelper {
  def getTasks() : JArray = {
    val tasks = Task.getTasksOn(taskStatus => taskStatus != TaskStatus.DELETED)
    for {task <- tasks ; taskJson = task.toJson} yield taskJson
  }
  
  def getTask(taskId: Int) = {
    val tasks = Task.getTask(taskId).map(_.toJson)
    if (tasks.isEmpty) {
        new BadResponse
    } else {
        JsonResponse(tasks, Nil, Nil, 200)
    }
  }
  
  def addTask(taskInput : JValue): JInt = {
    val newTaskId = Task.addTask((taskInput \ "subject").extract[String], (taskInput \ "content").extract[String])
    JInt(newTaskId)
  }
  
  def editTask(taskId: Int, taskInput: JValue) = {
    if ( Task.isTaskMatch(taskId, taskStatus => taskStatus == TaskStatus.PENDING )) {
        Task.edit(taskId, (taskInput \ "subject").extract[String], (taskInput \  "content").extract[String])
        new OkResponse
    } else {
        new BadResponse
    }
  }
  
  def changeStatusToDone(taskId: Int) = {
    if ( Task.isTaskMatch(taskId, taskStatus => taskStatus == TaskStatus.PENDING )) {
        Task.moveToDone(taskId)
        new OkResponse
    } else {
        new BadResponse
    }
  }
  
  def delete(taskId: Int) = {
    if ( Task.isTaskMatch(taskId, taskStatus => taskStatus != TaskStatus.DELETED )) {
        Task.markInactive(taskId)
        new NoContentResponse
    } else {
        new BadResponse
    }
  }
  
  serve {
    case "rattham" :: "task" :: AsInt(taskId) :: Nil JsonGet req => getTask(taskId)
    case "rattham" :: "task" :: Nil JsonGet req => getTasks()
    case "rattham" :: "task" :: Nil JsonPost ((taskInput, req)) => addTask(taskInput)
    case "rattham" :: "task" :: AsInt(taskId) :: Nil JsonPut ((taskInput, req)) => editTask(taskId, taskInput)
    case "rattham" :: "task" :: AsInt(taskId) :: "done" :: Nil JsonGet req => changeStatusToDone(taskId)
    case "rattham" :: "task" :: AsInt(taskId) :: Nil JsonDelete req => delete(taskId)
  }
}
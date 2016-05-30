package kopkaj.rattham.api

import net.liftweb.json.JsonDSL._
import net.liftweb.util.Helpers.AsInt
import net.liftweb.json.JsonAST._
import net.liftweb.json.Printer._

import net.liftweb.http.rest.RestHelper
import kopkaj.rattham.model.Task
import net.liftweb.common.Full

object TaskApi extends RestHelper {
  def getTasks() : JArray = {
    val tasks = Task.getTasks
    for {task <- tasks ; taskJson = task.toJson} yield taskJson
  }
  
  def getTask(taskId: Int): Option[JValue] = {
    Task.getTask(taskId).map(_.toJson)
  }
  
  serve {
    case "rattham" :: "task" :: AsInt(taskId) :: Nil JsonGet req => getTask(taskId)
    case "rattham" :: "task" :: Nil JsonGet req => getTasks()
  }
}
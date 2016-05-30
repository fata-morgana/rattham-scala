package kopkaj.rattham.model

import kopkaj.rattham.model.TaskStatus._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.Extraction

case class Task (id: Int, subject: String, content: String, taskStatus: TaskStatus) {
  implicit val formats = org.json4s.DefaultFormats
    // + new org.json4s.native.ext.JsonBoxSerializer

  def toJson = {
    import net.liftweb.json._
    import net.liftweb.json.JsonDSL._
    
    ("id" -> id) ~ ("subject" -> subject) ~ ("content" -> content) ~ ("taskStatus" -> taskStatus.toString())
  }
  
}

object Task {
  var tasks: List[Task] = Nil
  
  def addTask(subject: String, content: String): Int = {
    val nextId = tasks.size + 1;
    val newTask = new Task(nextId, subject, content, PENDING)
    tasks ::= newTask
    tasks.size
  }
  
  def getTasks = {
    tasks
  }
  
  def getTask(id: Int): Option[Task] = {
    tasks.filter(_.id == id).headOption
  }
}
package kopkaj.rattham.model

import kopkaj.rattham.model.TaskStatus._
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.Extraction

case class Task (id: Int, private var subject: String, private var content: String, private var taskStatus: TaskStatus) {
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
    val nextId = tasks.size;
    val newTask = new Task(nextId, subject, content, PENDING)
    tasks ::= newTask
    nextId
  }
  
  def getTasks = {
    getTasksOn((taskStatus) => true)
  }
  
  def getTasksOn(filterFunc: (TaskStatus) => Boolean) = {
    for {task <- tasks ; if filterFunc(task.taskStatus)} yield task
  }
  
  def moveToDone(taskId : Int) = {
    tasks.filter { _.id == taskId }.map { _.taskStatus = TaskStatus.DONE }
  }
  
  def markInactive(taskId : Int) = {
    tasks.filter { _.id == taskId }.map { _.taskStatus = TaskStatus.DELETED }
  }
  
  def edit(taskId: Int, subject: String, content : String) = {
    tasks.filter { _.id == taskId }.map { (task) =>
      task.subject = subject
      task.content = content }
  }
  
  def isTaskMatch(taskId : Int, filterFunc: (TaskStatus) => Boolean) = {
    val optTask = getTask(taskId)
    optTask.nonEmpty && filterFunc(optTask.get.taskStatus)
  }
  
  def getTask(taskId: Int): Option[Task] = {
    getTasksOn(taskStatus => taskStatus != TaskStatus.DELETED).filter(_.id == taskId).headOption
  }
}
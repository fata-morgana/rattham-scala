package kopkaj.rattham.model

object TaskStatus extends Enumeration {
  type TaskStatus = Value
  val PENDING = Value("PENDING")
  val DONE = Value("DONE")
  val DELETED = Value("DELETED")
}  
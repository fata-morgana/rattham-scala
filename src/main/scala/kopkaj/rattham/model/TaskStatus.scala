package kopkaj.rattham.model

object TaskStatus extends Enumeration {
  type TaskStatus = Value
  val PENDING = Value("PENDING")
  val DONE = Value("Done")
  val DELETED = Value("DELETED")
}  
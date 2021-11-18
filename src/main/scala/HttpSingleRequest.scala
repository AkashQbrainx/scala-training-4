import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest

import scala.util.{Failure, Success}

object HttpSingleRequest extends App {
  implicit val system=ActorSystem(Behaviors.empty,"MyAco")
  implicit val execution=system.executionContext


  val a=Http().singleRequest(HttpRequest(uri="http://akka.io"))
  a
    .onComplete{
      case Success(value)=>println(value)
      case Failure(_)   => sys.error("something wrong")
    }

}

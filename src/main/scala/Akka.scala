
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
object Akka extends App {

  implicit val sys = ActorSystem(Behaviors.empty, "maa")

  case class Student(name:String,age:Int)

  val route: Route =
    path("hello"){
      post{
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`,"scala beauty"))
      }
    }


val a=Http().newServerAt("localhost",8081).bind(route)
}
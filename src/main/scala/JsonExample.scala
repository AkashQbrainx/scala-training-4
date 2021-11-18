import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

import scala.concurrent.ExecutionContextExecutor
import spray.json._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{StatusCode, StatusCodes}

import java.util.UUID
object JsonExample extends App{

  implicit val system: ActorSystem[Nothing] =ActorSystem(Behaviors.empty,"MyActor")
  implicit val executionContext: ExecutionContextExecutor =system.executionContext

  case class Person(name:String,age:Int)
  case class User(id:String,timeStamp:Long)

  implicit val personJsonFormat: RootJsonFormat[Person] =jsonFormat2(Person)
 implicit val userJsonFormat: RootJsonFormat[User] =jsonFormat2(User)

  val route=(path("json")&post){
        entity(as[Person]){person:Person=>
          complete(User(UUID.randomUUID().toString,System.currentTimeMillis()))
        }
      }

  Http().newServerAt("localhost",8081).bind(route)

}

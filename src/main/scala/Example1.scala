

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Source
import akka.util.ByteString

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn


object Example1 extends App {

  implicit val system: ActorSystem[Nothing] =ActorSystem(Behaviors.empty,"MyActor")
  implicit val executionContext: ExecutionContextExecutor =system.executionContext

  val source=Source.fromIterator(()=>new Iterator[Int]{
    override def hasNext: Boolean = true

 var a=1
    override def next(): Int = {
      a+=1
      a
    }
  })
  val route:Route=path("hello"){
    get{

     complete(
       HttpEntity(ContentTypes.`text/plain(UTF-8)`,source.map(f=>
         ByteString(s"$f\n"))))
    }
  }
 val a= Http().newServerAt("localhost",8081).bind(route)
  println("go to  http://localhost:8081/hello")
  val input=StdIn.readLine()
  a
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())




}

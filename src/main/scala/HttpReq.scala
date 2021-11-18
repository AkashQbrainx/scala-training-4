import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}



  import akka.actor.typed.ActorSystem
  import akka.actor.typed.scaladsl.Behaviors
  import akka.http.scaladsl.Http
  import akka.http.scaladsl.model._

  import scala.concurrent.Future
  import scala.util.{ Failure, Success }

  object HttpClientSingleRequest {
    def main(args: Array[String]): Unit = {
      implicit val system = ActorSystem(Behaviors.empty, "SingleRequest")

      implicit val executionContext = system.executionContext

      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = "http://akka.io"))

      responseFuture
        .onComplete {
          case Success(res) => println(res)
          println(res.isResponse())
          case Failure(_)   => sys.error("something wrong")
        }
    }


}

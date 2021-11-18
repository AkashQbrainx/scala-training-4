import akka.actor.{Actor, ActorLogging, ActorSystem}
import akka.http.scaladsl.{Http, HttpExt}
import akka.http.scaladsl.model._
import akka.util.ByteString
object Example3 extends App{
class Myself extends Actor
  with ActorLogging {

  import akka.pattern.pipe
  import context.dispatcher

  implicit val system: ActorSystem = context.system
  val http: HttpExt = Http(system)

  override def preStart(): Unit = {
    http.singleRequest(HttpRequest(uri = "http://akka.io"))
      .pipeTo(self)
  }

  def receive: Receive = {
    case HttpResponse(StatusCodes.OK, headers, entity, _) =>
      entity.dataBytes.runFold(ByteString(""))(_ ++ _).foreach { body =>
        println("Got response, body: " + body.utf8String)
      }
    case resp @ HttpResponse(code, _, _, _) =>
      log.info("Request failed, response code: " + code)
      resp.discardEntityBytes()
  }

}}
package testhttp

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Source
import akka.util.ByteString
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import testhttp.models.Item
import testhttp.routes
import testhttp.routes.Route1

import java.lang.Thread.sleep
import scala.io.StdIn
import scala.util.Random

object HttpServer extends App {

  implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpExample")
  implicit val executionContext = system.executionContext

  val httpServer = Http().newServerAt("localhost", 8080)
  val bindingFuture = httpServer.bind(Route1.route)

  println(s"Server now online. Please navigate to http://localhost:8080/hello\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
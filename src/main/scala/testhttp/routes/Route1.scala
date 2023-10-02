package testhttp.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.stream.scaladsl.Source
import akka.util.ByteString
import spray.json.RootJsonFormat
import testhttp.blogic.{addItem, getItems}
import testhttp.models.Item

import scala.util.Random

object Route1 {

  import akka.http.scaladsl.server.Directives._
  val route =
    path("get") {
      concat(
        get {
          import spray.json.DefaultJsonProtocol._
          implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item.apply)
          import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.sprayJsonMarshaller
          complete(getItems)
        }
      )
    } ~
      path("get-stream") {
        get {
          val ids = Source.fromIterator(() => Iterator.continually(Random.nextInt()))
          complete(
            HttpEntity(
              ContentTypes.`text/plain(UTF-8)`,
              // transform each number to a chunk of bytes
              ids.map(n => ByteString(s"$n\n"))
            )
          )
        }
      } ~
      path("post"){
        post {
          import spray.json.DefaultJsonProtocol._
          implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item.apply)
          import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport.{sprayJsonMarshaller, sprayJsonUnmarshaller}
          entity(as[Item]) { item =>
            println(s"item: $item")
            complete(StatusCodes.Accepted, addItem(item))
          }
        }
      }

}

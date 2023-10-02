package testhttp

import testhttp.models.Item

import java.lang.Thread.sleep
import scala.concurrent.Future

package object blogic {
  import scala.concurrent.ExecutionContext.Implicits.global
  def addItem(itm: Item): Future[List[Item]] = getItems.map {
    List(itm) ++ _
  }

  def getItems(): Future[List[Item]] = Future {
    sleep(50)
    List(Item("Ahmad Adeel", 23))
  }

}

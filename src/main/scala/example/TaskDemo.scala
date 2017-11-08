package example

import org.asynchttpclient._
import scalaz._
import scalaz.syntax.either._
import scalaz.concurrent.Task

object TaskDemo {

  val asyncHttp = new DefaultAsyncHttpClient

  def oldStyleGet(): String = {
    val f = asyncHttp.prepareGet("https://api.mazeforce.com/").execute()
    f.get().getResponseBody
  }

  def get(s: String) = Task.async((k: \/[Throwable, String] => Unit) => asyncHttp.prepareGet(s).execute(toHandler(k)))

  private def toHandler(k: \/[Throwable, String] => Unit) = {
    new AsyncCompletionHandler[String] {
      override def onCompleted(response: Response) = {
        val result = response.getResponseBody
        k(result.right[Throwable])
        result
      }
    }
  }
}
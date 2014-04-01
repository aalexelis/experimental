package code.lib

import net.liftweb.http.rest.RestHelper
import net.liftweb.common.Loggable
import net.liftweb.http.{S, OkResponse}

/**
 * Created by andreas on 3/17/14.
 */
object RestTestShopProAPI extends RestHelper {
  serve {
    case "testshopproapi" :: Nil Get _ =>
      println("BINGO: " + S.param("code"))
      OkResponse()
  }

}

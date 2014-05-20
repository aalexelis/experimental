package code.lib

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.OkResponse

/**
 * Created by andreas on 5/19/14.
 */
object RestCart extends RestHelper {

  serve {
    case "cart" :: Nil JsonGet _ =>
      OkResponse()

    case "cart" :: Nil JsonPost _ =>
      OkResponse()
  }

}

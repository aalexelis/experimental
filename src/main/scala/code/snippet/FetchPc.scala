package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js.{JsCmd, JsCmds}
import dispatch._
import Defaults._
import net.liftweb.common.Loggable

/**
 * Created by andreas on 4/2/14.
 */
class FetchPc extends Loggable {
  def render = "#fetchpc" #> SHtml.ajaxButton("FetchPC", fetchAndSet)

  def fetchAndSet = () => {
    val svc = url("http://localhost:8080/xml/zip.php?zn=1030000")
    val zip = Http(svc OK as.xml.Elem)

    for ( z <- zip ) logger.info("z: "+z)
    logger.info("between")
    JsCmds.Run("console.log($('#shippingAddressCity').val('Hyogo'))")
  }

}

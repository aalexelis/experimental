package code.lib

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{BadResponse, XmlResponse, S}
import scala.xml.{Node, NodeSeq}
import net.liftweb.common.Full

/**
 * Created by andreas on 4/2/14.
 */
object postalcodeAPI extends RestHelper {

  serve{
    case "xml" :: "zip.php" :: Nil Get _ =>
      Thread.sleep(10000)
      S.param("zn") match {
        case Full(zn) => XmlResponse(dummy1030000)
        case _ =>  BadResponse()
      }
  }

  val dummy1030000:Node = <ZIP_result>
    <result name="ZipSearchXML" />
    <result version="1.01" />
    <result request_url="http%3A%2F%2Fzip.cgis.biz%2Fxml%2Fzip.php%3Fzn%3D1030000" />
    <result request_zip_num="1030000" />
    <result request_zip_version="none" />
    <result result_code="1" />
    <result result_zip_num="1030000" />
    <result result_zip_version="0" />
    <result result_values_count="1" />
    <ADDRESS_value>
      <value state_kana="ﾄｳｷｮｳﾄ" />
      <value city_kana="ﾁｭｳｵｳｸ" />
      <value address_kana="ｲｶﾆｹｲｻｲｶﾞﾅｲﾊﾞｱｲ" />
      <value company_kana="none" />
      <value state="東京都" />
      <value city="中央区" />
      <value address="none" />
      <value company="none" />
    </ADDRESS_value>
  </ZIP_result>

}

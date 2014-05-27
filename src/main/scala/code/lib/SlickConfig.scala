package code.lib

import net.liftweb.http.Factory
import net.liftweb.common.Loggable
import org.h2.engine.Session
import scala.slick.driver.H2Driver

// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._

/**
 * Created by andreas on 5/21/14.
 */
object SlickConfig extends Factory with Loggable {
  lazy val db = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver")

  def dbtransaction[U](q:(H2Driver.backend.Session) => U) = {
     db withSession(q)
  }

  def init() {
    db
    ()
  }


}
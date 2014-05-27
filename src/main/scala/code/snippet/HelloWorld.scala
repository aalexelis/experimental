package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import java.util.Date
import code.lib._
import Helpers._

import lib.SlickConfig._
// Use H2Driver to connect to an H2 database
import scala.slick.driver.H2Driver.simple._

class HelloWorld {
  lazy val date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  // replace the contents of the element with id "time" with the date
  def howdy = "#time *" #> date.map(_.toString)

  /*
   lazy val date: Date = DependencyFactory.time.vend // create the date via factory

   def howdy = "#time *" #> date.toString
   */

  class TestTbl(tag: Tag) extends Table[(Int, String)](tag, "Test"){
    def id = column[Int]("ID", O.PrimaryKey)
    def name = column[String]("NAME")

    def * = (id, name)
  }
  val tests = TableQuery[TestTbl]

  def getTests():(Int,String) = dbtransaction{ implicit session => {
      tests.ddl.create

      tests += (1, "first test")
      tests.firstOption.get
    }
  }

  def slick = "#slick *" #> getTests._2
}


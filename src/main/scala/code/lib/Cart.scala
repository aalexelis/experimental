package code.lib

import net.liftweb.util.ValueCell
import scala.tools.scalap.scalax.rules.Monad

/**
 * Created by andreas on 5/18/14.
 */

case class Item(id: String)

case class CartItem(item: Item, qty: BigDecimal, amt: BigDecimal)

class Cart{
  val contents = ValueCell[Vector[CartItem]](Vector())

  val totQty = contents.lift( _.map(_.qty).foldLeft(BigDecimal(0)){_ + _} )

  val totAmt = contents.lift( _.map(_.amt).foldLeft(BigDecimal(0)){_ + _} )

  def addItem(ai: CartItem){
    contents.atomicUpdate( v => {
      v.find(_.item == ai.item) match {
        case None => (v :+ ai)
        case Some(ci) => v.map( c =>
          c.item match {
            case ci.item => CartItem(c.item, c.qty+ai.qty, c.amt+ai.amt)
            case _ => c
          })
      }
    })
  }

  def setItem(si: CartItem){
    contents.atomicUpdate( v => {
      v.find(_.item == si.item) match {
        case None if (si.qty > 0) => (v :+ si)
        case Some(ci) if (si.qty > 0) => v.map( c =>
          c.item match {
            case ci.item => CartItem(c.item, si.qty, si.amt)
            case _ => c
          })
        case Some(ci) if (si.qty <=0) => v.filter(_.item != si.item)
      }
    })
  }

  def removeItem(ri: CartItem ){
    setItem( CartItem(ri.item, BigDecimal(0), BigDecimal(0)))
  }
}

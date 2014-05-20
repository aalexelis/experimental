package code.lib

import org.scalatest.{Matchers, FlatSpec}
import net.liftweb.common.Loggable


/**
 * Created by andreas on 2/21/14.
 */

class TestCart extends FlatSpec with Matchers with Loggable {
  behavior of "Cart"

  it should "be empty at the beginning of a session " in {
    val cart = new Cart()
    cart.contents.get shouldBe empty
    cart.totQty.get shouldBe BigDecimal(0)
    cart.totAmt.get shouldBe BigDecimal(0)
  }

  it should "add new item (item1, 1, 1000) properly " in {
    val item1 = CartItem(Item("item1"), 1, 1000)
    val cart = new Cart()
    cart.addItem(item1)
    cart.contents.get shouldBe Vector( item1 )
    cart.totQty.get shouldBe BigDecimal(1)
    cart.totAmt.get shouldBe BigDecimal(1000)
  }

  it should "add new item (item2, 2, 1600) to the previous properly " in {
    val item2 = CartItem(Item("item2"), 2, 1600)
    val item1 = CartItem(Item("item1"), 1, 1000)
    val cart = new Cart()
    cart.addItem(item1)
    cart.addItem(item2)
    cart.contents.get shouldBe Vector( item1, item2 )
    cart.totQty.get shouldBe BigDecimal(3)
    cart.totAmt.get shouldBe BigDecimal(2600)
  }

  it should "add a repeating item (item1, 2, 2000) to the previous properly " in {
    val item2 = CartItem(Item("item2"), 2, 1600)
    val item1 = CartItem(Item("item1"), 1, 1000)
    val cart = new Cart()
    cart.addItem(item1)
    cart.addItem(item2)
    cart.addItem( CartItem(Item("item1"), 2, 2000))
    cart.contents.get shouldBe Vector( CartItem(Item("item1"), 3, 3000), item2 )
    cart.totQty.get shouldBe BigDecimal(5)
    cart.totAmt.get shouldBe BigDecimal(4600)
  }

  it should "set a preexisting item (item1, 3, 3000) " in {
    val item1 = CartItem(Item("item1"), 1, 1000)
    val cart = new Cart()
    cart.addItem(item1)
    cart.setItem( CartItem(Item("item1"), 3, 3000))
    cart.contents.get shouldBe Vector( CartItem(Item("item1"), 3, 3000) )
    cart.totQty.get shouldBe BigDecimal(3)
    cart.totAmt.get shouldBe BigDecimal(3000)
  }

  it should "remove a preexisting item when qty set to 0 (item1, 0, 0) " in {
    val item1 = CartItem(Item("item1"), 3, 3000)
    val item2 = CartItem(Item("item2"), 2, 1600)
    val cart = new Cart()
    cart.addItem(item1)
    cart.addItem(item2)
    cart.setItem( CartItem(Item("item1"), 0, 0))
    cart.contents.get shouldBe Vector( item2 )
    cart.totQty.get shouldBe BigDecimal(2)
    cart.totAmt.get shouldBe BigDecimal(1600)
  }

  it should "remove a preexisting item (item1, 3, 3000) " in {
    val item1 = CartItem(Item("item1"), 3, 3000)
    val cart = new Cart()
    cart.addItem(item1)
    cart.removeItem( CartItem(Item("item1"), 1, 1000))
    cart.contents.get shouldBe Vector()
    cart.totQty.get shouldBe BigDecimal(0)
    cart.totAmt.get shouldBe BigDecimal(0)
  }



}
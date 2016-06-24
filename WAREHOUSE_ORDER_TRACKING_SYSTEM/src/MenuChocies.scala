import scala.annotation.switch
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn._

/**
  * Created by jonspyreas on 6/24/16.
  */

object MenuChocies{

  def menu(choice: Int, Orders: ArrayBuffer[Array[String]]): Unit ={
      var x = (choice: @switch) match {
        case 1 =>
          orderPrint.printOrders(Orders)
        case 2 =>
          do {
            println("Add supply order Item Name, New Stock Number, Supplyer, Item Number, is it a new Item true/false")
            StockDeliveries.addStockOrder(readLine(), readInt(), readLine(), readInt(), readBoolean())
            println("next order? y/n")
          } while ('y'.equals(readChar()))
        case 3 =>
          println("select order number to update and take")
         val chosenOrder =readInt()
          updateStock.decormentStock(Orders, chosenOrder)
          println("new Status")
          orderPrint.selectOrderAndUpdate(chosenOrder, readLine())
          orderPrint.writetoFile(Orders)

        case 4 =>
          println("select order number to send to accounts")
          val chosenOrder = scala.io.StdIn.readInt()
          notifyAccounts.readFile()
          notifyAccounts.mail(Orders, chosenOrder)

        case 5 =>
          println("select stock Order to send to accounts by Item ID")
          val chosenOrderID = scala.io.StdIn.readInt()
          notifyAccounts.readStockFile()
        //notifyAccounts.mailStock(notifyAccounts.StockSent,  )
        case 6 =>
          StockDeliveries.currentStock
          println("select item to locate")
          val p: Item = StockDeliveries.search(readLine(), StockDeliveries.StockList)
          println(p.Location(0) + " " + p.Location(1) + " " + p.Location(2))

        case 7 =>
          orderPrint.readFile()
          println("chose order to pick root for")
          TravalingSalesMan.traval(Orders(readInt()))


        case _ =>
          sys.exit()
      }
      orderPrint.printMenu()

      menu( readInt(), Orders)
    }


}
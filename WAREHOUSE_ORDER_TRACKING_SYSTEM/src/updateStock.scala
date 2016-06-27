import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


/**
  * Created by jonspyreas on 6/15/16.
  */
//should have made recersive/ think there might be a small error in line 16 forgot to change deliminator
object updateStock{
  var ItemsPare : mutable.HashMap[String, Int]= mutable.HashMap()
  def decormentStock(Order: ArrayBuffer[Array[String]], Ordernum : Int ): Unit ={
    StockDeliveries.currentStock
    val items: String =orderPrint.Orders(Ordernum)(7)
    val lineItems: Array[String] = items.split("\\|")
    for(x <- lineItems) {
      val temp: Array[String] = x.split("[\\:]")
      println(temp(0))
      for (y <- StockDeliveries.StockList)
        if (y.itemName == temp(0)) {
          var p = y.Stock
          p -= temp(1).toString.toInt
          y.Stock = p
          println(p)
          ItemsPare.put(temp(0),temp(1).toInt)
      }
    }
  }
}

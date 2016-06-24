import scala.collection.immutable.HashMap._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn._

object StockDeliveries {
  var StockList: ArrayBuffer[Item]= ArrayBuffer()
  var supplierList : mutable.HashSet[String] = mutable.HashSet()
  var StockOrders : ArrayBuffer[Array[String]] = ArrayBuffer()

  def currentStock(): Unit ={
    val bufferedSource = io.Source.fromFile("/src/orderStock")
    val temp: ArrayBuffer[Array[String]]=ArrayBuffer()
    for (line <- bufferedSource.getLines) {
      temp.append(line.split(","))
    }
    bufferedSource.close
    for(x <- temp) {
      val r=x(3).split("\\|").map(_.trim)
      val p: Item= new Item(x(0).toInt ,x(1), r, x(2).toInt)
      StockList.append(p)
    }

  }
  def addSuplier(name: String){
    supplierList.add(name)
    //add new suppliers to set since they should not repeat
  }
  def addInventory(ItemNum: Int, newStock: Int){
   for(x<- StockList){
    if(x.itemID == ItemNum) {
       x.Stock= newStock + x.Stock
    }
   }
  }
  def addStockOrder(itemName: String, newStock: Int,Supplyer: String, itemNumb: Int, newItem: Boolean ){
    if(newItem.equals(false)) {
      if (supplierList.contains(Supplyer)) {
        addItem(itemName, newStock, itemNumb)
      } else {
        addSuplier(Supplyer)
        addItem(itemName, newStock, itemNumb)
      }
    }else{
      if (supplierList.contains(Supplyer)) {
        println("give row and case for the location")
        val k: Item= new Item(itemNumb,itemName,Array(readLine(),readLine()),newStock)
        StockList.append(k)
      } else {
        addSuplier(Supplyer)
        println("give row and case for the location")
        val k: Item= new Item(itemNumb,itemName,Array(readLine(),readLine()),newStock)
        StockList.append(k)
      }
    }
    StockOrders.append( Array(itemName,newStock.toString,Supplyer,itemNumb.toString))
    println(itemName,newStock,Supplyer,itemNumb)

  }
  def addItem(itemName: String,newStock: Int,itemNumb: Int): Unit = {
    for (x <- StockList) {
      if (x.itemID == itemNumb) {
        addInventory(itemNumb, newStock)
      }
    }
  }
  def search(ItemName: String, StockList: ArrayBuffer[Item]): Item = {
    if (ItemName.equals(StockList.head.itemName)) {
      StockList.head
    }else {
       search(ItemName, StockList.tail)
    }

  }
  def main(args: Array[String]): Unit ={
    do {
      println("Add supply order Item Name, New Stock Number, Supplyer, Item Number, is it a new Item true/false")
      addStockOrder(readLine(), readInt(), readLine(), readInt(), readBoolean())
      println("next order? y/n")
    }while('y'.equals(readChar()))


  }

}

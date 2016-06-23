import java.io.{File, FileNotFoundException, PrintWriter}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by jonspyreas on 6/16/16.
  */
object notifyAccounts  {
  var Order: ArrayBuffer[Array[String]]= ArrayBuffer()
  def  readFile(): Unit = {

      val bufferedSource = io.Source.fromFile("src/accountUpdate.csv")
      for (line <- bufferedSource.getLines) {
        Order.append(line.split(","))
      }
      bufferedSource.close
  }

  def mail(Orders: ArrayBuffer[Array[String]],chosenOrder: Int): Unit ={
    val out = new PrintWriter(new File("src/accountUpdate.csv"));
    val r = Orders(chosenOrder)
    Order.append(r)
    for (i<- Order){
      for(j<- i){
        out.write( j + ',')
      }
      out.write("\n")
    }
    out.close()
  }
  var Stock: ArrayBuffer[Array[String]]= ArrayBuffer()
  var StockSent: ArrayBuffer[Item]= ArrayBuffer()
  def  readStockFile(): Unit = {

    val bufferedSource = io.Source.fromFile("src/StockUpdate.csv")
    for (line <- bufferedSource.getLines) {
      Stock.append(line.split(","))
    }
    for(x <- Stock) {
      val r=x(3).split("\\|").map(_.trim())
      val p: Item= new Item(x(0).toInt ,x(1), r, x(2).toInt);
      StockSent.append(p)
    }
    bufferedSource.close
  }

  def mailStock(Orders: ArrayBuffer[Item],chosenOrder: Int): Unit ={
    val out = new PrintWriter(new File("src/StockUpdate.csv"));
    val r = Orders(chosenOrder)
    StockSent.append(r)
    for (i<- StockSent){
      out.write( i.itemID + ','+i.itemName + ','+i.Stock + ','+i.Location(0) + '|'+i.Location(1) + '|'+i.Location(2) + ','+"\n")
    }
    out.close()
  }

}

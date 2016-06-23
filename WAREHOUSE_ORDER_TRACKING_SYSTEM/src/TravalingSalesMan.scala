import StockDeliveries._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by jonspyreas on 6/17/16.
  */

object TravalingSalesMan{
  def traval(Order: Array[String]) {
    var orderitems: ArrayBuffer[Item]= ArrayBuffer()
    currentStock
    val Stock: ArrayBuffer[Item] = StockList
    val x = Order(7).split("\\|")
    for( p <- x) {
      val y = p.split("\\:")
      println(y(0))
      val r = search(y(0), Stock)
      orderitems.append(r)
    }
    for(x<-orderitems){
      var loc: Array[Int]=Array(0,0)
      if(loc(0) <= x.Location(0).toString.toInt && loc(1) <= x.Location(1).toString.toInt){

      }else  if(loc(0) <= x.Location(0).toString.toInt && loc(1) <= x.Location(1).toString.toInt){

      }
    }

  }

}
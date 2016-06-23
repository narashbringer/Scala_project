import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import java.io._

import scala.annotation.switch
import scala.io.StdIn._


object orderPrint {
  var Orders:ArrayBuffer[Array[String]]= ArrayBuffer()
  def readFile(): Unit ={
    val bufferedSource = io.Source.fromFile("src/Incoming orders") //opens the file of incoming orders to be packed
    for (line <- bufferedSource.getLines) {
      Orders.append(line.split(","))   // brakes apart the csv into separate strings
    }
    bufferedSource.close
  }
  def printOrder(Orders:ArrayBuffer[Array[String]]): Unit ={
    for(i <- Orders) {    //prints out each of the lines formatted nicely
      println("%s,%s,%s,%s,%s,%s,%s,%s,%s".format(i(0), i(1), i(2), i(3), i(4), i(5), i(6), i(7), i(8)))
    }
  }
  def selectOrderAndUpdate(ordernum: Int, orderstat: String){
    if(ordernum < Orders.size){   // prints out and format's the updated order for printing
      Orders(ordernum)(8) = orderstat
      println(Orders(ordernum)(0)+","+Orders(ordernum)(1)+
        ","+Orders(ordernum)(2)+","+Orders(ordernum)(3)+
        ","+Orders(ordernum)(4)+","+Orders(ordernum)(5)+
        "," +Orders(ordernum)(6)+","+Orders(ordernum)(7)+
        "," + orderstat)
    }else { // if the order number does not exist I don't know
      scala.io.StdIn.readLine()
    }
  }
  def writetoFile(Orders: ArrayBuffer[Array[String]]): Unit ={
    val out = new PrintWriter(new File("order.csv")); //writes the new version of the csv orders to a new csv
    for(i<- Orders){
     for(j<- i){
        out.write(j+',')
      }
      out.write("\n")
    }
     out.close()
  }
  def printMenu(): Unit ={
    println("Menu chose your adventure")
    println("1: Print Orders")
    println("2: Add Stock")
    println("3: select order and update")
    println("4: mail Accounts an update")
    println("5: notify accounts of new Stock")
    println("6: check item location")
    println("7: root to take between items")
  }
  var Users: ArrayBuffer[WareHouseWorker]= ArrayBuffer()
  def users(): Unit ={
    val bufferedSource = io.Source.fromFile("src/Accounts") //opens the file of user Accounts to be packed
    for (line <- bufferedSource.getLines) {
      val x = line.split(",")
      val p = new WareHouseWorker(x(0).toInt,x(1),x(2),x(3).toInt,x(4))
       Users.append(p) // brakes apart the csv into separate strings
    }
    bufferedSource.close
  }
  def login(Users: ArrayBuffer[WareHouseWorker],username: String,password: String): Boolean={
    if(Users.head.Name == username && password == Users.head.password) {
      false
    }else{
      login(Users.tail,username,password)
    }
  }

  def main(args: Array[String]) {
    readFile()
    println("login")
    users()
    print("username")
    var username = readLine()
    println()
    print("password")
    var password = readLine()
    var loged: Boolean = login(Users,username,password)
    while(loged == false){
      printMenu()
      var choice= scala.io.StdIn.readInt()
      while(choice==1 || choice==2 ||choice ==3|| choice==4 || choice==5 || choice==6 || choice==7 ) {
        val x = (choice: @switch) match{
         case 1 =>
           printOrder(Orders)
          case 2 =>
           do {
              println("Add supply order Item Name, New Stock Number, Supplyer, Item Number, is it a new Item true/false")
              StockDeliveries.addStockOrder(readLine(), readInt(), readLine(), readInt(), readBoolean())
              println("next order? y/n")
           }while('y'.equals(readChar()))
         case 3 =>
           println("select order number to update and take")
           val chosenOrder = scala.io.StdIn.readInt()
           updateStock.decormentStock(Orders, chosenOrder)
           selectOrderAndUpdate(chosenOrder, scala.io.StdIn.readLine())
            writetoFile(Orders)
          case 4=>
            println("select order number to send to accounts")
            val chosenOrder = scala.io.StdIn.readInt()
            notifyAccounts.readFile()
            notifyAccounts.mail(Orders, chosenOrder )
          case 5=>
            println("select stock Order to send to accounts by Item ID")
            val chosenOrderID = scala.io.StdIn.readInt()
            notifyAccounts.readStockFile()
            //notifyAccounts.mailStock(notifyAccounts.StockSent,  )
          case 6=>
            StockDeliveries.currentStock
            println("select item to locate")
            val x = readLine()
            val p: Item = StockDeliveries.search(x,StockDeliveries.StockList)
            println(p.Location(0)+" "+ p.Location(1)+" "+ p.Location(2))
          case 7 =>
            readFile()
            println("chose order to pick root for")
            TravalingSalesMan.traval(Orders(readInt()))
        }

        printMenu()
        choice =scala.io.StdIn.readInt()
      }
      println("logout? t/f")
      loged= readBoolean()

    }
  }

}


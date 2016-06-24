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
  def printOrders(Orders:ArrayBuffer[Array[String]]): Unit ={
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
    println("\nchose number of option you want input any other number to exit")
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
    def input(): Int={
      val c : Int = readInt()
      c
    }
    readFile()
    println("login")
    users()
    print("username")
    val username = readLine()
    println()
    print("password")
    val password = readLine()
    var loged: Boolean = login(Users,username,password)
    while(loged == false){
      printMenu()
        MenuChocies.menu(input(),Orders)
      }
      println("logout? t/f")
      loged= readBoolean()

    }


}


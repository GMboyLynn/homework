package com.ruozedata

object implicitApp {
  def main(args: Array[String]): Unit = {
    val one=new tea("奶茶")
    implicit def tea2milkTea(one:tea):milkTea=new milkTea(one.name)
    one.speak
  }
  class tea(val name:String)
  class milkTea(val name:String) {
    def speak {
      println(s"$name:我们不一样")
    }
  }
}

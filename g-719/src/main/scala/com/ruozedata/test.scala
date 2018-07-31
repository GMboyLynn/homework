package com.ruozedata

object test {
  def main(args: Array[String]): Unit = {
    def SayChineseName:PartialFunction[String,String]={
      case "烙痕" =>"Lynn"
      case "老师" =>"Teacher"
      case "学生" =>"Student"
    }
    println(SayChineseName("烙痕"))
  }
}
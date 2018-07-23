package com.ruozedata
import scala.io.Source

object wordCount {
  def main(args: Array[String]): Unit = {
    val b = Source.fromFile("""G:\bigdata\[201805发布版]若泽数据-零基础大数据第4期\软件\IDEA_workspace\g-719\src\main\scala\com\ruozedata\wcdata.txt""").getLines().toList.flatMap ( _.split("\t") )
      .map ((_, 1) ).groupBy(_._1).mapValues(_.map((_._2)).reduce(_ + _))
      println(b)
  }
}

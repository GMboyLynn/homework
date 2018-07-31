package com.ruozedata
import scala.io.Source
import java.io._

object wordCount {
  def main(args: Array[String]): Unit = {
    val b = Source.fromFile("""G:\bigdata\[201805发布版]若泽数据-零基础大数据第4期\软件\IDEA_workspace\g-719\src\main\scala\com\ruozedata\wcdata.txt""").getLines().toList.flatMap ( _.split("\t") )
      .map ((_, 1) ).groupBy(_._1).mapValues(_.map((_._2)).reduce(_ + _))
      println(b)

    val writer=new PrintWriter("G:\\bigdata\\[201805发布版]若泽数据-零基础大数据第4期\\软件\\IDEA_workspace\\g-719\\src\\main\\scala\\com\\ruozedata\\wcResult.txt")
    writer.write(b.toString())
    writer.flush()
    writer.close()
  }
}

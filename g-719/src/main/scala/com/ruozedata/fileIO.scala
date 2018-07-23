package com.ruozedata
import java.io.RandomAccessFile
import scala.collection.mutable.ArrayBuffer
object fileIO {
  var randomnum=ArrayBuffer("00","01","02","03","04","05","06","07","08","09")
  var traffic=ArrayBuffer("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z")

  def main(args: Array[String]): Unit = {
    val randomFile = new RandomAccessFile("G:\\bigdata\\[201805发布版]若泽数据-零基础大数据第4期\\软件\\IDEA_workspace\\g-719\\src\\main\\scala\\com\\ruozedata\\outputFile.txt","rw")
    val fileLength = randomFile.length; //得到文件长度
    randomFile.seek(fileLength);//指针指向文件末尾
    for(a<- 10 until 60)
      randomnum+=""+a
    for(b<- -10000 to 20000)
      traffic+=""+b
    for(i<- 0 to 75000) {
      randomFile.writeBytes(getlinecont().toString()); //写入数据
    }
    randomFile.close();
  }
  def getlinecont():StringBuilder={
    var linecontent=new StringBuilder()
    val doman = Array("www.ruozedata.com","www.zhibo8.com","www.dongqiudi.com")
    linecontent.append(doman((new util.Random).nextInt(2))+"\t")
    linecontent.append(traffic((new util.Random).nextInt(traffic.size))+"\t")
    var day=(new util.Random).nextInt(30)
    var hour=(new util.Random).nextInt(24)
    var min=(new util.Random).nextInt(60)
    var sec=(new util.Random).nextInt(60)
    var time="[2018-07-"+randomnum(day+1)+" "+randomnum(hour)+":"+randomnum(min)+":"+randomnum(sec)+"]"
    linecontent.append(time+"\n")
    linecontent
  }
}

package com.ruozedata.zk

import org.apache.curator.retry.RetryNTimes
import org.apache.curator.framework.CuratorFrameworkFactory
import scala.collection.JavaConversions._
import org.slf4j.LoggerFactory

object Curator_zk {
  private val logger = LoggerFactory.getLogger(Curator_zk.getClass)
  val OffsetPath = s"/zktest"
  val client = CuratorFrameworkFactory.newClient("192.168.137.252:2181", new RetryNTimes(10,5000))

  def main(args: Array[String]): Unit = {
    client.start()
    logger.warn("zk client status:{}",client.getState)

    val ZK_PATH = "/zktest"
//    val data1 = "hello"
//    print("create", ZK_PATH, data1)
//    client.create.creatingParentsIfNeeded.forPath(ZK_PATH, data1.getBytes)

    val offsets : Map[TopicAndPartition, Long] = Map(TopicAndPartition("web",1) ->4444, TopicAndPartition("web",2)->2222 )
    storeOffsets(offsets)

    val topicSet = Set("web")
    val offsetstoMap:Map[TopicAndPartition, Long]= obtainOffsets(topicSet,Map(TopicAndPartition("web",1) ->0))
    offsetstoMap.keySet.foreach(line=>{
      val  topicName = line.topic
      val  topicPartition = line.partition
      val data = client.getData.forPath(s"$OffsetPath/$topicName/$topicPartition")
      val offset = java.lang.Long.valueOf(new String(data)).toLong
      println("偏移量读取: "+"路径"+s"$OffsetPath/$topicName/$topicPartition"+"的值为:"+ offset)
    })
  }

//  /consumers/G301/offsetsze_offset_topic/partition/0
//  /consumers/G301/offsetsze_offset_topic/partition/1
//  /consumers/G301/offsetsze_offset_topic/partition/2

  def obtainOffsets(topicSet: Set[String], defaultOffset: Map[TopicAndPartition, Long])
  : Map[TopicAndPartition, Long] = {

    // TODO...
    val offsets = for {
      //t就是路径webstatistic/Offsets下面的子目录遍历
      t <- client.getChildren.forPath(OffsetPath)
      if topicSet.contains(t)
      //p就是新路径   /webstatistic/Offsets/donews_website
      p <- client.getChildren.forPath(s"$OffsetPath/$t")
    } yield {
      //遍历路径下面的partition中的offset
      val data = client.getData.forPath(s"$OffsetPath/$t/$p")
      //将data变成Long类型
      val offset = java.lang.Long.valueOf(new String(data)).toLong
      (TopicAndPartition(t, Integer.parseInt(p)), offset)
    }
    defaultOffset ++ offsets.toMap
  }

  case class TopicAndPartition(topic:String, partition:Int)

  def storeOffsets(offsets: Map[TopicAndPartition, Long]): Unit = {
    // TODO...
    val OffsetPath = s"/zktest"
    if (client.checkExists().forPath(OffsetPath) == null) {
      client.create().creatingParentsIfNeeded().forPath(OffsetPath)
    }
    for ((tp, offset) <- offsets) {
      val data = String.valueOf(offset).getBytes
      val path = s"$OffsetPath/${tp.topic}/${tp.partition}"
      if(client.checkExists().forPath(path) == null)
        client.create().creatingParentsIfNeeded().forPath(path)
      client.setData().forPath(path, data)
      println("偏移量储存成功")
    }
  }

}

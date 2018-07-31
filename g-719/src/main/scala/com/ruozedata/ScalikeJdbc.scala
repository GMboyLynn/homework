package com.ruozedata
import scalikejdbc._
import scalikejdbc.config._

case class User(id: Int,name: String, age: Int)
object ScalalikeJdbc {
  def main(args: Array[String]): Unit = {
    scalikejdbc.config.DBs.setupAll()

    val userList:List[User] = List(User(1,"Lynn",20),User(2,"zhangsan",19),User(3,"lisi",17))
    println("新增数据:"+batchSave(userList))
    println("查询数据:")
    val users1 = select()
    for (user <- users1){
      println("id:"+user.id +" name:"+user.name+" age:"+user.age)
    }
    println("更新id：1的年龄:"+update(10,1))
    val users2 = select()
    for (user <- users2){
      println("id:"+user.id +" name:"+user.name+" age:"+user.age)
    }
    println("删除id:1:"+deleteByID(1))
    println("删除id:2:"+deleteByID(2))
    println("删除id:3:"+deleteByID(3))
    DBs.closeAll()

  }

  def deleteByID(id:Int) = {
    DB.autoCommit { implicit session =>
      SQL("delete from user where id = ?").bind(id).update().apply()
    }
  }

  def update(setage:Int,pid:Int) {
    DB.autoCommit { implicit session =>
      SQL("update user set age = ? where id = ?").bind(setage, pid).update().apply()
    }
  }

  def select():List[User] = {
    DB.readOnly { implicit session =>
      SQL("select * from user").map(rs => User(rs.int("id"), rs.string("name"), rs.int("age"))).list().apply()
    }
  }

  def batchSave(users:List[User]) :Unit= {
    DB.localTx { implicit session =>
      for (user<- users){
        SQL("insert into user(name,age,id) values(?,?,?)").bind(user.name, user.age, user.id).update().apply()
      }
    }
  }
}
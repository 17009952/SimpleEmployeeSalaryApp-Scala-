package ch.makery.address.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}

class Person ( nameS : String, empIDS: String, bankS: String, accountNoS: String, positionS: String, salaryS: Int){
  var name  = new StringProperty(nameS)
  var empID = new StringProperty(empIDS) 
  var bank = new StringProperty(bankS)
  var accountNo = new StringProperty(accountNoS)
  var position = new StringProperty(positionS)
  var salary = ObjectProperty[Int](salaryS)
  var bonus = ObjectProperty[Double](0)
}
 
class EntryLevelEngineer (n : String, id: String, b: String, a: String, p: String, s: Int) extends Person (n,id,b,a,p,s){
    bonus = ObjectProperty[Double](0.05)
}
 
class JuniorEngineer (n : String, id: String, b: String, a: String, p: String, s: Int) extends Person (n,id,b,a,p,s){
    bonus = ObjectProperty[Double](0.07)
}
 
class SeniorEngineer (n : String, id: String, b: String, a: String, p: String, s: Int) extends Person (n,id,b,a,p,s){
    bonus = ObjectProperty[Double](0.10)
}

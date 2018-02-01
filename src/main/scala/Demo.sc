object JavaDemo {
  val p = javapair.Pair.make(1, 2)                //> p  : <error> = Pair(1, 2)

  val f1 = p.first                                //> f1  : <error> = 1
  val s1 = p.second                               //> s1  : <error> = 2
  
  import scalapair._
  
  val p2 = Pair apply (1, 2)                      //> p2  : scalapair.Pair = Pair(1, 2)
  
  val x = p2.first                                //> x  : Int = 1
  
  val p3 = Pair(2, 3)                             //> p3  : scalapair.Pair = Pair(2, 3)
  
  val y = p3.second                               //> y  : Int = 3
  
  p2 + p3                                         //> res0: scalapair.Pair = Pair(3, 5)
  
  import scalaexpr._
  import scalaexpr.Expressions._
  
  val e = BinOp(Plus, Number(3), Number(0))       //> e  : scalaexpr.BinOp = BinOp(Plus,Number(3),Number(0))
  
  val e2 = BinOp(Div, BinOp(Times, Number(1), Number(6)),
                      BinOp(Plus, Number(2), Number(1)))
                                                  //> e2  : scalaexpr.BinOp = BinOp(Div,BinOp(Times,Number(1),Number(6)),BinOp(Plu
                                                  //| s,Number(2),Number(1)))
  
  Expressions.simplifyTop(e)                      //> res1: scalaexpr.Expression = Number(3)
  
  println(format(e2) + " = " + eval(e2))          //> 1 * 6 / (2 + 1) = 2
}
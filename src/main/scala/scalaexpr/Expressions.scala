package scalaexpr

sealed abstract class Expression

case class Number(value: Int) extends Expression
case class BinOp(op: Op, left: Expression, right: Expression) extends Expression

sealed abstract class Op
case object Plus extends Op
case object Minus extends Op
case object Times extends Op
case object Div extends Op

object Expressions {
  
  /** Evaluation */
  def eval(e: Expression): Int = e match {
    case Number(n) => n
    case BinOp(op, e1, e2) =>
      val v1 = eval(e1)
      val v2 = eval(e2)
      op match {
        case Plus => v1 + v2
        case Minus => v1 - v2
        case Times => v1 * v2
        case Div => v1 / v2
      }
  }
 
  /** Formating */
  def prec(e: Expression): Int = e match {
    case Number(_) => 0
    case BinOp(Times | Div, _, _) => 1
    case BinOp(Plus | Minus, _, _) => 2
  }
  
  def format(op: Op): String = op match {
    case Plus => " + "
    case Minus => " - "
    case Times => " * "
    case Div => " / "
  }
  
  def format(e: Expression): String = e match {
    case Number(n) => n.toString()
    case BinOp(op, e1, e2) =>
      def paren(left: Boolean, ep: Expression, e: Expression): String = {
        if (prec(ep) < prec(e) || !left && prec(ep) == prec(e)) {
          "(" + format(e) + ")" 
        } else {
          format(e)
        }
      }
      paren(true, e, e1) + format(op) + paren(false, e, e2)
  }

  /** Simplification */
  def simplifyTop(e: Expression): Expression = e match {
    case BinOp(Plus, e1, Number(0)) => e1
    case BinOp(Times, _, e2 @ Number(0)) => e2
    case BinOp(Times, e1, Number(1)) => e1
    case BinOp(Times, e1, e2) if e1 == e2 => BinOp(Times, Number(2), e1)
    case _ => e
  }
   
}
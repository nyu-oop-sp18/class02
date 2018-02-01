package scalapair

class Pair(val first: Int, val second: Int) {
  def this() {
    this(0, 0)
  }
  
  def setFirst(fst: Int): Pair = new Pair(fst, second)
  
  override def toString(): String = s"Pair($first, $second)"
  
  def +(other: Pair): Pair = new Pair(first + other.first, second + other.second)
}

object Pair {
  def apply(fst: Int, snd: Int) = new Pair(fst, snd)
}
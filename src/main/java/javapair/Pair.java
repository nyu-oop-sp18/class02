package javapair;

public class Pair extends Object {
  public final int first;
  public final int second;

  public Pair(int fst, int snd) {
    first = fst;
    second = snd;
  }
  
  public String toString() {
  	return "Pair(" + first + ", " + second + ")";
  }
  
  public static Pair make(int fst, int snd) {
  	return new Pair(fst, snd);
  }
}

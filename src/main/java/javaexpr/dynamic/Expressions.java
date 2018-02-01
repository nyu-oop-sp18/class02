package javaexpr.dynamic;

/** The abstract expression type */
abstract class Expression {}	

/** Number expressions */
class Number extends Expression {
		
  private final int value;
	    
  Number(int value) {
	  this.value = value;
  }
 
  int value() {
	  return value;
  }
}

/** The abstract operator type */
abstract class Op {}

/** The addition operator */
class Plus extends Op {}

/** The subtraction operator */
class Minus extends Op {}

/** The multiplication operator */
class Times extends Op {}

/** The division operator */
class Div extends Op {}

/** Binary operator expressions */
class BinOp extends Expression {

	private final Expression left;
	private final Op op;
	private final Expression right;

	BinOp(Expression left, Op op, Expression right) {
    this.left = left;
    this.op = op;
    this.right = right;
	}

	Expression left() {
	  return left;
	}

	Op op() {
	  return op;
	}

	Expression right() {
	  return right;
	}

}


/** For testing ... */
public class Expressions {	  
  public static int eval(Expression e) {
  	// Don't use this style. This is very inefficient!
  	if (e instanceof Number) {
  		return ((Number) e).value();
  	} else {
  		BinOp b = (BinOp) e;
  		Op op = b.op();
  		
  		int v1 = eval(b.left());
  		int v2 = eval(b.right());
  		
  		if (op instanceof Plus) {
  			return v1 + v2;
  		} else if (op instanceof Minus) {
  			return v1 - v2;
  		} else if (op instanceof Times) {
  			return v1 * v2;
  		} else {
  			return v1 / v2;
  		}
  	}
  }
	
  public static String format(Expression e) {  	
  	// Don't use this style. This is very inefficient!
  	if (e instanceof Number) {
  		return Integer.toString(((Number) e).value());
  	} else {
  		BinOp b = (BinOp) e;
  		Op op = b.op();
  		
  		String s1 = format(b.left());
  		String s2 = format(b.right());
  		
  		if (op instanceof Plus) {
  			return s1 + " + " + s2;
  		} else if (op instanceof Minus) {
  			return s1 + " - " + s2;
  		} else if (op instanceof Times) {
  			return s1 + " * " + s2;
  		} else {
  			return s1 + " / " + s2;
  		}
  	}
  }
  
	public static void main(String[] args) {
  	// Create an expression for 2 * 3 + 10 / 5.
	  Expression e =
	    new BinOp(new BinOp(new Number(2), new Times(), new Number(3)),
		            new Plus(),	  
			  	      new BinOp(new Number(10), new Div(), new Number(5)));

    // Print the expression, evaluate it, and print the result.
	  System.out.println(format(e) + " = " + eval(e));
  }
}

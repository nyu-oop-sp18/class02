package javaexpr.traditional;

/** The abstract expression type */
abstract class Expression {
	abstract int eval();
	
	abstract String format();
	
}	

/** Number expressions */
class Number extends Expression {
		
  private final int value;
	    
  Number(int value) {
	  this.value = value;
  }
 
  int value() {
	  return value;
  }

  int eval() {
  	return value();
  }
  
  String format() {
  	return Integer.toString(value());
  }
}

/** The abstract operator type */
abstract class Op {
  abstract int eval(Expression e1, Expression e2);
  
  abstract String format(Expression e1, Expression e2);
}

/** The addition operator */
class Plus extends Op {
	int eval(Expression e1, Expression e2) {
		return e1.eval() + e2.eval();
	}
	
	String format(Expression e1, Expression e2) {
		return e1.format() + " + " + e2.format();
	}
}

/** The subtraction operator */
class Minus extends Op {
	int eval(Expression e1, Expression e2) {
		return e1.eval() - e2.eval();
	}
	
	String format(Expression e1, Expression e2) {
		return e1.format() + " - " + e2.format();
	}
}

/** The multiplication operator */
class Times extends Op {
	int eval(Expression e1, Expression e2) {
		return e1.eval() * e2.eval();
	}
	
	String format(Expression e1, Expression e2) {
		return e1.format() + " * " + e2.format();
	}
}

/** The division operator */
class Div extends Op {
	int eval(Expression e1, Expression e2) {
		return e1.eval() / e2.eval();
	}
	
	String format(Expression e1, Expression e2) {
		return e1.format() + " / " + e2.format();
	}
}

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

	int eval() {
  	return op().eval(left(), right());
  }
	
	String format() {
		return op().format(left(), right());
	}
}


/** For testing ... */
public class Expressions {	  
  public static int eval(Expression e) {
  	return e.eval();
  }
	
  public static String format(Expression e) {
  	return e.format();
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

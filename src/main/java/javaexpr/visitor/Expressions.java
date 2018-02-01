package javaexpr.visitor;

/** The Visitor class representing a generic operation on expressions */
abstract class Visitor<T> {
	abstract T visit(Number number);
	
	T visit(BinOp binop) {
		return binop.op().dispatch(this, binop.left(), binop.right());
	}
	
	abstract T visit(Plus op, Expression e1, Expression e2);
	abstract T visit(Minus op, Expression e1, Expression e2);
	abstract T visit(Times op, Expression e1, Expression e2);
	abstract T visit(Div op, Expression e1, Expression e2);
}

/** The abstract expression type */
abstract class Expression {
  abstract <T> T dispatch(Visitor<T> visitor);
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

  <T> T dispatch(Visitor<T> visitor) {
  	return visitor.visit(this);
  }
  
}

/** The abstract operator type */
abstract class Op {
  abstract <T> T dispatch(Visitor<T> visitor, Expression e1, Expression e2);
}

/** The addition operator */
class Plus extends Op {
	<T> T dispatch(Visitor<T> visitor, Expression e1, Expression e2) {
		return visitor.visit(this, e1, e2);
	}
}

/** The subtraction operator */
class Minus extends Op {
	<T> T dispatch(Visitor<T> visitor, Expression e1, Expression e2) {
		return visitor.visit(this, e1, e2);
	}
}

/** The multiplication operator */
class Times extends Op {
	<T> T dispatch(Visitor<T> visitor, Expression e1, Expression e2) {
		return visitor.visit(this, e1, e2);
	}
}

/** The division operator */
class Div extends Op {
	<T> T dispatch(Visitor<T> visitor, Expression e1, Expression e2) {
		return visitor.visit(this, e1, e2);
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

	<T> T dispatch(Visitor<T> visitor) {
  	return visitor.visit(this);
  }
}

/** A visitor for evaluating expressions */
class Evaluator extends Visitor<Integer> {
	
  Integer visit(Number e) { return e.value(); }

  Integer visit(Plus op, Expression e1, Expression e2) {
	  return e1.dispatch(this) + e2.dispatch(this);
  }
  
  Integer visit(Minus op, Expression e1, Expression e2) {
  	return e1.dispatch(this) - e2.dispatch(this);
  }
  
  Integer visit(Times op, Expression e1, Expression e2) {
  	return e1.dispatch(this) * e2.dispatch(this);
  }
  
  Integer visit(Div op, Expression e1, Expression e2) {
  	return e1.dispatch(this) / e2.dispatch(this);
  }
}

/** A visitor for formating expressions */
class Formatter extends Visitor<Void> {
	
	StringBuffer buffer = new StringBuffer();
	
  Void visit(Number e) { 
  	buffer.append(e.value());
  	return null;
  }

  Void visit(Plus op, Expression e1, Expression e2) {
	  e1.dispatch(this);
	  buffer.append(" + ");
	  e2.dispatch(this);
	  return null;
  }
  
  Void visit(Minus op, Expression e1, Expression e2) {
	  e1.dispatch(this);
	  buffer.append(" - ");
	  e2.dispatch(this);
	  return null;
  }
  
  Void visit(Times op, Expression e1, Expression e2) {
	  e1.dispatch(this);
	  buffer.append(" * ");
	  e2.dispatch(this);
	  return null;
  }
  
  Void visit(Div op, Expression e1, Expression e2) {
	  e1.dispatch(this);
	  buffer.append(" / ");
	  e2.dispatch(this);
	  return null;
  }
  
  public String toString() {
  	return buffer.toString();
  }
}

/** For testing ... */
public class Expressions {	  
  public static int eval(Expression e) {
  	return e.dispatch(new Evaluator());
  }
	
  public static String format(Expression e) {
  	Formatter f = new Formatter();
  	e.dispatch(f);
    return f.toString();
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

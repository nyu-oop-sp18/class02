# Class 2

## Classes and Objects

In the previous class, we have learned about the basic language features of
Scala. In this class, we will learn how Scala programs are organized.
Scala is an object-oriented language, so Scala programs are organized
using classes and objects.

### Classes, Fields, and Methods

Similar to Java, Scala allows you to define classes with fields and
methods, which you can extend using inheritance, override,
etc. Fortunately, Scala's syntax for classes is much more lightweight
than Java's. For example, consider the following Java class which we
can use to wrap pairs of integer values in a single object:

```java
public class Pair {
  private int first;
  private int second;

  public Pair(int fst, int snd) {
    first = fst;
    second = snd;
  }
	
  public int first() {
    return first;
  }
	
  public int second() {
    return second;
  }
}  
```

The class consists of:

* two *fields* called `first` and `second` of type `int` to store the
  two values;

* a *constructor*, which takes values to initialize the two fields;

* two *getter methods* to retrieve the two values (we follow good
  practice and declare all non-final fields as private so that their
  values cannot be modified without explicit method calls.).


Here is how we can define the corresponding class in Scala (let us
ignore for the moment that we can represent pairs much more easily
using tuples):

```scala
class Pair(val first: Int, val second: Int)
```

There are some important differences between the Java and Scala
version of the class `Pair`:

* In Scala, the class name is followed by a list of class
  parameters. These parameters serve two purposes:
  
  1. Parameters that are prefixed by a `val` or `var` keyword
     automatically create a field in the class with the given name and
     type.

  1. The parameter list implicitly defines a *primary* constructor
     with a corresponding list of arguments. The values that are
     provided for arguments prefixed with `val` or `var` will be used
     to initialize the associated fields.
    
* The default visibility of classes, fields, and methods in Scala is
  `public`. Hence, we can access the values of the fields `first` and
  `second` directly and we do not need to define extra getter
  methods. Note that the public default visibility makes sense because
  Scala discourages mutable state. In particular, we defined the two
  fields as `val`s, so their values cannot be changed, once
  an instance of class `Pair` has been created.  In Scala,
  you can leave out the braces around an empty class body, so
  `class C` is the same as `class C {}`.

We can create instances of class `Pair` and access their
fields as usual:

```scala
scala> val p = new Pair(1,2)
p: Pair = Pair@1458e1cc
scala> p.first
res0: Int = 1
```

What if we do want to modify the values stored in a `Pair`
object? In Java, we would do this by adding appropriate *setter methods*
to the class:

```java
public class Pair {
  private int first;
  private int second;

  ...

  public void setFirst(int fst) {
    first = fst;
  }
  public void setSecond(int snd) {
    second = snd;
  }
}
```

In Scala, we could follow the same route: change all `val`s
into `var`s, make them private, and add getter and setter
methods. However, we want to avoid using `var` declarations
as much as possible. The idiomatic solution in Scala is to make a copy
of the entire object and change the appropriate value:

```scala
class Pair(val first: Int, val second: Int) {
  def setFirst(fst: Int): Pair = new Pair(fst, second)
  def setSecond(snd: Int): Pair = new Pair(first, snd)
}
```

### Secondary Constructors

Secondary constructors are defined like ordinary methods within the
class body using the dedicated keyword `this` as the name of the
constructor method. For instance, the suppose we want to add a
secondary no-argument constructor that default initializes the
components of a pair. Then we can do this as follows:

```scala
class Pair(val first: Int, val second: Int) {
  ...
  def this() {
    this(0, 0)
  }
}
```

```scala
scala> val p = new Pair()
p: Pair = Pair@1458e1cc
scala> p.first
res1: Int = 0
```

### Overriding Methods

Java allows us to override methods that are declared in super
classes. Since method calls are dynamically dispatched at run-time,
this feature allows us to modify the behavior of an object of the
subclass when it is used in a context where an object of the super
class is expected.

All Java classes extend the class `Object`. The class
`Object` provides, among others, a method
`toString`, which computes a textual representation of the
object. In particular, the `toString` method can be used to
pretty-print objects. By default, the textual representation of
objects consists of the name of the object's class, followed by a
unique object ID. We can modify the way objects of a specific class
are printed, by overriding the `toString` method. In Java,
this can be done as follows:

```java
public class Pair {
  private int first;
  private int second;
  ...
  public String toString() {
    return "Pair(" + first + ", " + second + ")";
  }
}
```

In Scala, all classes extend the class `scala.Any` which
also provides a method called `toString`. Scala's class
hierarchy is further subdivided into the classes
`scala.AnyVal` and `scala.AnyRef`, which are
directly derived from `scala.Any`. All instances of
`scala.AnyVal` are immutable, whereas instances of
`scala.AnyRef` may have mutable state. That is,
`scala.AnyRef` corresponds to Java's `Object` class.

If we want to override a method in a Scala class, we have to
explicitly say so by using the `override` qualifier:

```scala
class Pair(val first: Int, val second: Int) {
  ...
  override def toString() = "Pair(" + first + ", " + second + ")"
}
```

The pretty printer in the REPL will now use the new
`toString` method to print `Pair` objects:

```scala
scala> val p = new Pair(1,2)
p: Pair = Pair(1, 2)
```

### Singleton and Companion Objects

It is often useful to declare factory methods that simplify the
construction of objects, which involve complex initialization code. In
Java, we would declare such methods as static members of the
corresponding class:

```java
public class Pair {
  ...
  public static Pair make(int fst, int, snd) {
    return new Pair(fst, snd);
  }
}
```

We can now call `Pair.make` to create new `Pair` instances.

Scala does not support static methods as they violate the philosophy
of object-oriented programming. Instead, it provides *singleton
objects*. Singleton objects are declared just like classes, but using
the keyword `object` instead of `class`. There exists exactly one
instance of each `object`, which is automatically created from the
`object` declaration when the program is started. Hence, unlike class
declarations, object declarations cannot have parameter lists.

A singleton object whose name coincides with the name of another class
`C` is called the *companion object* of `C`. Companion objects have
access to all private members of instances of `C`. Consequently, a
method or field that is defined in the companion object is
conceptually equivalent to a static method/field of `C` in Java:

```scala
class Pair(val first: Int, val second: Int) {
  ...
}
object Pair {
  def make(fst: Int, snd: Int) = new Pair(fst, snd)
}
```

We can access members of companion objects just like static class
members in Java:

```scala
scala> def p = Pair.make(3,4)
p: Pair = Pair(3, 4)
```

### The `apply` Method

Methods with the name `apply` are treated specially by the
compiler. For example, if we rename the factory method
`make` in our companion object for the `Pair`
class to `apply`

```scala
object Pair {
  def apply(fst: Int, snd: Int) = new Pair(fst, snd)
}
```

then we can call this method simply by referring to the
`Pair` companion object, followed by the argument list of
the call:

```scala
scala> def p = Pair(3,4)
p: Pair = Pair(3, 4)
```

This is equivalent to the following explicit call to the
`apply` method:

```scala
scala> def p = Pair.apply(3,4)
p: Pair = Pair(3, 4)
```

The compiler automatically expands `Pair(3,4)` to `Pair.apply(3,
4)`. That is, objects with an `apply` method can be used as if they
were functions (Using `apply` methods in Scala is, thus, similar to
overloading the function call operator `()` in C++.). This feature is
particularly useful to enable concise calls to factory methods. In
fact, factory methods for the data structures in the Scala standard
library are typically implemented using `apply` methods in companion
objects.


### Algebraic Data Types

Algebraic data types and pattern matching are constructs that are
commonly found in functional programming languages. They allow you to
implement regular, non-encapsulated data structures (such as lists and
trees) in a convenient fashion. We will make heavy use of this feature
in some of our projects.

#### Case Classes

Suppose we want to implement a simple calculator
program that takes arithmetic expressions such as 

``` (3 + 6 / 2) * 5 ```

as input and evaluates these expressions. This problem is quite
similar to writing an interpreter for a programming language, except
that the language that we are interpreting is much simpler.

One of the first question that we have to answer is: how do we
represent expressions in our program? Our representation should allow
us to easily implement common tasks such as pretty printing,
evaluation, and simplification of expressions. In particular, the
representation should make the precedence of operators in expressions
explicit. E.g., consider the expression `3 + 6 / 2`, then when we
evaluate the expression, our representation should immediately tell us
that we first have to divide `6` by `2` before we add `3`. To achieve
this, expressions are represented as *abstract syntax trees*, or
ASTs for short. For example, the abstract syntax tree of the
expression `(3 + 6 * 2) * 5` can be visualized as follows:

```
       *
      / \
     +   5
    / \
   3   *
      / \
     6   2
```
  
Note that the AST tells us exactly how to evaluate the expression. We
start at the root. At each node that we visit, we first recurse into
the left subtree to evaluate the corresponding subtree. Then we do the
same for the right subtree. Finally, we combine the results according
to the operation labeling the current node. 

Algebraic data types allow us to represent tree-like data structures
such as ASTs. In Scala, algebraic data types are constructed using
*case classes*. The following case classes define the ASTs of our
arithmetic expressions:

```scala
abstract class Expression
/* Numbers such as 1, 2, etc. */
case class Number(num: Int) extends Expression 
/* Expressionessions composed using binary operators */
case class BinOp(op: Op, left: Expression, right: Expression) extends Expression
/* Binary operators */
abstract class Op
case object Plus extends Op /* + */
case object Minus extends Op /* - */
case object Times extends Op /* * */
case object Div extends Op /* / */
```

The Scala compiler adds some convenient functionality to the case
classes. First, it automatically generates companion objects with
appropriate factory methods. These methods are particularly useful
when you nest them to construct complex expressions:

```scala
scala> val e = BinOp(Plus, Number(3), BinOp(Times, Number(4), Number(5)))
e: BinOp = BinOp(Plus, Number(3), BinOp(Times, Number(4), Number(5))) 
```

Second, the compiler adds natural implementations of the methods
`toString`, `hashCode`, and `equals` to
case classes. They will print, hash, and compare a whole tree
consisting of the top-level case class instance and (recursively) all
its arguments. In Scala, an expression of the form `x == y`
always translates into a call of the form `x.equals(y)`
(just like in Java).  The overriden `equals` method
therefore ensures that case class instances are always compared
structurally. For example, we have:

```scala
scala> val e1 = BinOp(Plus, Number(3), Number(4))
e1: BinOp = BinOp(Plus, Number(3), Number(4))
scala> val e2 = BinOp(Plus, Number(3), Number(4))
e2: BinOp = BinOp(Plus, Number(3), Number(4))
scala> e1 == e2
res2: Boolean = true
```

Note that in the example above, `e1` and `e2`
point to two different objects in memory. However, the two ASTs
represented by these objects have exactly the same structure. Hence,
`e1 == e2` evaluates to `true`. If you want to compare two objects for
reference equality instead (i.e., that two object references point to the
same object in memory), you can use the method `eq`. For instance we have

```scala
scala> e1 eq e2
res3: Boolean = false
scala> val e3 = e2
e3: BinOp = BinOp (Plus, Number(3), Number(4))
scala> e2 eq e3
res4: Boolean = true
```

Next, all arguments in the parameter list of a case class implicitly
get a `val` prefix, so they are maintained as fields:

```scala
scala> val n = e1.left
n: Number = Number(3)
scala> n.num
res3: Int = 3
```

Finally, the compiler adds a copy method to your case classes for
making modified copies. This method is useful if you need to create a
new case class object that is identical to another case class object except that
some of its attributes are different:

```scala
scala> e1.copy(op = Minus)
res4: BinOp(Minus, Number(3), Number(4))
```

#### Pattern Matching

Suppose we want to implement an algorithm that simplifies expressions
by recursively applying the following simplifications rules:

* `e + 0 => e`
* `e * 1 => e`
* `e * 0 => 0`

To identify whether a given expression matches one of the left-hand
sides of the rules, we have to look at some of its
subexpressions. E.g., to check whether an expression of the form `e_1
+ e_2` matches the left-hand side of the first rule, we have to look
at the left subexpression `e_1` to check whether `e_1 =
0`. Implementing this kind of pattern matching is quite tedious in
many languages (including Java). Fortunately, the Scala language has
inbuilt support for pattern matching that works hand-in-hand with case
classes.

Let us first reformulate the three simplification rules in
terms of our case class representation of expressions:

```scala
  BinOp(Plus, e, Number(0)) => e
  BinOp(Times, e, Number(1)) => e
  BinOp(Times, e, Number(0)) => Number(0)
```
Using pattern matching, these rules almost directly give us the
implementation of the following function `simplifyTop`, which applies
the rules at the top-level of the given expression `e`:

```scala
def simplifyTop(e: Expression) = e match {
  case BinOp(Plus, e1, Number(0)) => e1
  case BinOp(Times, e1, Number(1)) => e1
  case BinOp(Times, _, Number(0)) => Number(0)
  case _ => e
}
```

The body of `simplifyTop` is a *match expression*. A
match expression consists of a *selector*, in this case `e`,
followed by the keyword `match`, followed by a sequence of
*match alternatives* enclosed in braces.

Each alternative starts with the keyword `case`, followed by a pattern,
followed by an expression that is evaluated if the pattern matches the
selector. The pattern and expression are separated by an arrow symbol
`=>`.

A match expression is evaluated by checking whether the selector
matches one of the patterns in the alternatives. The patterns are
tried in the order in which they appear in the program. The first pattern that
matches is selected and the expression following the arrow is evaluated. The
result of the entire match expression is the result of the expression
in the selected alternative.

Here is an example of a recursive function that uses pattern matching
to pretty print arithmetic expressions:

```scala
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
    def paren(ep: Expression, e: Expression): String = {
      if (prec(ep) < prec(e)) "(" + format(e) + ")" else format(e)
    }
    paren(e, e1) + format(op) + paren(e, e2)
}

scala> val e = BinOp(Minus, BinOp(Times, Number(3), 
                            BinOp(Plus, Number(2), Number(2))), Number(1))
e: BinOp = BinOp(Plus, BinOp(Times, Number(3), BinOp(Plus, Number(2), Number(2))), Number(1))
scala> format(e)
res0: String = 3 * (2 + 2) - 1
```

There are different types of patterns. The most important types are:

* **Constant patterns**: A constant pattern such as `0` matches
  values that are equal to the constant (with respect to `==`).

* **Variable patterns**: A variable pattern such as `e1` matches
  every value. Here, `e1` is a variable that is bound in the
  pattern. The variable refers to the matched value in the right-hand
  side of the case clause.

* **Wildcard patterns**: A wildcard pattern `_` also
  matches every value, but it does not introduce a variable that
  refers to the matched value.

* **Constructor patterns**: A constructor pattern such as
  `BinOp(Plus, e, Number(0))` matches all values of type
  `BinOp` whose first argument matches `Plus`,
  whose second argument matches `e`, and whose third
  argument matches `Number(0)`. Note that the arguments to the
  constructor `BinOp` are themselves patterns. This allows
  you to write deep patterns that match complex case class values
  using a concise notation.

* **Choice patterns**: A choice pattern such as `Plus | Times` matches
  all values that match `Plus` or `Times`.

#### Binding Names in Patterns

Sometimes we want to match a subexpression against a specific pattern
and also bind the matched expression to a name. This is useful when we
want to reuse a matched subexpression in the right-hand side of the
match alternative. For example, in the third simplification rule of
`simplifyTop` we are returning `Number(0)`, which is
also the second subexpression of the matched expression
`e`. Instead of creating a new expression,
`Number(0)` on the right-hand side of the rule, we can also
directly return the second subexpression of `e`. We can do
this by binding a name to that subexpression in the pattern using the
operator `@` as follows:

```scala
def simplifyTop(e: Expression) = e match {
  case BinOp(Plus, e1, Number(0)) => e1
  case BinOp(Times, e1, Number(1)) => e1
  case BinOp(Times, _, e2 @ Number(0)) => e2
  case _ => e
}
```

Note that the pattern in the third match alternative now binds the name
`e2` to the value matched by the pattern
`Number(0)`. This value is then returned on the righ-hand side
of the rule by referring to it using the name `e2`.

#### Pattern Guards

Suppose we want to extend our expression simplifier so that it
additionally implements the following simplification rule:

`e + e => 2 * e`

If we directly translate the rule to a corresponding match
alternative, we obtain the following implementation of `simplilfyTop`:

```scala
def simplifyTop(e: Expression) = e match {
  ...
  case BinOp(Plus, e1, e1) => BinOp(Times, Number(2), e1)
  case _ => e
}
```

Unfortunately, the compiler will reject this function because we use
the name `e1` twice within the same pattern. In general, a
variable name such as `e1` may only be used once in a
pattern. We can solve this problem by using a different variable name
for the second subexpression, say `e2`, and then use a
*pattern guard* to enforce
that the subexpressions matched by *e1* and *e2*
are equal:

```scala
def simplifyTop(e: Expression) = e match {
  ...
  case BinOp(Plus, e1, e2) if e1 == e2 => 
    BinOp(Times, Number(2), e2)
  case _ => e
}
```

In general, a pattern guard can be an arbitrary Boolean expression
over the names that are in the scope of the match alternative. The
pattern guard is appended to the pattern of a match alternative using
the keyword `if`.

#### Sealed Classes

Consider the following function `simplifyAll` that applies
our simplification rules recursively to the given expression:

```scala
def simplifyAll(e: Expression) = e match {
  case BinOp(Plus, e1, Number(0)) => simplifyAll(e1)
  case BinOp(Times, e1, Number(1)) => simplifyAll(e1)
  case BinOp(Times, _, e2 @ Number(0)) => e2
  case BinOp(Plus, e1, e2) if e1 == e2 => 
    BinOp(Times, Number(2), simplifyAll(e2))
  case BinOp(bop, e1, e2) => 
    BinOp(bop, simplifyAll(e), simplifyAll(e2))
}
```

Observe that in this function, the pattern alternatives are no longer
exhaustive. That is, there exist values `e` that are not
matched by any of the match alternatives, e.g., the value
`Number(0)`. If `simplifyAll` is called with
`Number(0)`, it will throw a runtime exception.

We can fix this code by adding an explicit match alternative for
the `Number` constructor:

```scala
def simplifyAll(e: Expression) = e match {
  case BinOp(Plus, e1, Number(0)) => simplifyAll(e1)
  case BinOp(Times, e1, Number(1)) => simplifyAll(e1)
  case BinOp(Times, _, e2 @ Number(0)) => e2
  case BinOp(Plus, e1, e2) if e1 == e2 => 
    BinOp(Times, Number(2), simplifyAll(e2))
  case BinOp(bop, e1, e2) => 
    BinOp(bop, simplifyAll(e), simplifyAll(e2))
  case Number(_) => e
}
```

Is this code safe or are there still values for `e` that are
not matched by any of the match alternatives? The answer is:
it depends. Scala allows us to further extend the abstract
class `Expression` by new case classes. In particular, we could
define a new case class

```scala
case class Var(name: String) extends Expression
```
in a different source file, but as part of the same package as the
abstract class `Expression`. Now calling `simplifyAll`
on, say, `Var("x")` would again yield a run-time exception.

We can prevent the extension of a class outside of the source file in
which it is defined by declaring the class as `sealed`. For our
expression type `Expression` this looks as follows:

```scala
sealed abstract class Expression
case class Number(num: Int) extends Expression
case class BinOp(bop: Op, left: Expression, right: Expression) extends Expression

sealed abstract class Op
case object Plus extends Op
case object Minus extends Op
case object Times extends Op
case object Div extends Op
```

Now, the code of `simplifyAll` guarantees that for every call
`simplifyAll(e)`, one of the patterns in the match expression in
`simplifyAll` will always match `e`. In fact, one
of the nice features of sealed classes is that the compiler checks
that pattern matching on sealed classes is exhaustive and warns us
if we forgot to handle a particular match case.

Unfortunately, these exhaustiveness checks can sometimes produce
spurious warnings. For example, suppose we have a function that is
meant to pretty print number expressions, but not other expressions
which have not yet been reduced:

```scala
def formatNumber(e: Expression): String =
  e match {
    case Number(num) => num.toString()
  }
```
Further suppose that we know that our program ensures that
`formatNumber` is never called on a `BinOp`
expression. Yet, the compiler still complains about the non-exhaustive
pattern matching. We can suppress this warning by declaring
`e` as `unchecked`:

```scala
def formatNumber(e: Expression): String =
  (e: @unchchecked) match {
    case Number(num) => num.toString()
  }
```

While `@unchecked` annotations are sometimes necessary to
suppress spurious warnings, you should be very careful about
introducing them in your code. In most cases, the compiler generated
warnings indicate actual problems in your code that need your
attention.

#### Option Types

Suppose we want to write a function that evaluates arithmetic
expressions to `Int` values. One question is: How should we deal with
undefined operations such as division by zero:

```scala
BinOp(Div, e, Number(0)) => ???
```

In Java, we would typically go for one of the following two solutions:

* throw an exception such as `ArithmeticException`;
* return `null` to indicate that the intended operation
  does not yield a valid result.

Both approaches have advantages and drawbacks.

Exceptions are a good solution if the undefined operation is indeed
exceptional behavior that should, e.g., abort the program. In this
case, we ensure that a computation that returns normally always yields
a valid result. However, if the undefined operation commonly occurs in
computations, we will have to catch the exception and handle it
appropriately. This has two disadvantages. First, the exception
mechanism is relatively expensive and should only be used in truly
exceptional situations. Second, the exception handlers will clutter
the code and the non-structured control-flow of thrown exceptions make
it more difficult to understand what the program is doing.

If we return `null`, we avoid the two disadvantages of
exceptions: the computation always returns normally, and there is no
computational overhead such as recording the stack-trace to the point
where the exception was thrown. However, `null` values
introduce their own problems. Since `null` can have an
arbitrary type, the type checker of the compiler will give us much
weaker static correctness guarantees for our code. In particular, it
will be unable to statically detect unintended accesses to the return
value in cases where the return value is invalid (hello
`NullPointerException`!).

In languages that support pattern matching, there is a common idiom
that avoids the problem of introducing `null` values: *option types*.

The option type is an algebraic data type with two variants:
`Some(v)` to indicate that a computation returned a proper
result value `v`, and `None` to indicate that the
intended operation was undefined and has no proper result.

In Scala, we can define an option type for `Int` values
using case+ classes as follows:

```scala
sealed abstract class IntOption
case class Some(value: Int) extends IntOption
case object None extends IntOption
```

We can now use the option type similarly to `null` values in Java:

```scala
def div(x: Int, y: Int): IntOption =
  if (y == 0) None else Some(x / y)
```

Unlike in Java, where the static type checker is unable to distinguish
a `null` value from a genuine result of a computation, the
Scala type checker will force us to explicitly unwrap the `Int` value
embedded in an `IntOption` before we can access it. Using
pattern matching, we can do this conveniently. For example, suppose we
want to convert the result of `div` to a double precision
floating point number. By using pattern matching on the return value
of `div`, we can recover from some of the cases where
integer division by 0 is undefined: 

```scala
def divToDouble(x: Int, y: Int): Double = 
  div(x,y) match {
    case Some(x) => x
    case None =>
      if (x < 0) Double.NegativeInfinity
      else if (x > 0) Double.PositiveInfinity
      else Double.NaN
  }
```

Since option types are so useful, Scala already provides a generic
option type, called `Option`, in its standard library. Using
the predefined type `Option` we can write the function
`div` like this:

```scala
def div(x: Int, y: Int): Option[Int] =
  if (y == 0) None else Some(x / y)
```

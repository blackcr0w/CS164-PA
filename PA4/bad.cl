(* primitives test *)
class APrimitives{
    x:Int <- "one";
    y:Bool <- 1;
    z:String <- true;
 };

(* bad method expression type *)
class ADispatch{
     moo() : Bool  {
       zoo() (* bad type *)
     };

     zoo(): Int{
        5
     };

};

(* test conditionals *)
class ACond{
    y:String <- if true then new Object else 2 fi; (* lub type test *)

};

(* while test *)
class ALoop{
    x:Int <- while false loop 1 pool; (* while type test *)
};

(* block test *)
class ABlock{
    x:String <- {1; true;}; (* block type test *)
    y:Object <- {}; (* block must have at least one expression *)
};

(* let test *)
class ALet{
    w:Object <- let x:Int in x <- 1; (* let no init -- x in scope with correct type *)
    x:Object <- let x:Int <- 5 in x+"one"; (* let init -- x in scope iwth correct type *)
    y:Object <- let x:Int <- 5, y:Int <- 5 in x+y+(new Object); (* multi init variable in scope *)
};

(* case test *)
class ACaseTest{
};
class BCaseTest{
};
class ACase{
    x:Int <- case 1 of x:ACaseTest => 1;
                     y:BCaseTest => "two";
                     esac; (* case lub test *)

    y:Object <- case 1 of x:Int => 1;
                         y:Int => 2;
                         esac; (* can't have multiple branches with the same type *)
};

(* new test *)
class Anew{
    x:Anew <- new Object; (* test new type *)
};

(* isvoid test *)
class Aisvoid{
    x:Int <- isvoid 1; (* isvoid type test *)
};

(* arithmetic test *)
class AArithmetic{
    x:Int <- 1+true; (* easy plus test *)
    y:Int <- x-"two"; (* easy sub test *)
    z:Int <- x*"one"; (* easy mul test *)
    zz:Int <- z/"zero"; (* easy div test *)

    a:Bool <- x<(new Object); (* easy inequality test *)
    b:Bool <- x<=false; (* easy inequality test 2 *)
    d:Bool <- not (new Object); (* easy not test *)
};

(* test inheritance *)
class AInher {
  x:Int <- 5;
  zoo(): Int { 1 };
};

class BInher inherits AInher {
  x:Int <- 4; (* can't redefine variables from ancestors *)
  y:Int <- 4;
  y:Int <- 4; (* can't redefine variables within the same class *)
  zoo():Bool{true}; (* overridden methods must match return type and signature of the ancestor method *)
};

(* test method calls *)
class AMethodCall {
  zoo(a:Int, b:Int, c:Bool): Int { if c then a + b else b + a fi };
};

(* non existent type names*)
class AType {
  zoo(a:Int, b:Int, c:FakeType): Int { if c then a + b else b + a fi };
};

(* default tests *)
class C {
	a : Int;
	b : Bool;
	init(x : Int, y : Bool) : C {
           {
		a <- x;
		b <- y;
		self;
           }
	};
};

Class Main {
    testAMethodCall:AMethodCall <- new AMethodCall;
	main():C {
	 {
	  testAMethodCall.zoo(1, 2, 1); (* test incorrect argument types *)
	  (new C).init(1,1);
	  (new C).init(1,true,3);
	  (new C).iinit(1,true);
	  (new C);
	 }
	};
};
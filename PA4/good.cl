(* primitives test *)
class APrimitives{
    x:Int <- 1;
    y:Bool <- true;
    yy:Bool <- false;
    z:String <- "true";
 };

(* test 3 types of dispatch *)
class BDispatch{
    moo() : Int  {
      5
    };

 };

class ADispatch inherits BDispatch{
    x:Int <- 5;

    moo() : Int  {
      zoo() (* type 2 *)
    };

    zoo(): Int{
       5
    };

 };






(* test conditionals *)
class ACond{
    x:Int <- if true then 1 else 2 fi; (* easy test *)
    y:Object <- if true then new Object else 2 fi; (* lub test *)

};

(* while test *)
class ALoop{
    x:Object <- while false loop 1 pool; (* easy test *)
};

(* block test *)
class ABlock{
    x:Object <- {1; 2;}; (* easy test *)
};

(* let test *)
class ALet{
    w:Object <- let x:Int in x <- 1; (* no init *)
    x:Object <- let x:Int <- 5 in x+1; (* 1 init *)
    y:Object <- let x:Int <- 5, y:Int <- 5 in x+y; (* multi init *)
};

(* case test *)
class ACase{
    x:Object <- case 1 of x:Int => 1;
                     y:String => "two";
                     esac; (* easy test *)
};

(* new test *)
class Anew{
    x:Object <- new Object;
};

(* isvoid test *)
class Aisvoid{
    x:Object <- isvoid 1; (* easy test *)
};

(* arithmetic test *)
class AArithmetic{
    x:Int <- 1+2; (* easy plus test *)
    y:Int <- x-2; (* easy sub test *)
    z:Int <- x*y; (* easy mul test *)
    zz:Int <- z/y; (* easy div test *)

    a:Bool <- x<y; (* easy inequality test *)
    b:Bool <- x<=y; (* easy inequality test 2 *)
    c:Bool <- x=y; (* easy equality test *)
    d:Bool <- not c; (* easy not test *)
};

(* test inheritance *)
class AInher {
  x:Int <- 5;
  zoo(): Int { 1 };
};

class BInher inherits AInher {
  moo():Int{ x + zoo() (* can access ancestor attributes and methods *)
  };
};



(* test method calls *)
class AMethodCall {
  zoo(a:Int, b:Int, c:Bool): Int { if c then a + b else b + a fi };
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



(* sally-silly test: test SELF_TYPE  on return type *)
class Silly {
   copy() : SELF_TYPE { self };
};

class Sally inherits Silly { 
  x:Int;
  copy() : SELF_TYPE { 
    {x <- 5;
    self;}
  };
};

class AselftypeA {
   x : Sally <- (new Sally).copy();
   test() : Sally { x };
};


(* test dynamic and static dspatch with SELF_TYPE *)
class DispatchAndSelf inherits Sally{
    x0:Int <- 5;
    e0:SELF_TYPE;
    e1:SELF_TYPE;
     y0 : Sally;

  copy0() : SELF_TYPE { 
   { x0 <- 1;
    self; 
  }
    };

 };


(* Test SELF_TYPE in new expression *)
class AselftypeC {

   var : Int <- 0;

   value() : Int { var };

   set_var(num : Int) : SELF_TYPE {  -- return self, work as constructor
      {
         var <- num;
         self;
      }
   };

   method1(num : Int) : SELF_TYPE {  -- same
      self
   };
 };



class Main inherits IO{
  testBInher:BInher <- new BInher;
  testAMethodCall:AMethodCall <- new AMethodCall;
  testADispatch:ADispatch <- new ADispatch;

  main () : Object {{
     testAMethodCall.zoo(1, 2, true);
     testBInher.moo();
     testADispatch.moo(); (* type 1 *)
     testADispatch@BDispatch.moo(); (* type 3 -- static dispatch *)
	 (new C).init(1,true); (* test parentheses *)
	}};
};
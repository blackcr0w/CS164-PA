
(*  Example cool program testing as many aspects of the code generator
    as possible.
 *)
-------------------------------------------------------------------------
(*from original example.cl*)
class Main {
  main():Int { 0 };
};

-------------------------------------------------------------------------
(*test abort method*)
class Main inherits IO{
	main(): Int {
		{
			self.abort();
			0;
		}
	};
};
(*it will print abort from Main*)
---------------------------------------------------------------------------
(*test simple comparisons*)

class A {

};

class Main inherits IO {
	
	x : A <- new A;
	y : A <- x;
	z : A <- new A;

	a:String <- "a";
	b:String <- "a";

	c:Bool <- true;
	d:Bool <- true;

	e: Int <-2;
	f: Int <-2;

	main(): Object{
		{
		if x=y then out_string("correct\n") else out_string("incorrect\n") fi; -----------------------Object compares reference, if they have the same reference it will be considered equal
		if x=z then out_string("incorrect\n") else out_string("correct\n") fi; -----------------------Object compares reference, actual value are not used to test the equality
		if a=b then out_string("correct\n") else out_string("incorrect\n") fi; -----------------------String object tests actual content
		if c=d then out_string("correct\n") else out_string("incorrect\n") fi; -----------------------Bool object tests actual content
		if e=f then out_string("correct\n") else out_string("incorrect\n") fi; -----------------------Int object tests actual content
		}
	};
};

(*expected results to be all correct*)
-------------------------------------------------------------------------------
(*test case statements and simple let statement, this test is from the given test 5.cl*)

class Main inherits IO
{
  main() : Object
  {
    let thing : Object <- self in
      case thing of
	i : Int => out_string( "int\n" );
	b : Bool => out_string( "bool\n" );
      esac
  };
};

(*expected result will be: No match in case statement for Class Main*)
-----------------------------------------------------------------------------------
(*Test if it will pick the most specific branch then choose the most generic branch no matter the order case branches are given.*)

class Main inherits IO
{
	main(): Object{
		let a : Object <-self in 
		case a of 
		o: Object => out_string("object");		 
		m: Object => out_string("main");
		esac
	};
};

(*the expected output is main. Reason is sated above*)
-------------------------------------------------------------------------------
(*test nested let statement, not statement this test is from the given test 4.cl*)

class Main inherits IO
{
  print(x:Int) : Object
  { {
     out_int(x);
     out_string("\n");
    }
  };

  main() : Object
  {
    let foo : Int <- 5 in 
       let foo: Int <- ~1 in
         let foo:Bool in
           if not foo then
             let foo : Int in 
               print(foo+1) ---------------------- prints 1
           else
             5
           fi
  };
};

(*expected result is 1*)

--------------------------------------------------------------------------------------------------------------------
(*test isvoid expression and not expression to see if they work correctly*)
Class A{
}

Class Main inherits IO {
  x : Int <- 1;
  y : Int;
  a : A <- new A;
  b : A;

  main(): SELF_TYPE{
  	{
  		if not isvoid x then out_string("correct\n") else out_string("incorrect\n") fi; ----x is an initialized int
  		if not isvoid y then out_string("correct\n") else out_string("incorrect\n") fi; -----y is an Int, so it is not set to void
      	if not isvoid a then out_string("correct\n") else out_string("incorrect\n") fi;------ a is an initialized varuable A
      	if isvoid b then out_string("correct\n") else out_string("incorrect\n") fi;  -----------b is uninitialized, so it is set to void

  	}
  };
};

(*expected results are all correct*)
-----------------------------------------------------------------------------------------------------------------
(*scoping test. Using same symbols in multiple scopes to test the compiler's ability to deal with multiscope tasks*)

class B inherits IO {
	x : Int <-10;
};

class Main inherits B {
	y : Int <- 20;
	meth(x:Int, y :Int ) : Int{				----------after the call in main method, it takes arguments 30 and 60
		{
			let y:Int <- y+x in print(y);   ---------- y in the let statement is assigned 60+30 and print 90
			x<-x+5;							----------xis modified to 35
			let x:Int <- y+x in x;			---------- x in the let satement is assigned 60+35= 95 and this value is returned
		}
	};
	print(a:Int): SELF_TYPE{
		{
		out_string("\ny=");
		out_int(a);
	}
	};
	
	main(): B {
		{print(y);                              ----------it should print 20 since y has not been modified
		let y:Int <- meth(30,40+y) in print(y); ----------it first call meth to print 90 and then the y in the let statement is assigned 95 to be print out in print(y)
		}
	};
};

(*expected output:
 *y=20
 *y=90
 *y=95*)
-----------------------------------------------------------------------------------------------------------------
(*test complicated case statements, let statement and several comparisons in a case inside case program*)

Class Main inherits IO {
  main(): SELF_TYPE {
    case 5 of												------------------it will go to type Int branch
    a : Bool => out_string("Bool");
    c : Object => out_string("Object");
    b : Int => {
    case "String" of										------------------it will go to String branch
    a : Bool => out_string("Bool");
    c : Object => out_string("Object");
    b : String => { 
    case true of											------------------it will go to Bool branch
    a : Bool => 
    let x : String <- "finish this big chunk of stuff\n" in {
    
    case x of
    a : Bool => out_string("Bool");							------------------since there is no String branch, it will go to Object branch
    c : Object => let y : Int <- ~(~4) in {
    	case y of
    a : Bool => out_string("Bool");
    c : Object => out_string("Object");
    b : Int => {											------------------since y is an int, it will go to Int branch. y will keep the value 4 and x is the string defined before
    	if 3 < y then out_string("correct\n") else out_string("incorrect\n") fi; -----------should print correct
		if 8 < y then out_string("incorrect\n") else out_string("correct\n") fi; -----------should print correct
		if 10 <= y then out_string("incorrect\n") else out_string("correct\n") fi;----------should print correct
		if y <= y then out_string("correct\n") else out_string("incorrect\n") fi; ----------should print correct
		if 10 <= 15 then out_string("correct\n") else out_string("incorrect\n") fi;---------should print correct
		y <- y+1;
		out_int(y); -------------------should print 5
		out_string("\n");
		out_string(x); ----------------should print finish this big chunk of stuff
		out_string("\n");
		};
    esac;
    	};
    b : Int => out_int(b);
    esac;
    };
    c : Object => out_string("Object");
    b : Int => out_int(b);
    esac;
    };
    esac;
    };
    esac
  };
};

----------------------------------------------------------------------------
(*string.concat() method test*)

class Main inherits IO {
	main():Int {
		let a : String <- in_string(), b : String <- in_string()in {

		out_string(a.concat(b));  ------------------------------------------it will print out ab where a and b are inputs from people
		0;  
	}
	};
};
------------------------------------------------------------------------------
(*string.substring() method test*)
class Main inherits IO {
	main():Int {
		let a : String <- in_string(), b : Int <- in_int(), c : Int <- in_int() in { -------------------a is the string, b is the starting point, and c is the number of chars it will save	
		out_string(a.substr(b,c));   ---------if the inputs are abcdefg,0,2 it will print out ab
		0;  
	}
	};
};
-------------------------------------------------------------------------------
(*string.length() method test *)

class Main inherits IO {
	main():Int {
		let c : Int <- in_string().length()in { 
		out_int(c); -----------------------it will print out the length of c
		0;  
	}
	};
};
--------------------------------------------------------------------------------
(*test calling a method in its own class *)

class Main inherits IO {
	add(a: Int, b: Int) : Int {
		a + b
	};
	main():Int {
		{ out_int(add(1, 1)); -------------------it should print out 2
		0; 
	}
	};
};


------------------------------------------------------------------------------------------
(*test method that takes arguments*)

class Main inherits IO{

  main():Object {
  	{
   out_int(sum(add1(1), mul2(2)) );
   out_string("\n");
  }
  };

  sum(a:Int, b:Int):Int {a+b};
  add1(x:Int):Int { x+1 };
  mul2(x:Int):Int { x*2 };

};

(*expected output is: 6  *)


---------------------------------------------------------------------------------
(*test simple arithmetic operations*)

Class Main inherits IO{
  main(): Object {
    out_int(3*(2+2-2)+(9/3))  ----------------------it should print out 9 
  };
};

--------------------------------------------------------------------------------
(*negation tests*)

class Main inherits IO {
	
	x : Int <- 0;
	y : Int <- ~1;

	main(): Object{
		{
		if (~x)=0 then out_string("correct\n") else out_string("incorrect\n") fi;  ----it should print out correct since 0 == -0
		out_int(~x); 																----it should print out 0
		if (~y)=1 then out_string("correct\n") else out_string("incorrect\n") fi;   ----it should print out correct
		out_int(~y);																----it should print out 1
		if (~y)=(~1) then out_string("correct\n") else out_string("incorrect\n") fi;----it should print out incorrect		
		out_int(~(~y));																-----it should print out -1
	}
};
};

---------------------------------------------------------------------------------
(*test type_name method*)

class Main inherits IO {	
	main():Int {
	{
	out_string(type_name());   ----------------------------it should print out Main
	0;
	}
	};
};
---------------------------------------------------------------------------------
(*Dispatch to void*)

class Main inherits IO {	
	main():String {
		let a:Object in a.type_name()  -------------x is not initialized, so it will say Dispatch to void
	};
};
-----------------------------------------------------------------------------------
(*another Dispatch to void*)
class A {
    m() : Int {0};
};
class Main {
    a : A;
    main () : Int {
    {a.m();  ------------------------- a is not initialized, a is void. The program is trying to dispatch to void.
    0;
    }
    };
};

(*expected outout: Dispatch to void *)
-----------------------------------------------------------------------------------
(* Match on void in case statement*)
class Main inherits IO {	
	main():String {
		let x:Object in 
			case x of x : Object => x.type_name() ; --- x is not a type, so it will print match on void in case statement.
	};
};

-----------------------------------------------------------------------------------
(*test static Dispatch and static dispatch to void*)

Class A {
  foo(): String {"A\n"
  };
};

Class B inherits A {
  foo(): String {"B\n"
  };
};

Class Main inherits IO {
	c:B;
  main() : Object {{

    out_string((new B)@A.foo()); -----------it should print out A instead of B
    c@A.foo();   ---------------------------it should show dispatch to void

  }
  };
};

-----------------------------------------------------------------------------------
(*test inheritance and override method*)

class A inherits IO{
    n : Int <- 5; 
    method() : SELF_TYPE {
        {   out_string("This is from A,");
            out_int(n);
            out_string("\n");
            self;
        }
    };
};

class B inherits A {  
    method() : SELF_TYPE {
        {	out_string("This is from B,");
            out_int(n);
            out_string("\n");
            self@A.method();
            self;
        }
    };
};

class Main {
    a : A <- new A;
    b : B <- new B;
    main () : Int {
        {   a.method();  -------------it will print This is from A, 5
            b.method();  -------------call the overriden method, and it will print This is from B, 5, 
            			 -------------              This is from A, 5
            0;   
        }
    };    
};

------------------------------------------------------------------------------------------------------------
(*test case with no right branch *)
class A {
};
class B inherits A {
};
class C inherits A {
};
class Main inherits IO {
    a : A <- new A;
    main () : Int {
        {
            case a of 
                b : B => out_string("B\n");
                c : C => out_string("C\n");
            esac;
            0;
        }
    };
};


(*The result will be: No match in case statement for Class A since a does not match any of the branches*)

-----------------------------------------------------------------------------------------------------------------
(*test case with void*)
class A {
};
class B inherits A {
};
class C inherits A {
};

class Main inherits IO {
    a : A ;
    main () : Int {
        {
            case a of 
                b : B => out_string("B\n");
                c : C => out_string("C\n");
            esac;
            0;
        }
    };
};

(*since a is not initialized, a is void, and it does not match any of the case branches*)
(*it will print out Match on void in case statement*)

-------------------------------------------------------------------------------------------------------------------
(*simple loop test*)
Class Main inherits IO {
  x : Int <- 0;
  main(): Object{
  	while x< 5 loop{
  		out_int(x);
  		x<- x+1;
  		out_string(" ");
  	}pool
  };
};

(*expected result:0 1 2 3 4 *)

-------------------------------------------------------------------------------------------------------------------
(*equality test on void that is returned by a while loop*)

class Main inherits IO {
	m :Object;
	n :Object;
	x : Int <- 0;

	a() : Object {
    while x < 3 loop { 
      out_int(x); 
      x <- x + 1;
      out_string("from a");
    } pool
  };
	b() : Object {
    while x < 6 loop { 
      out_int(x); 
      x <- x + 1;
      out_string("from b");
    } pool
  };

	main(): Object{ -----------------------since a() and b() return void due to the while loop, m and n are void. Here we test if the two void can be judged as eaqual
		{
		m <- a();
		n <- b();
		if m=n then out_string("\nyes\n") else out_string("no") fi;  -------------m and n should be equal, so the expected result should be yes
	}	
	}; 
};

(*expected out put:
*0 from a
*1 from a
*2 from a
*3 from b
*4 from b
*5 from b
*
*yes
*)

-------------------------------------------------------------------------------------------------------------------------------------------
(*simple garbage collection test*)

Class Main inherits IO {
  x : Int <- 0;
  main() : Object {
    {while x < 10000 loop { 
      out_int(x); 
      x <- x + 1;
   	  out_string("\n");
    } pool;
    out_string("finish");
  }
  };
};

(*This is a loop that increases x and assign it back to x 10000 times,
 *so when the garbage collection flag is on, the program should out put the number and 
 *Garbage collecting ... occasionally
*)

---------------------------------------------------------------------------------------------------------------------------------------------
(* Folllowing tests are from the resources provided for this CS164 to test the whether the compiler can deal with a big chunk of cool code*)


(*from cool.cl, it tests type_name method*)
class Main inherits IO {
    main() : SELF_TYPE {
	{
	    out_string((new Object).type_name().substr(4,1)). ----- it gives out c
	    out_string((isvoid self).type_name().substr(1,3)); ----it gives out ool
	    out_string("\n");
	}
    };
};

(*it should output: cool*)

------------------------------------------------------------------------------------------------------------------------------------
(*from list.cl*)

(*
 *  This file shows how to implement a list data type for lists of integers.
 *  It makes use of INHERITANCE and DYNAMIC DISPATCH.
 *
 *  The List class has 4 operations defined on List objects. If 'l' is
 *  a list, then the methods dispatched on 'l' have the following effects:
 *
 *    isNil() : Bool		Returns true if 'l' is empty, false otherwise.
 *    head()  : Int		Returns the integer at the head of 'l'.
 *				If 'l' is empty, execution aborts.
 *    tail()  : List		Returns the remainder of the 'l',
 *				i.e. without the first element.
 *    cons(i : Int) : List	Return a new list containing i as the
 *				first element, followed by the
 *				elements in 'l'.
 *
 *  There are 2 kinds of lists, the empty list and a non-empty
 *  list. We can think of the non-empty list as a specialization of
 *  the empty list.
 *  The class List defines the operations on empty list. The class
 *  Cons inherits from List and redefines things to handle non-empty
 *  lists.
 *)


class List {
   -- Define operations on empty lists.

   isNil() : Bool { true };

   -- Since abort() has return type Object and head() has return type
   -- Int, we need to have an Int as the result of the method body,
   -- even though abort() never returns.

   head()  : Int { { abort(); 0; } };

   -- As for head(), the self is just to make sure the return type of
   -- tail() is correct.

   tail()  : List { { abort(); self; } };

   -- When we cons and element onto the empty list we get a non-empty
   -- list. The (new Cons) expression creates a new list cell of class
   -- Cons, which is initialized by a dispatch to init().
   -- The result of init() is an element of class Cons, but it
   -- conforms to the return type List, because Cons is a subclass of
   -- List.

   cons(i : Int) : List {
      (new Cons).init(i, self)
   };

};


(*
 *  Cons inherits all operations from List. We can reuse only the cons
 *  method though, because adding an element to the front of an emtpy
 *  list is the same as adding it to the front of a non empty
 *  list. All other methods have to be redefined, since the behaviour
 *  for them is different from the empty list.
 *
 *  Cons needs two attributes to hold the integer of this list
 *  cell and to hold the rest of the list.
 *
 *  The init() method is used by the cons() method to initialize the
 *  cell.
 *)

class Cons inherits List {

   car : Int;	-- The element in this list cell

   cdr : List;	-- The rest of the list

   isNil() : Bool { false };

   head()  : Int { car };

   tail()  : List { cdr };

   init(i : Int, rest : List) : List {
      {
	 car <- i;
	 cdr <- rest;
	 self;
      }
   };

};



(*
 *  The Main class shows how to use the List class. It creates a small
 *  list and then repeatedly prints out its elements and takes off the
 *  first element of the list.
 *)

class Main inherits IO {

   mylist : List;

   -- Print all elements of the list. Calls itself recursively with
   -- the tail of the list, until the end of the list is reached.

   print_list(l : List) : Object {
      if l.isNil() then out_string("\n")
                   else {
			   out_int(l.head());
			   out_string(" ");
			   print_list(l.tail());
		        }
      fi
   };

   -- Note how the dynamic dispatch mechanism is responsible to end
   -- the while loop. As long as mylist is bound to an object of 
   -- dynamic type Cons, the dispatch to isNil calls the isNil method of
   -- the Cons class, which returns false. However when we reach the
   -- end of the list, mylist gets bound to the object that was
   -- created by the (new List) expression. This object is of dynamic type
   -- List, and thus the method isNil in the List class is called and
   -- returns true.

   main() : Object {
      {
	 mylist <- new List.cons(1).cons(2).cons(3).cons(4).cons(5);
	 while (not mylist.isNil()) loop
	    {
	       print_list(mylist);
	       mylist <- mylist.tail();
	    }
	 pool;
      }
   };

};

(*expected output
 *5 4 3 2 1
 *4 3 2 1
 *3 2 1
 *2 1
 *1      
 *)

------------------------------------------------------------------------------------------------------------------------------
(*from complex.cl*)

class Main inherits IO {
    main() : SELF_TYPE {
	(let c : Complex <- (new Complex).init(2, 3) in
	    if c.reflect_X().reflect_Y() = c.reflect_0()
	    then out_string("=)\n")
	    else out_string("=(\n")
	    fi
	)
    };
};

class Complex inherits IO {
    x : Int;
    y : Int;

    init(a : Int, b : Int) : Complex {
	{
	    x = a;
	    y = b;
	    self;
	}
    };

    print() : Object {
	if y = 0
	then out_int(x)
	else out_int(x).out_string("+").out_int(y).out_string("I")
	fi
    };

    reflect_0() : Complex {
	{
	    x = ~x;
	    y = ~y;
	    self;
	}
    };

    reflect_X() : Complex {
	{
	    y = ~y;
	    self;
	}
    };

    reflect_Y() : Complex {
	{
	    x = ~x;
	    self;
	}
    };
};

(*it should output:=)
 *COOL program successfully executed
 *)
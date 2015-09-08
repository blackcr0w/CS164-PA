 (*
 *  CS164 Fall 2015
 *
 *  Author: Mingjie Zhao
 *  Login:  cs164-cp
 *  Time:   Sep.3, 2015, Berkeley
 *
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *
 *
 *)

 class List {
 	 isNil() : Bool { 
 	 	 true
 	 };

 	 head() : String {
 	 {
 	 	 abort(); 
 	    "\0"; 
 	     } 
 	 };

    tail() : List {
    	{
    		abort();
    		self;
        }
    };

    cons(s : String) : List {
    	(new Cons). init(s, self)
    };
 };

 class Cons inherits List {
 	 car : String;
 	 cdr : List;

 	 isNil() : Bool {
 	 	 false
     };

     head() : String {
     	 car
     };

     tail() : List {
     	 cdr
     };

     init(s : String, rest : List) : List {
     	 {
     	 	 car <- s;
     	 	 cdr <- rest;
     	 	 self;
     	 	 }
     };
 };
   
class Main inherits IO {

    z : A2I <- new A2I;
    s : String;
    stack : List;

    display(l : List) : Object {
    	if l.isNil() then 
    		out_string("")
    	else {
    		    out_string(l.head());
    		    out_string("\n");
    		    display(l.tail());
            }
        fi
    };

    push(str : String) : Object {
    	if stack.isNil()  then {
    		stack <- (new List).cons(str);
        }
    	else
    		stack <- stack.cons(str)
    	fi
    };

    pop() : String {
    		(let str : String in {
    		    str <- stack.head();
         	    stack <- stack.tail();
        	    str;
            }
        	)
    };

   newline() : Object {
   	   out_string("\n")
   };

    prompt() : String {
    	{
    		out_string(">");
    		in_string();
        }
    };

    add(l : List) : Object { 
    	{
    		(let s1 : String <- pop(), s2 : String <- pop(), sums : String in
    		    (let i1 : Int <- z.a2i(s1), i2 : Int <- z.a2i(s2), sum : Int in
    		    {
    		    	sum <- i1 + i2;
    		    	sums <- z.i2a(sum);
    		    	push(sums);
                }
                )
            );
        }
    };

    swap(l : List) : Object {
    	{
    		(let s1 : String, s2 : String in
    		{
    		    s1 <- pop();
    		    s2 <- pop();
    		    push(s1);
    		    push(s2);
            }
            );
        }
    };

    evalu(l : List) : Object {
        if stack.isNil() then 
            0
        else 
            (let str : String <- l.head() in
             if str = "s" then {
                    pop();
                    swap(stack);
               }
               else if str = "+" then {
                    pop();
                    add(stack);
                 }
               else 
                    0
              fi fi
             )
         fi
    };

    main() : Object {
    	{
    		stack <- new List;

    		while true loop {
            	s <- prompt();
            	if s = "e" then
            		evalu(stack)
            	else if s = "d" then
            		display(stack)
            	else if s = "x" then
            		abort()              (**************)
            	else 
            		push(s)
               fi fi fi;
            }
            pool;
    }
    
  };

};

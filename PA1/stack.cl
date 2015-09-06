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

class StackCommand inherits Cons {
	stack : List;
	head : Int;

    display_stack(l : List) : Object {
		if l.isNil() then out_string("\n")
			else {
				out_int(l.head());
				out_string(" ");
				print_stack(l.tail());
            }
            fi
    };

    push(i : Int) : Int {
    	{
    		if stack.isNil()
    			stack <- new List
    		else
    			stack <- stack.cons(i)
    		fi
        };
        0;
    };

    pop() : Int {
    	{
    		head <- stack.head();
    		stack <- stack.tail();
    		head;
        }
    };
    


class Main inherits IO {

   newline() : Object {
   	   out_string("\n")
   };

    prompt() : String {
    	{
    		out_string(">");
    		in_string();
        }
    };

(* TODO: return type is not correct,
 * when enter "x", should not abort()
 *)
    main() : Object {
    	(let z : A2I <- new A2I, s : String <- prompt() in
    	while not s = "x" loop
    		{
        	s <- prompt();
        	(let i : Int <- z.a2i(s) in 
        	 (let news : String <- z.i2a(i) in
        	  {
        	 	 out_int(i);
        	 	 newline();
        	 	 out_string("foo\n");
        	 	 out_string(news);
        	 	 newline();
              }
             )
            );
            }
          pool 
          )
    
  };

};



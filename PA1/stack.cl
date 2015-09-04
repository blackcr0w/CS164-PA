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



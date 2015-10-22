(*
 *  CS164 Spring 2004
 *
 *  Programming Assignment 1
 *    Implementation of a simple stack machine.
 *
 *  Skeleton file
 *)
class List {

   isNil() : Bool { true };

   head()  : String { { abort(); ""; } };

   tail()  : List { { abort(); self; } };

   cons(s : String) : List {
      (new Cons).init(s, self)
   };

};

class Cons inherits List {

   car : String;

   cdr : List;

   isNil() : Bool { false };

   head()  : String { car };

   tail()  : List { cdr };

   init(i : String, rest : List) : List {
      {
	 car <- i;
	 cdr <- rest;
	 self;
      }
   };

};

class Stack inherits Object {
   top : List <- (new List);
   temp : List; 
   atoi : A2I <- (new A2I);

   push(s : String) : List {
      top<-top.cons(s)
   };

   pop() : String{{
       temp <- top;
       top <- top.tail();
       temp.head();}
   };

   getTop() : List{
      top
   };

   isNil() : Bool {
      top.isNil()
   };
   
   priv_to_string(l : List) : String{
      if l.isNil() then ""
      else{
         l.head().concat("\n").concat(priv_to_string(l.tail()));   
      }
      fi
   };

   to_string() : String {
      priv_to_string(top)   
   };

   (*these are to be called by child classes*)
   init(stack : Stack) : Stack{
      {abort(); self; (*never gets called, just satisfies the return type*)}
   };

   command() : String {
      {abort(); "";}
   };

};

class SE inherits Stack{
   temp_s1 : String;
   temp_s2 : String;
   temp_i1 : Int;
   temp_i2 : Int;

   init(stack : Stack) : Stack{
      {
         top <- stack.getTop();
         self;
      }
   };

   command() : String {
   {  
      if (not self.isNil()) then temp_s1 <- self.pop() else temp_s1 <- "" fi;

      if temp_s1 = "" then ""
      else if temp_s1 = "+" then
      {
         temp_i1 <- atoi.a2i(self.pop());
         temp_i2 <- atoi.a2i(self.pop());
         
         temp_i1 <- temp_i1 + temp_i2;
         temp_s2 <- atoi.i2a(temp_i1);
         self.push(temp_s2);
         temp_s2;
      }else if temp_s1 = "s" then
      {
         temp_s1 <- self.pop();
         temp_s2 <- self.pop();
         self.push(temp_s1);
         self.push(temp_s2);
         temp_s2;
      }else {
         self.push(temp_s1);
         temp_s1;
      }
      fi fi fi;
   }
   };
};

class Main inherits IO {

   stack : Stack;
   input : String;
   flag : Bool <- true;

   prompt() : String{
   {
       out_string("> ");
       in_string();
   }
   };
   
   main() : Object {
      {
      stack <- (new Stack);

      input <- prompt();
   
      while flag loop
         {
            if input = "d" then out_string(stack.to_string())
            else if input = "x" then flag <- false
            else if input = "e" then 
            {
               stack <- (new SE).init(stack);
               stack.command();
            }
            else stack.push(input) (*else it's a digit*)
            fi fi fi;
            
            if flag then input <- prompt() else "" fi;
         }
         pool;
      }
   };

};

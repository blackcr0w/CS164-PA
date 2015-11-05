class Main inherits IO {

   foo(x:Int) : Object {
      { out_int(x);
        x <- 5;
        out_int(x);
        x <- x * x;
        out_int(x);
      }
   };

   main() : Object {
     foo(4)
   };
};
   

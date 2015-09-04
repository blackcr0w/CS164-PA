class A {};
class Main inherits IO {
  a : A;
  main() : Object {
    {
      a <- (new A);
      (if isvoid a then
        out_string("true")
      else 
        out_string("false")
      fi);
      out_string("\n");
    }
  };
};

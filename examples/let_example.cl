class Main inherits IO {
  x : Int;
  z : Int;
  main() : Object {
    {
      x <- 4; -- x initially set to 4
      z <- 2; -- z initially set to 2
      (* z is shadowed and redefined in the let expr *)
      x <- let z:Int, z:Int <- z+1 in z; -- assign value to x
      out_int(x); -- x should be re-assigned to 1
      out_string("\n");
      out_int(z); -- z has not been redefined in this scope
      out_string("\n");
    }
  };
};

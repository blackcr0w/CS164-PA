class Main inherits IO {
  main() : Object {
    let result : Int <- 1, i : Int <- 5 in {
      while(not(i=0)) loop {
        result = result * i;
        i <- i - 1;
      } 
      pool;
      result;
    }
  };
};

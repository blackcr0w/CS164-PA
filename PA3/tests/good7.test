(* no feature test *)
class MadeUpType{
   
};

(* multiple class test and multiple features*)
class Foo inherits Bazz {
     (* all basic expressions test *)
     a : Int <- 1; 
     b : Int <- testId <- 1;
     c : Int <- 1 @ Int.testId2(1, 2);
     c2 : Int <- 1 @ Int.testId3();
     d : Int <- testId4(1, 2, 3);
     d2 : Int <- testId5();
     e : Int <- if 1 then 2 else 4 fi;
     f : Int <- while 0 loop 1 pool;
     g : Int <- {1;};
     g2 : Int <- {1; 2; 3;};
     h : Int <- let testId6 : Int in 1;
     h2 : Int <- let testId6 : Int <- 1, testId7 : MadeUpType in testId7 <- testId6;
     i : Int <- case 1 of testId8 : Int => 2; esac;
     i2 : Int <- case 1 of testId9 : Int => 1; testId10 : MadeUpType => 2;  esac;
     j : Int <- new MadeUpType;
     k : Int <- isvoid 1;
     l : Int <- 1 + 2;
     m : Int <- 1 - 2;
     n : Int <- 1 * 2;
     o : Int <- 1 / 0;
     p : Int <- ~1;
     q : Int <- 1 < 2;
     r : Int <- 1 <= 2;
     s : Int <- 1 = 2;
     t : Int <- not 1; 
     u : Int <- (1);
     v : Int <- testId11;
     w : Int <- "test string";
     x : Int <- true;
     y : Int <- false;

     (* method feature with no formals test *)
     madeUptest() : Int { 1 };
     (* method features with multiple formals test *)
     madeUptest(testId13 : Int, testId14 : MadeUpType) : Int {1};

     
     (* nested expressions tests *)
     a : Razz <- case self of
              n : Razz => (new Bar);
              n : Foo => (new Razz);
              n : Bar => n;
             esac;

     b : Int <- a.doh() + g.doh() + doh() + printh();

     doh() : Int { (let i : Int <- h in { h <- h + 2; i; } ) };

};

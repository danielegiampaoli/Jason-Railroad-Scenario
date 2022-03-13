/* Initial beliefs */

+waiting(passenger,station).
+onboard(passenger,train).

/* Goals */

!operate(train,station).

/* Plans */

+!operate(train,station) : true
   <- !at(train,station);
	  open(doors);
      !unboard(passenger);
	  !board(passenger);
	  close(doors);
	  +done(operations,station);
	  !at(train,station);
	  !operate(train,station).
	   
+!at(train,station)
    : at(train,station) & not done(operations,station)
	<- true.
+!at(train,station)
    : at(train,station) & done(operations,station)
    <- depart(station);
	   -done(operations,station);	
	   move_to(station);
	   !at(train,station).
+!at(train,station)
    : not at(train,station)
    <- move_to(station);
	   !at(train,station).
	   
+!unboard(passenger) : onboard(passenger,train) 
    <- unboard(passenger).
+!unboard(passenger) : not onboard(passenger,train) 
    <- true.	
	   
+!board(passenger) : waiting(passenger,station)
    <- board(passenger).
+!board(passenger) : not waiting(passenger,station)
    <- true.	   
	   
+waiting_pass(passenger,0)
    :  waiting(passenger,station)
    <- -waiting(passenger,station).
+waiting_pass(passenger,N)
    :  N > 0 & not waiting(passenger,station)
    <- +waiting(passenger,station).
   
+onboard_pass(passenger,0)
    : onboard(passenger,train)
    <- -onboard(passenger,train).
+onboard_pass(passenger,N)
    :  N > 0 & not onboard(passenger,train)
    <- +onboard(passenger,train).

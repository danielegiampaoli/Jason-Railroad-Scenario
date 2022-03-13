/* Initial Beliefs */
+colour(green).

/* Goals */

!check(train).

/* Plans */

+!check(train): true
    <- !at(train,station);
        !check(train).
	
+!at(train,station): at(train,station) & not done(operations,station)
	<- true.		
		
+!at(train,station): at(train,station) & done(operations,station)
	<- .wait(2000);
		turn_red(signal2);
		+colour(red);
		-colour(green).	
		
+!at(train,station):
    not at(train,station)
    <- !check(colour).
	
+!check(colour): colour(red)
    <- .wait(4000);
        turn_yellow(signal2);
        +colour(yellow);
        -colour(red);
        !at(train,station).
		
+!check(colour): colour(yellow)
    <- .wait(1500);
        turn_green(signal2);
        +colour(green);
        -colour(yellow);
        !at(train,station).
		
+!check(colour): colour(green)
    <- !at(train,station).
	
+!check(colour): true <- true.

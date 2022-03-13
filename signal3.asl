/* Initial Belief, my light is green */
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
		turn_red(signal3);
		+colour(red);
		-colour(green).			
	
+!at(train,station): not at(train,station)
	<- !check(colour).
	
+!check(colour): colour(red)
	<- .wait(4000);
		turn_yellow(signal3);
		+colour(yellow);
		-colour(red);
		!at(train,station).
		
+!check(colour): colour(yellow)
	<- .wait(2000);
		turn_green(signal3);
		+colour(green);
		-colour(yellow);
		!at(train,station).
		
+!check(colour): colour(green)
	<- !at(train,station).
	
+!check(colour): true <- true.

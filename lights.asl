/* Goals */

!change(colour).

/* Plans */

+!change(colour) : true
    <- turn_red(colour);
	   .wait(2000);
	   turn_yellow(colour);
	   .wait(2000);
	   turn_green(colour);
	   .wait(2000);
	   !change(colour).

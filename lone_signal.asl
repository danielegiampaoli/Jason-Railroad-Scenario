/* Goals */

!change(colour).

/* Plans */

+!change(colour) : true
    <- turn_red(lone);
	   .wait(5000);
	   turn_yellow(lone);
	   .wait(5000);
	   turn_green(lone);
	   .wait(5000);
	   !change(colour).

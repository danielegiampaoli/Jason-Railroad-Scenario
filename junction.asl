/* Goals */
!switch(points).

/* Plans */

+!switch(points): true
	<- .wait(4000);
		switch(reverse);
		.wait(4000);
		switch(normal);
		!switch(points).

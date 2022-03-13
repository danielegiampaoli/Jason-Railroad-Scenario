# Jason Railroad Scenario

This is a multi-agent system in Jason modelling a simplified railroad scenario. The MAS includes a set of cognitive agents following the BDI architecture, i.e., a train, a set of signals and a railroad junction. The train will move along a circular track while collecting and dropping passengers at stations, stopping at red lights and switching track
at the junction when required. A custom environment in Java will handle agent interactions, provide functions to agents and a graphical interface.

This work is intended to be run from _jedit_ (or equivalent) and its output is the list of actions executed as displayed on a console and a live visual representation of agents performing their actions and interacting.

### Scenario

A train moves along a circular track. The track has three stations, each hosting a number of waiting
passengers. The number of passengers can vary from station to station and from time to time. Each
station is equipped with a signal, which can take three colours - green, yellow or red. Station signals
are green in idle conditions. When the train departs from a station, that station’s signal will turn
red shortly after, in order to indicate that the track beyond is now occupied. It will then turn yellow
and finally green again, in order to indicate that the track is now free. This is inspired by common
underground systems, as opposed to railway systems, where station signals are red in idle conditions
and only turn green to allow trains to leave the station.
The train stops at each station to pick up passengers waiting on the platform and drop passengers on
board of the train. After the boarding operations have completed, if the signal is green, the train will
depart and move towards the next station.
Additionally, a stand-alone signal is placed along the track, away from the stations, which changes
colour from time to time (at regular intervals). This signal simulates what happens in real scenarios,
where signals also stop and let trains go not necessarily at stations for a number of reasons, e.g. a
portion of the track in front is occupied.
Lastly, a railroad junction is placed before accessing one of the stations, which has two platforms.
Whenever platform 1 is free, points are in normal position and the approaching train can access this
platform. If another train occupies platform 1, points will switch to reverse position and reroute any
approaching train to platform 2, which is supposed to be free. In this simulation, there will physically
be no additional trains on platform 1 and the model will simply simulate their presence by changing
the points position from time to time (at regular intervals). The train will therefore be ’randomly’
directed to one of the two platforms when approaching this station.
Agents were programmed in Jason using jedit, while the surrounding environment was developed in
Java.

This work was part of the Multiagent System course at the University of Genoa, Italy (Data Science and Engineering MSc, Artificial Intelligence track, 2021/22).

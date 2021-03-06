# MultiAgentSimulator
A multi-agent system tool for simulating occupancy over time. The simulation involves the movement of agents between spaces over time, with the resulting population of each room available to view at each time-step.
The movements between spaces are Markovian, in that they are only dependent on the current state, and no past states. Depending on where an agent is currently located,
that agent will have an associated probability for each available movement they can make, each of which are configured by the user.

The options for the user to configure are:
* The number of spaces in the system.
* The number of agents in the system.
* The length of the dataset, i.e. the number of time increments.
* The movement probabilities between spaces.

[Click here to open the User Documentation](https://github.com/csteven1/MultiAgentSimulator/wiki)

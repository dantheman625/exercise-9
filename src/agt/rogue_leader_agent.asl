// rogue leader agent is a type of sensing agent

/* Initial beliefs & goals */
witness_ratings(
  [sensing_agent_1, sensing_agent_2, sensing_agent_3, sensing_agent_4, sensing_agent_5, sensing_agent_6, sensing_agent_7, sensing_agent_8, sensing_agent_9],
  [-1, -1, -1, -1, 1, 1, 1, 1, 1]
).

!set_up_plans. // the agent has the goal to add pro-rogue plans

/* 
 * Plan for reacting to the addition of the goal !set_up_plans
 * Triggering event: addition of goal !set_up_plans
 * Context: true (the plan is always applicable)
 * Body: adds pro-rogue plans for reading the temperature without using a weather station
*/
+!set_up_plans : true <-

  // removes plans for reading the temperature with the weather station
  .relevant_plans({ +!read_temperature }, _, LL);
  .remove_plan(LL);
  .relevant_plans({ -!read_temperature }, _, LL2);
  .remove_plan(LL2);

  .add_plan({ +temperature(Celsius)[source(Sender)] : true <-
    .findall([Agents, WRRatings], witness_ratings(Agents, WRRatings), WRRatingsList);
    .nth(0, WRRatingsList, WR);
    .nth(0, WR, Agents);
    .nth(1, WR, WRRatings);
    .my_name(Name);
    for ( .range(I,0,8) ) {
      .nth(I, Agents, Agent);
      .nth(I, WRRatings, WRRating);
      if (Sender == Agent & Agent \== Name) {
          .send(acting_agent, tell, witness_reputation(Name, Agent, temperature(Celsius), WRRating));
      };
    };
  });

  // adds a new plan for always broadcasting the temperature -2
  .add_plan({ +!read_temperature : true
    <-
      .print("Reading the temperature");
      .print("Read temperature (Celcius): ", -2);
      .broadcast(tell, temperature(-2))}).

/* Import behavior of sensing agent */
{ include("sensing_agent.asl")}
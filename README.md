Spring Tomcat application that generates random Role Playing Game-related entities, for use by players and Game Masters who want some quick tools to help the world-creation process.

# Name Generator

The name generator can be reached at the /name endpoint as well as / starting point. It provides functionality to generate a list of random names. If the user authenticates with their Google account and authorizes the application to save to their Google Drive appData folder, they can save names that they like and access them from any computer.

There is also a feature to "train" the name generator. When there is enough training data, you may be able to specify a particular attribute that you'd like the generated names to embody.

# City Generator

The city generator provides various customizable parameters to generate a city, from a tiny hamlet to a sprawling metropolis. The definitions of these sizes and races come directly from the D&D DM's guide, but the level of diversity is application specific.

# Dungeon Generator

The dungeon generator tool creates a simple graphical map of a dungeon, and provides some random values from the DMG tables on the purpose/history/population of said dungeon.

# NPC Generator

The NPC generator creates an NPC including name, race, class, background, appearance, alignment, and motivation, based on the NPC generation tables in the DMG.

# Party Tracker

The party management tool allows the user to create adventuring parties, populate them with members and add common values like current/max HP, initiative roll, and AC.

# Tables

The table tools has many of the tables that can be found in the D&D Players' Handbook and DMG, and includes a function to roll randomly on the table. The tables are organized by topic and purpose.

# Development

Required for development:

* gradle

<pre>
git clone --recursive https://github.com/dzliergaard/namegen.git
cd namegen
gradle build
</pre>

## Google authentication

In order to run you will need to create a google developer console project Credentials and allow the javascript origin of whatever endpoint you'd like to call the signin/drive API from.

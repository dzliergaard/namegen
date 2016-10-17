Spring Tomcat application that generates random Role Playing Game-related entities, for use by players and Game Masters who want some quick tools to help the world-creation process.

# Name Generator

The name generator can be reached at the /name endpoint as well as / starting point. It provides functionality to generate a list of random names. If the user authenticates with their Google account and authorizes the application to save to their Google Drive appData folder, they can save names that they like and access them from any computer.

There is also a feature to "train" the name generator. When there is enough training data, you may be able to specify a particular attribute that you'd like the generated names to embody.

# City Generator

The city generator provides various customizable parameters to generate a city, from a tiny hamlet to a sprawling metropolis. The definitions of these sizes and races come directly from the D&D DM's guide, but the level of diversity is application specific.

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

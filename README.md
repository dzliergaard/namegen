Spring Tomcat application that generates random Role Playing Game-related entities, for use by players and Game Masters who want some quick tools to help the world-creation process.

## Name Generator

The name generator can be reached at the /name endpoint as well as / starting point. It provides functionality to generate a list of random names. If the user authenticates with their Google account and authorizes the application to save to their Google Drive appData folder, they can save names that they like and access them from any computer.

There is also a feature to "train" the name generator. When there is enough training data, you may be able to specify a particular attribute that you'd like the generated names to embody.

## City Generator

The city generator provides various customizable parameters to generate a city, from a tiny hamlet to a sprawling metropolis. The definitions of these sizes and races come directly from the D&D DM's guide, but the level of diversity is application specific.

## Development

The following technologies are required to build the project:

<pre>
Typescript transpiler "tsc"
Scss compiler "scss"
Node.js installer "npm"
Maven task runner "mvn"
</pre>

### Building web resources

<pre>
git clone https://github.com/dzliergaard/namegen.git
# install node dependencies and transpile typescript files
cd namegen/src/main/webapp/resources/js
npm install
tsc */*.ts
# compile main.scss into main.css
cd ..
scss --update css/main.scss
</pre>

### AWS and Google authentication

In order to run you will need to create a google developer console project Credentials, and save the secret key to the file "src/main/resources/rptools_secret". In addition, you will have to install and set up AWS CLI to retrieve the name and city raw data files. I have made the base files downloadable to anyone, but if you want to make changes you will have to upload your own.

Installing and configuring AWS CLI:

<pre>
sudo apt-get install -y python-pip
sudo pip install awscli
aws configure
</pre>

### Running Tomcat with Maven

<pre>
# from base directory
mvn clean install tomcat7:run
</pre>
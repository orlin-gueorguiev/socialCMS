socialCMS
=========

The social CMS

This project tries to add some social components to your typical contact management, such as:
- Helping You never forget special dates for other people
- Assisting You in keeping contact with people
- etc.


Technically, the project is build as follows:
- JPA 2.1 for DB (Impelementation: Hibernate)
- Rest Services for accessing the information (JAX-WS)
- Portlets for accessing the UI (Jetspeed)
- Portlet container for representing the portles (Jetspeed)


I. How to start using the Rest Project:
1. Download the soruces:
git clone git://github.com/orlin-gueorguiev/socialCMS -b dev/v1/minor00

2. Setup the DB
Install posgresql and configure according to profile/dev/profile.properties
DB = jdbc:postgresql://127.0.0.1/socialCMS_dev
User = socialCMS
Pass = cms_pass123

3. Build with maven 
mvn clean install

4. Deploy the war files to your web application server and start it
The war files is in: rest-base/target/rest-base.war; 

5. Execute the integration tests:
- Go into rest-test
- Execute mvn verify -DskipITs=false

II. Deploy the Jetspeed and the portlets
1. Build with maven
Go to socialCMSPortall and execute:
mvn jetspeed:mvn -Dtarget=install

2. Deploy the two war files into your web application server and start them
- Jetspeed portal: socialCMSPortal/socialCMSPortal-pa/target/socialCMSPortal-pa.war 
- Jetspeed portlets: socialCMSPortal/socialCMSPortal-portal/socialCMSPortal-portal.war

3. Visit the Jetspeed portal under: 
localhost:8080/socialCMSPortal
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


How to start using the project:
1. Download the soruces:
git clone git://github.com/orlin-gueorguiev/socialCMS -b dev/v1/minor00

2. Setup the DB
Install posgresql and configure according to profile/dev/profile.properties


3. Build with maven 
mvn clean install
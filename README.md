# Mitchell-JAVA

Restful Web Application build using Java and Hibernate.

Requirements:

1.JDK8
2.MYSQL
3.Tomcat v8

Run:

Configure the HIbernate config file based upon the databases properties and deploy the application using Tomcat

APIS:-

1. GET http://mitchell.dal.jelastic.vps-host.net/vijayrajthota/vehicles
   returns all the vehicles information

 2. GET http://mitchell.dal.jelastic.vps-host.net/vijayrajthota/vehicles/{id}
    returns vehicle information with the specific id

3. POST http://mitchell.dal.jelastic.vps-host.net/vijayrajthota/vehicles
    creates a new entry in the table and returns a success message

4. PUT http://mitchell.dal.jelastic.vps-host.net/vijayrajthota/vehicles
     Updates the entry in the table and return a success message

5. DELETE http://mitchell.dal.jelastic.vps-host.net/vijayrajthota/vehicles/{id}
    deletes the entry of the vechile with the specified id and return success.
    
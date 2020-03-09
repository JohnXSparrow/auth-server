##### - Run a specified profile:
`mvn spring-boot:run -Dspring-boot.run.profiles= <environment>`

##### - Build Project, Specific Profile Properties
`mvn install -DmyActiveProfile=<environment>`

##### - Run .jar file
*Used when profile is specified on build

`java -jar auth-server.jar`

##### - Setting active profile on jar file
*Used when no profile is specified on build

`java -jar -Dspring.profiles.active=<environment> auth-server.jar`

##### Available Environments: 
`prod, dev, local`
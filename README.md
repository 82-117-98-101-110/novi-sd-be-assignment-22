**Ravel-backend-01**

Installatiehandleiding met Docker
Dit onderdeel zal uitleggen hoe je de applicatie kan compileert naar een Docker image en de Docker image start om de applicatie te starten. Kennis van Docker en terminal is hiervoor nodig. Gebruik eventueel de Terminal binnen IntelliJ wanneer je het project hebt geopend.  Zorg er voor dat Docker operationeel is op je systeem zodat je Docker images kan mounten en starten.

Building the Docker container
Wanneer het project in IntelliJ geopend is, kan de terminal geopend worden op de vanaf de content root van het project. Of anders gezegd, mount de directory van de root van het project in de terminal. Zorg er dus voor dat Docker operationeel is voordat je verder gaat met deze stap.
Voer nu onderstaand terminal commando uit. Dit commando zal een Dockerimage maken van het project.

````
./mvnw clean compile jib:dockerBuild -Djib.to.image=localbuild:latest
````
Als het maken van een Docker image is gelukt en voltooid, zal deze nu met het commando: docker images , een image moeten laten zien met de naam localbuild en tag latest

Running the Docker container with one of the Spring Profiles
We gaan nu de Docker image starten. Port 8080 moet vrij zijn voor de applicatie. Als deze door een andere applicatie wordt gebruikt kan de Docker image niet starten. Om de Docker image te starten kan je kiezen uit onderstaande terminal commando’s. Je kan één van de commando’s uitvoeren in hetzelfde terminal venster waar je zojuist ook de applicatie gecompileerd hebt naar een Docker image.
Nadat je een commando hebt uitgevoerd, zal de docker image starten en kan je de applicatie bereiken op http://localhost:8080. Je kan eventueel gebruik maken van de Swagger documentatie op http://localhost:8080/swagger-ui.html

Run de Docker image met Spring Profile en Database H2
````
docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=h2" localbuild:latest
````

Run de Docker image met Spring Profile dockerlocal en Database PostgresSQL op localhost:8080 port 5432
````
docker run -d --add-host host.docker.internal:host-gateway -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dockerlocal" localbuild:latest
````
s

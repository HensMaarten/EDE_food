# EDE Maarten Hens

_Ik heb het gehele project lokaal laten werken aangezien ik geen account meer kon aanmaken op Okteto._

Als thema voor het project koos ik ervoor om een backend te maken voor een webapplicatie waar niet ingelogde gebruikers recepten kunnen bekijken en ingelogde gebruikers recepten konden aanmaken, verwijderen en bewerken. Een recept kan natuurlijk niet bestaan zonder ingrediënten, en iets wat veel recepten vergeten te vermelden: het benodigde keukengerei.

![Alt text](/images/Deployment_diagram.jpg)

Zo hebben we dus ook meteen onze 3 microservices. De eerste ingredients-service maakt gebruik van een mongoDB. Ik koos hiervoor omdat ik vermoed dat de ingrediënten in de toekomst het snelst van datastructuur zou veranderen (bv. toevoegen van allergieën).

De ingredients-service bevat volgende endpoints:

- GET: /ingredients/all

![Alt text](/images/image.png)

- GET: /ingredients/getbyids?id={1}&id={2}

![Alt text](/images/image-1.png)

- PUT: /ingredients/{id}

![Alt text](/images/image-2.png)

- POST: /ingredients

![Alt text](/images/image-3.png)

- DELETE: /ingredients/{id}

![Alt text](/images/image-4.png)

De tweede microservice, de utensils-service is qua endpoints erg vergelijkbaar met de ingredients-service. Deze service maakt echter gebruik van een mysql database.

De utensils-service bevat volgende endpoints:

- GET: /utensil/all

![Alt text](/images/image-5.png)

- GET: /utensil/getbyids?id={id}&id={id}

![Alt text](/images/image-6.png)

- PUT: /utensil/{id}

![Alt text](/images/image-7.png)

- POST: /utensil

![Alt text](/images/image-8.png)

- DELETE: /utensil/{id}

![Alt text](/images/image-9.png)

De derde en laatste microservice, de recipes-service mag natuurlijk niet ontbreken als het project rond recepten draait. Deze microservice communiceert tijdens het uitvoeren van de GET endpoint 'recipe/complete/{id}' en de DELETE endpoint 'recipe/{id}' ook met de andere microservices. Bij de GET endpoint wordt het recept zelf opgehaald maar ook de ingrediënten en het keukengerei op basis van de id's. Hiervoor worden de 'GET: /utensil/getbyids?id={id}&id={id}' en 'GET: /ingredients/getbyids?id={1}&id={2}' endpoints gebruikt. Bij de DELETE endpoint van het recept worden ook de bijhorende ingrediënten en het benodigde keukengerei uit de database verwijderd.

De recipes-service bevat volgende endpoints:

- GET: /recipe/all

![Alt text](/images/image-10.png)

- GET: /recipe/{id}

![Alt text](/images/image-11.png)

- GET: /recipe/complete/{id}

![Alt text](/images/image-12.png)

- PUT: /recipe/{id}

![Alt text](/images/image-13.png)

- POST: /recipe

![Alt text](/images/image-14.png)

- DELETE: /recipe/{id}

![Alt text](/images/image-15.png)

Al deze endpoints kunnen aangeroepen worden vanuit mijn gateway die gebruik maakt van Google OAuth2. Alle get endpoints mogen aangeroepen worden zonder dat de gebruiker ingelogd is, voor alle andere endpoints dient de gebruiker in te loggen. Aangezien ik ervoor koos om als extra uitbreiding een front-end te maken diende ik ook een corsConfigurationSource toe te voegen aan de securityconfig in mijn gateway.

Uitbreidingen:

- Front-end

Ik koos ervoor om een front-end te maken voor mijn applicatie in Vue.js. Aangezien mijn containers lokaal draaien, kon ik deze front-end niet deployen.

- _Zet de deployment docker-compose.yml om naar__Kubernetes Manifest .yml-files_

Ik probeerde ook nog om mijn docker-compose.yml files om te zetten naar Kubernetes Manifest .yml-files, maar jammer genoeg werkte enkel mijn mongoDB service.
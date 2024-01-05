# EDE Project Maarten Hens

_Ik heb het gehele project lokaal laten werken aangezien ik geen account meer kon aanmaken op Okteto._

Als thema voor het project koos ik ervoor om een backend te maken voor een webapplicatie waar niet ingelogde gebruikers recepten kunnen bekijken en ingelogde gebruikers recepten konden aanmaken, verwijderen en bewerken. Een recept kan natuurlijk niet bestaan zonder ingrediënten, en iets wat veel recepten vergeten te vermelden: het benodigde keukengerei.

![](RackMultipart20240105-1-we1dfm_html_fbccbbc240108d1e.png)

Zo hebben we dus ook meteen onze 3 microservices. De eerste ingredients-service maakt gebruik van een mongoDB. Ik koos hiervoor omdat ik vermoed dat de ingrediënten in de toekomst het snelst van datastructuur zou veranderen (bv. toevoegen van allergieën).

De ingredients-service bevat volgende endpoints:

- GET: /ingredients/all

![](RackMultipart20240105-1-we1dfm_html_c52a0391f5483d0.png)

- GET: /ingredients/getbyids?id={1}&id={2}

![](RackMultipart20240105-1-we1dfm_html_ed9e85b616c846e4.png)

- PUT: /ingredients/{id}

![](RackMultipart20240105-1-we1dfm_html_27b29e947dfa189d.png)

- POST: /ingredients

![](RackMultipart20240105-1-we1dfm_html_2c2cacea26a7ea7.png)

- DELETE: /ingredients/{id}

![](RackMultipart20240105-1-we1dfm_html_eafaecbe28f6916f.png)

De tweede microservice, de utensils-service is qua endpoints erg vergelijkbaar met de ingredients-service. Deze service maakt echter gebruik van een mysql database.

De utensils-service bevat volgende endpoints:

- GET: /utensil/all

![](RackMultipart20240105-1-we1dfm_html_79c1421515d2b274.png)

- GET: /utensil/getbyids?id={id}&id={id}

![](RackMultipart20240105-1-we1dfm_html_fb2f64000599078.png)

- PUT: /utensil/{id}

![](RackMultipart20240105-1-we1dfm_html_7ace8da4b084a20e.png)

- POST: /utensil

![](RackMultipart20240105-1-we1dfm_html_4ebd3b66967a536e.png)

- DELETE: /utensil/{id}

![](RackMultipart20240105-1-we1dfm_html_853f6c6bdbe95948.png)

De derde en laatste microservice, de recipes-service mag natuurlijk niet ontbreken als het project rond recepten draait. Deze microservice communiceert tijdens het uitvoeren van de GET endpoint 'recipe/complete/{id}' en de DELETE endpoint 'recipe/{id}' ook met de andere microservices. Bij de GET endpoint wordt het recept zelf opgehaald maar ook de ingrediënten en het keukengerei op basis van de id's. Hiervoor worden de 'GET: /utensil/getbyids?id={id}&id={id}' en 'GET: /ingredients/getbyids?id={1}&id={2}' endpoints gebruikt. Bij de DELETE endpoint van het recept worden ook de bijhorende ingrediënten en het benodigde keukengerei uit de database verwijderd.

De recipes-service bevat volgende endpoints:

- GET: /recipe/all

![](RackMultipart20240105-1-we1dfm_html_935250198eee2ab0.png)

- GET: /recipe/{id}

![](RackMultipart20240105-1-we1dfm_html_8287a2776435d6a3.png)

- GET: /recipe/complete/{id}

![](RackMultipart20240105-1-we1dfm_html_a729dae99d1df11d.png) ![](RackMultipart20240105-1-we1dfm_html_ac0c7ead59ab26f0.png) ![](RackMultipart20240105-1-we1dfm_html_4de17b3bbb16ec06.png)

- PUT: /recipe/{id}

![](RackMultipart20240105-1-we1dfm_html_1a559997d04f02cb.png)

- POST: /recipe

![](RackMultipart20240105-1-we1dfm_html_c15ebb1b6e05c8ff.png)

- DELETE: /recipe/{id}

![](RackMultipart20240105-1-we1dfm_html_fe8bba6733de1d31.png)

Al deze endpoints kunnen aangeroepen worden vanuit mijn gateway die gebruik maakt van Google OAuth2. Alle get endpoints mogen aangeroepen worden zonder dat de gebruiker ingelogd is, voor alle andere endpoints dient de gebruiker in te loggen. Aangezien ik ervoor koos om als extra uitbreiding een front-end te maken diende ik ook een corsConfigurationSource toe te voegen aan de securityconfig in mijn gateway.

Uitbreidingen:

- Front-end

Ik koos ervoor om een front-end te maken voor mijn applicatie in Vue.js. Aangezien mijn containers lokaal draaien, kon ik deze front-end niet deployen.

- _Zet de deployment docker-compose.yml om naar__Kubernetes Manifest .yml-files_

Ik probeerde ook nog om mijn docker-compose.yml files om te zetten naar Kubernetes Manifest .yml-files, maar jammer genoeg werkte enkel mijn mongoDB service.
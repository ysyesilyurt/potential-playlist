# potential-playlist
A playlist maintainer backend written with Spring Framework. Developed it as a last
step of my [Spring-Workbench](https://github.com/ysyesilyurt/spring-workbench) and covered pretty much 
most of the stuff that I needed to practice in Spring for now, specifically below content is introduced and practiced 
on top of [my second workbench](https://github.com/ysyesilyurt/spring-workbench/tree/master/springDataAndMore):
* `Spring Hateoas` for ultra-Rest practices in Spring-based web applications.
* `Spring Security` for practicing security concerns (Authentication/Authorization and much more) of web applications and their management in a Spring-based backend. 
* `DTO`s along with `MapStruct` library to create handy `DTO`s and `Models` for internal usage in the application.
* `liquibase` for practicing manipulations of db tables more easily. 

## More About the Application
Simply put, this application is a backend that aims to serve services to multiple _potential-playlist_ users as a song and playlist platform (Just as Spotify's backend :blush:) 
from where users can create and maintain their favourite playlists and songs of their favourite albums and artists.

Currently there are 5 main models in various relations with each other in the app, which are Playlists, Songs, Albums, Artists and Users 
(Pretty much the users that application authenticates and authorizes). Below ER diagram illustrates the relations of models with each other (attributes except primary keys are excluded due to brevity):

[erDiagram]: https://github.com/ysyesilyurt/potential-playlist/blob/master/images/er.png 

![alt text][erDiagram]

```
(Not Null) User <- OneToMany -> Playlist
Playlist <- ManyToMany -> Song
Song <- ManyToOne -> Album (Not Null) 
Album <- ManyToOne -> Artist (Not Null) 
```

* Users can create multiple playlists and each playlist may have multiple songs.
* A song need to belong an album and an album need to belong to an artist.

#### Authentication & Authorization
All incoming requests are authenticated with `HTTP BASIC`, so requests' `authorization` header needs to be filled with user credentials beforehand. Also `CSRF` is disabled for this primitive version of backend, maybe activated later.

For the sake of api management issues and use cases, currently there are 2 roles which are `USER` and `ADMIN`.
An intended use case is where there is a user with the role of `ADMIN` and can consume all endpoints without any problem and there are other users 
with the role of `USER` and can only consume all the endpoints under `/api/playlists` without any authorization issue. For other endpoints, these users can only consume `GET` endpoints but not others. 

Current Spring Security configuration is set according to above guideline.

#### Testing
I created also a structured Postman collection to test each endpoint separately with a total of 29 requests. No unit tests are implemented with JUnit since 
Postman has been utilized fully for these purposes (However I may write some unit tests later just to practice Spring's Test, JUnit and Mockito).

## Future Potential-Playlist
I plan to use this project as a Spring Boot backend of a future _Single Page Application_ "Potential-Playlist" from where users can use the api to its full extent 
and not only maintain their favourite songs and playlists of their favourite albums and artists, 
but also actually play their songs in the app with an experience close to native applications.
 
Therefore I plan to develop a frontend architecture with probably Vue.js and Axios.js with "Potential-Playlist" name and this backend may evolve over time due 
to probable needs from frontend architecture.

### Build and run:

* On a Docker Container:
```
mvn clean package
sudo docker build -t potential-playlist .
sudo docker run -e"SPRING_PROFILES_ACTIVE=dev" --network="host" -d --rm --name potential-playlist potential-playlist
```

* On host machine:
```
mvn clean install
java -jar -Dspring.profiles.active=dev target/potential-playlist-1.0-SNAPSHOT.jar
```

## Contribution

I tried to cover as many cases as I can however weird bugs may still appear :blush:. Feel free to open an issue if you spot a bug. 

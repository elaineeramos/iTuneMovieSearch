# iTune Movie Search
This application displays a list of movies from iTunes.
The list of movies is obtained by using the iTunes Search API 
(https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/#searching).

The following data are displayed:
* Title of the movie
* Movie poster
* Price
* Genre of the movie

# Built With
* Model View ViewModel architectural pattern
* Room Persistence Library
* Glide Image Loading Library

# Model View ViewModel Architectural Pattern (MVVM)
This pattern is chosen so that there will be abstraction between the interface and the manipulation of data. 
The View is responsible for interacting with the user (i.e. displaying the data and reading user input).
The Model is responsible for fetching, storing and manipulating data. The Model notifies ViewModel of new or update in data.
The ViewModel is responsible for retrieving data from Model and for handling of data needed by the View.

# Room Persistence Library
This library is chosen since it provides an abstracted reading and writing of data to the local database.
Also, even if the user is unable to access to the internet, the user may still browse for movies if the information is cached in the local database.

# Glide Image Loading Library
This library is chosen since it provides loading an image when given a URL. Also, it saves images to cache memory, which provides faster loading time when image is re-loaded.

# Author
Elaine Ramos

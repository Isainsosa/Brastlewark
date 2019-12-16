# Brastlewark
Gnome directory to facility the Brastlewark inhabitants to find the census data

This project is built based in a MVVM architecture using a BaseFragmentController, a ViewModelRenderer and a BaseViewModel as principal components, in this way all components are be able to react to the actions performed in each component.

To load images from Internet I used Glide, its a library that helps to load the images and cache them to future requests
https://bumptech.github.io/glide/

To perform the HTTP Connections I used Retrofit, a lybrary that helps to manage the network connections in an optimized way.
https://square.github.io/retrofit/

The application are storing data in a locale database using Room
https://developer.android.com/jetpack/androidx/releases/room

I also separated in Android modules the libraries Network, Arco(here I defined the base of the architecture) and design to mantain separated the app functionallity.

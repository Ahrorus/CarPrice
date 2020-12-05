# CarPrice

>Java Swing MVC application that allows users to checkout car prices and modify them. <br/>Essentially, it is a kbb.com (Kelly Blue Book) prototype.

Application features:
1) Checkout an available car
2) Add a new car
3) Edit existing car
4) Delete existing car

Cars are saved in a serialized file. If the file gets deleted, the software "heals" itself and recreates the file with some initial test cars.


## Application Demo Recording

(insert video link here)


## Model Design

(insert image here)

Model uses the Singleton pattern, in which the car list is wrapped in a SingleCarList class that is shared across the application. <br/>
Model, View, and Controller classes are strictly separated, following the MVC pattern.


## Project Origins

This application was made as a semester team project for SJSU. <br/>
The collaborators: <br/>
  - Ahror Abdulhamidov - team leader (Me) <br/>
  - Dickson Minang <br/>
  - Basmah Altimimi <br/>

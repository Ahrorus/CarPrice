# CarPrice

>Java Swing MVC application that allows users to check out car prices and modify them. <br/>Essentially, it is a kbb.com (Kelly Blue Book) prototype.

To run the application, double click the **CarPrice.jar** file in the project folder.

Cars are saved in a serialized file. If the file gets deleted, the software "heals" itself and recreates the file with some initial test cars.


## Application's Screenshots

###Main Screen
![](screenshot_1.png)

###Checking Out the Existing Car
![](screenshot_2.png)

###Adding a New Car
![](screenshot_3.png)

###Editing the Existing Car
![](screenshot_4.png)

## Model Design

![](UML_Class_Diagram.png)

Model uses the Singleton pattern, in which the car list is wrapped in a SingleCarList class that is shared across the application. <br/>
Model, View, and Controller classes are strictly separated, following the MVC pattern.


## Project's Origins

This application was made as a semester team project for SJSU. <br/>
The collaborators: <br/>
  - Ahror Abdulhamidov - team leader (Me) <br/>
  - Dickson Minang <br/>
  - Basmah Altimimi <br/>

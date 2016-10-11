# Open Balloon Map

Geoinformation für alle – Luftballons im Dienste der Wissenschaft. Unter dem Motto "OpenBalloonMap" vermittelt das i3mainz Grundlagen der Geoinformatik und präsentiert Projekte aus der Umwelt- und Gesundheitsforschung. Die ersten Ergebnisse sind auf der Projektwebsite "openballoonmap.org" einsehbar.

**OpenBallonMap** is a free available balloonmap using Java (Spring Boot, Spring MVC, Spring Data JPA, Hibernate Spatial, Spring Security, ...) / JavaScript (leaflet, jQuery, ...) / Thymeleaf and a PostGIS / GeoServer connection.

Here, the sourcecode of the server and client application is published.

The project builds on top of Spring Boot and generates fully runable JAR file.  

You have to run this JAR file:
java -jar JAR-FILE 
The configuration can be changed through application.properties or application.yml file, also setting environment variables or add properties to the java run call. Database tables can be build or updated via JPA functionality automatically. Please configure this also through the parameter. More you find in the reference of [Spring Boot](https://projects.spring.io/spring-boot/). 

Want to see the app? [See the application.](http://openballoonmap.org)

Want to see the data? [See the GeoServer Layers.](http://openballoonmap.org/geoserver/web/?wicket%3AbookmarkablePage=%3Aorg.geoserver.web.demo.MapPreviewPage)

More about the project you can find at the [i3mainz](http://i3mainz.hs-mainz.de/en/projekte/openballoonmap) website.

## Licence

The MIT License (MIT)

Copyright (c) 2016 [i3mainz](http://i3mainz.hs-mainz.de/en/institute)**

**Florian Thiery M.Sc.

**Martin Unold M.Sc.

**Axel Kunz M.Sc.

**Nikolai Bock M.Eng.

**[i3mainz](http://i3mainz.hs-mainz.de/en/institute) - Institute for Spatial Information and Surveying Technology

**Hochschule Mainz University of Applied Sciences

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

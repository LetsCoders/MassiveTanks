Massive Tanks 1.0
=============

Massive Tanks is a classic tanks game remade as an MMO. It's purely browser based on the client side, built with awesome
 [Phaser](http://phaser.io/) framework. Server is written in Java with [Atmosphere](https://github.com/Atmosphere/atmosphere) 
framework at it’s core for WebSocket communication.

Base of this project was created during a 10-hours long development tournament. After that event, we took our time to 
refactor the code and optimize it. Live demo is available (though on a very average server) under [this](http://massivetanks.com) URL.

Project structure
-------------
Structure is enforced by [Maven](http://maven.apache.org/), on which this project depends. JavaScript and in general 
frontend files are located in `src/main/webapp/client`, while whole backend is placed in `src/main/java`.

Project can be build using two available **profiles**: development and production (all configured in *pom.xml* file). 
Production minifies all JavaScript and CSS files, replacing their references in `client/index.html` and `index.jsp` 
files with pointers to minified versions. Development is meant for debug deployments and doesn’t perform any extra 
operations beside standard build process.

To **deploy** the server on the embedded tomcat clone this repository, navigate to project’s root folder and issue 
the following command: `mvn tomcat7:run -P production`.

Game will be available at this URL: `http://localhost:8080/`

Technologies
-------------
Frontend: 

* [Phaser](http://phaser.io/) - JavaScript game framework,
* [Lo-Dash](http://lodash.com/) - the utility belt.

Backend:

* [Atmosphere](https://github.com/Atmosphere/atmosphere) - framework for WebSocket communication,
* [dyn4j](http://dyn4j.org/) - physics engine and collision detection for two dimensional world,
* [Google Guice](https://code.google.com/p/google-guice/) - dependency injection,
* [Google Guava](https://code.google.com/p/guava-libraries/) - all those little improvements,
* [Jackson](https://github.com/FasterXML/jackson) - mapping objects to JSON and the other way around. Required to send messages to clients,
* [SLF4J](http://www.slf4j.org/) + [Logback](http://logback.qos.ch/) - logging framework.

Team Members
-------------
* [Bartosz Augustyn](https://github.com/crymoer)
* [Piotr Jankowski](https://github.com/petejank), [http://likeadev.com](http://likeadev.com)
* [Tomasz Krug](https://github.com/Edhendil)

Contact
-------------
If you would like to contact us feel free to use [this](massivetanks@gmail.com) e-mail.

License
-------------
Massive Tanks is licensed under the MIT license. All sprites, beside logo, are licensed under 
[CPL 1.0](http://opensource.org/licenses/cpl1.0.php) license and came 
from [WidgetWorx's SpriteLib](http://www.widgetworx.com/spritelib/)
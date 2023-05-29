ATQA
====

* To build: `make`
* To run: `make run`
* For help: `make help`

System requirements: 
--------------------

JDK 20 is _required_

Developed in two environments:
* MacBook Pro with OS 12.0.1, with OpenJDK 20, GNU Make 3.81 and Rsync 2.6.9
* Windows 10 64-bit professional, on Cygwin, OpenJDK 20, Gnu Make 4.4 and Rsync 3.2.7

First-time use
--------------

1. Start the application: `make clean run`
2. Go to http://localhost:8080
3. Soak up the austere beauty of the homepage
4. Click on **Auth**
5. Click on **Register**
6. Enter some credentials, such as _foo_ and _bar_, click Enter
7. Enter them again (you're logging in this time)
8. Click on **Sample domain**
9. Click on **Enter a name**
10. Enter some names, pressing **Enter** each time.
11. Click **Index**
12. Click on **Photos**
13. Click on **Upload**
14. Select an image, enter a short caption and longer description 
15. Click **Submit**
16. Click **Index**
17. Click on **Auth**
18. Click on **Logout**
19. Go view the other pages again

Directories:
------------

- src: All the source code, including production and test code
- docs: documentation for the project
- lib: essential utilities and dependencies
- .git: necessary files for Git.
- utils: useful software for our system

Root-level files:
-----------------

- app.config: a configuration file for the running app (a local / test-oriented version)
- .gitignore: files we want Git to ignore.
- Makefile: the configuration for Gnu Make, which is our build tool
- README.md: this file

# Medware Automation Project


## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

* Please verify you got Java 8 and above installed on your local machine.
* You can run the program using your favorite IDE or using maven (will be explained later)
* Maven version 3.6.3

### Before Running 
* Before running please set all the requirement used under this file: 
```
src/main/java/resources/data.properties
```
* please be aware that there are 2 excel files added to the project in order to simulate a case where the database got the correct info and the database got the incorrect info.

  You can find those excel files under:
  
  ```
  src/main/java/resources/medi.xlsx
  src/main/java/resources/medi_malformed.xlsx
  ```
* please be aware of the screenshot of how the table created, images can be found under:

```
\medware\2020-08-21 13_20_23-MySQL Workbench.png
\medware\2020-08-21 13_21_33-MySQL Workbench.png
```

### Running Tests
* running using maven: (verify you are located in the source path, e.g: '\medware')
    
    please run this commands:
    
 ```
  1. mvn clean
  2. mvn compile
  3. mvn test
  ```
  The above commands will run the program 
 
### Tests Results
 
* logs can be found under:

```
   logs
```
* output files (report success/failure file) can be found under:
```
src/main/java/output
```
(only after the program ran at least once)
 
## Authors

 **Erez Naim** - *Senior Automation Engineer* 

Enjoy! :)
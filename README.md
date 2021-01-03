# request-parser

## Prerequisites 
* [Maven](https://maven.apache.org/)
* java8 or 11

## Getting Started 
First run the following command

``` sh
$ git clone https://github.com/halkichi0308/request-parser
$ cd request-parser
$ mvn install && mvn compile
```

Maven build `request-parser-v1.x.jar` in the current directory.   
Now `request-parser` should be available.

## Composition
``` sh
├── src        # To change request parser only directories below this level
│   ├── main
│   │   └── java
│   │       └── burp
│   │           ├── BurpExtender.java
│   │           └── com
│   │               ├── App.java  # Not required to build Maven, Used to sandbox.
│   │               ├── burp      # request parser source codes.
│   │               └── org       # The burp tool needs to arrange this folder to use apache libraries.
│   └── test
└── target     # This folder is the maven default output folder.
```

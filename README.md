# request-parser
Request parser is very nice burp extender. This extender helps all pentester when getting evidence. Request parser can dump evidence easy to use request.

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
│   │               └── org       # Don't move and any change. The burp tool needs to arrange this folder to use apache libraries.
│   └── test
└── target     # This folder is the maven default output folder.
```

## Burp Extender Usage
It's easy way to use request parser by following these steps.
[Extender] -> [Extentions] -> [Add] -> [Extention Details]


And [Request Paeser] tab will appear in the burp tab line. 

## Debugging
There are two ways to debug method. for now.
1. Using IDE.
2. Using Burp extender output console.

# ilp-java-example
Simple example of ILP forwarded payments sender / connector / receiver in Java

Terminal window 1 (compile and run receiver):
```sh
javac *.java
java Receiver
```

Terminal window 2 (run connector):
```sh
java Connector
```

Terminal window 3 (run sender):
```sh
curl  -H"ILP-Condition: c" -H"ILP-Destination: d" -H"ILP-Expiry: e" -H"ILP-Amount: 10" http://localhost:8080/asdf
```

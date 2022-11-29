Run this to build the client stub
change the jdk path to your machine appropriately

made the changes in demoWs.java file then run this command in intelij terminal 👇 then eventually generate the stub folder, and then you can use the generated function in main.java.

run in the terminal 👇
wsimport.exe -keep -d ..\DemoWebServiceClient\Stub -p com.sltc.soa.client.stub http://localhost:8888/DemoWebService?wsdl
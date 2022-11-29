package com.sltc.soa;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class DemoWS {
    Client connectedClient;
    @WebMethod
    public int authoriseUser(String userName, int password) {
        if(Client.getUsernameList().contains(userName)){
            connectedClient = Clients.clientProfilesByUserName.get(userName);
            if(connectedClient.getPassword() == password){
                connectedClient.showClient();
                return 1;
            }
            return -1;
        }
        else
            return -2;
    }
    @WebMethod
    public boolean checkUserName(String userName){
        if(Client.getUsernameList().contains(userName))
            return true;
        else
            return false;
    }
    @WebMethod
    public boolean checkUserAccount(String userAcc){
        if(Client.getAccountNumberList().contains(userAcc))
            return true;
        else
            return false;
    }
    @WebMethod
    public boolean checkNIC(String nic){
        if(Client.getNICList().contains(nic))
            return true;
        else
            return false;
    }


    @WebMethod
    public int addNewClient(String firstName, String lastName, String nic, String username, int password, float amount){
        Client temporaryClient = new Client(firstName,lastName,nic,username,password,amount);
        Clients.clientProfilesByUserName.put(username,temporaryClient);
        Clients.userNameByAccountNumber.put(temporaryClient.getAccountNumber(),username);
        System.out.println(temporaryClient.getAccountNumber()+" Account created for "+username+" user.");
        return 0;
    }

    @WebMethod
    public int deposit(float amount, String username){
        connectedClient.deposit(amount);
        return 1;
    }
    @WebMethod
    public int withdraw(float amount, String username){
        return connectedClient.withdraw(amount);
    }
    @WebMethod
    public int transfer(String username, float amount, String userAccount){
        return connectedClient.transfer(amount,userAccount);
    }
    @WebMethod
    public float getBalance(String username){
        float balance = Clients.clientProfilesByUserName.get(username).getBalance();
        System.out.println( " Updated Balance of "+username+" is RS: " + balance+"/=");
        return balance;
    }
    @WebMethod
    public String getAccountNumber(String username){
        String accountNumber = Clients.clientProfilesByUserName.get(username).getAccountNumber();
        return accountNumber;
    }

    public static void main(String[] args){
        Endpoint.publish("http://localhost:8888/DemoWebService", new DemoWS());
        System.out.println("==================================================================");//Display that sever was began.
        System.out.println("====================== Bank Server Starts =======================");
        System.out.println("==================================================================");

    }
}

package com.company;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;


class Patient{

    private final String name;
    private final float temperature;
    private final int oxygen,age,id;
    private int recoverydays;
    private String status;
    private String hospitalName;
    public Patient(int id,String Name,float temp,int oxy,int age){
        this.id=id;
        name=Name;
        temperature=temp;
        oxygen=oxy;
        this.age=age;
        recoverydays=-1;
        status="Not Admitted";
        hospitalName="No hospital";
    }

    public float getTemp(){
        return this.temperature;
    }
    public int getOxygen(){
        return this.oxygen;
    }
    public int getAge(){
        return this.age;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getStatus(){
        return this.status;
    }
    public int getRecoverydays(){
        return this.recoverydays;
    }
    public void setStatus(){
        this.status="Admitted";
    }
    public void setHospitalName(String name){
        this.hospitalName=name;
    }
    public void setRecoverydays(int days){
        this.recoverydays=days;
    }

    public void display(){
        System.out.println("Name-"+name);
        System.out.println("Temperature is "+temperature);
        System.out.println("Oxygen level is "+oxygen);
        System.out.println("Admission status- "+status);
        if(status.equals("Admitted")){
            System.out.println("Admitting Institute- "+hospitalName);
        }
    }

}


class Hospital{
    private final String name;
    private final float temperature;
    private final int oxygen;
    private int bedsavailable;
    private String status;
    private ArrayList<Patient> allpatients=new ArrayList<>();

    public Hospital(String Name,float temp,int oxy,int beds){
        name=Name;
        temperature=temp;
        oxygen=oxy;
        bedsavailable=beds;
        if(beds>0){
            status="OPEN";
        }
        else{
            status="CLOSED";
        }
    }

    public void display(){
        System.out.println(name);
        System.out.println("Temperature should be <= "+temperature);
        System.out.println("Oxygen levels should be >= "+oxygen);
        System.out.println("Number of Available beds - "+bedsavailable);
        System.out.println("Admission Status - "+status);
    }

    public void insertpatients(@NotNull ArrayList<Patient> incoming){
        for(Patient i:incoming){
            if(i.getOxygen()>=this.oxygen&&i.getStatus().equals("Not Admitted")&&this.bedsavailable>0){
                this.allpatients.add(i);
                this.bedsavailable--;
                i.setStatus();
                i.setHospitalName(this.name);
            }
        }
        if(this.bedsavailable>0){
            for(Patient i:incoming){
                if(i.getTemp()<=this.temperature&&i.getStatus().equals("Not Admitted")&&this.bedsavailable>0){
                    this.allpatients.add(i);
                    this.bedsavailable--;
                    i.setStatus();
                    i.setHospitalName(this.name);
                }
            }
        }
        if(bedsavailable==0){
            status="CLOSED";
        }
    }
    public ArrayList<Patient> getAllpatients(){
        return this.allpatients;
    }

    public String getName(){
        return this.name;
    }
    public String getStatus(){
        return this.status;
    }
}


class Camp{

    private ArrayList<Patient> patients;
    private ArrayList<Hospital> hospitals;

    public Camp(){
        this.patients=new ArrayList<>();
        this.hospitals=new ArrayList<>();
    }

    public void deletePatient(){
        ArrayList<Patient> deleteList=new ArrayList<>();
        System.out.println("Account IDs of admitted patients which were removed");
        for(Patient i:patients){
            if(i.getStatus().equals("Admitted")){
                System.out.println(i.getId());
                deleteList.add(i);
            }
        }
        for(Patient i:deleteList){
            patients.remove(i);
        }
    }
    public void deleteHospital(){
        ArrayList<Hospital> deleteList=new ArrayList<>();
        System.out.println("Names of removed health care institutes which were closed");
        for(Hospital i:hospitals){
            if(i.getStatus().equals("CLOSED")){
                System.out.println(i.getName());
                deleteList.add(i);
            }
        }
        for(Hospital i:deleteList){
            hospitals.remove(i);
        }
    }

    public ArrayList<Patient> getPatients(){
        return this.patients;
    }
    public ArrayList<Hospital> getHospitals(){
        return this.hospitals;
    }
    public void setPatients(Patient patient){
        this.patients.add(patient);
    }
    public void setHospitals(Hospital hospital){
        this.hospitals.add(hospital);
    }
    public int getPatientCount(){
        int count =0;
        for(Patient i: this.patients){
            if(i.getStatus().equals("Not Admitted")){
                count++;
            }
        }
        return count;
    }
    public int getHospitalCount(){
        int count =0;
        for(Hospital i: this.hospitals){
            if(i.getStatus().equals("OPEN")){
                count++;
            }
        }
        return count;
    }

}



public class Main {

    public static boolean alldone(ArrayList<Patient> patients){
        for(Patient i:patients){
            if(i.getStatus().equals("Not Admitted")){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Camp manager=new Camp();
        int idcounter=1;
        int n=in.nextInt();

        for(int i=0;i<n;i++){
            String patientName=in.next();
            float patientTemp=in.nextFloat();
            int patientOxy=in.nextInt();
            int patientAge=in.nextInt();
            manager.setPatients(new Patient(idcounter++,patientName,patientTemp,patientOxy,patientAge));
        }

        while(!alldone(manager.getPatients())){
            String choice;
            System.out.println("Press the number in order to select your query");
            System.out.println("1-Add Health Care Institute ");
            System.out.println("2-Remove the accounts of admitted patients");
            System.out.println("3-Remove the accounts of health care institutes whose applications are closed");
            System.out.println("4-Display number of patients still in the camp (not admitted yet)");
            System.out.println("5-Display number of health care institutes admitting patients currently (Open)");
            System.out.println("6-Display details of the Health Care Institute");
            System.out.println("7-Display details of particular patient");
            System.out.println("8-Display all patients");
            System.out.println("9-Display names of patients admitted in an Institute and their recovery time");
            choice=in.next();


            if(choice.equals("1")){
                System.out.println("Enter the name of the hospital");
                String name=in.next();
                System.out.println("Enter the temperature criteria");
                float temp=in.nextFloat();
                System.out.println("Enter the oxygen criteria");
                int oxy=in.nextInt();
                System.out.println("Enter the total available beds");
                int beds=in.nextInt();
                Hospital test;
                test=new Hospital(name,temp,oxy,beds);
                test.display();
                test.insertpatients(manager.getPatients());
                for(Patient i:test.getAllpatients()){
                    System.out.println("Enter the recovery days of admitted patient with ID-"+i.getId());
                    i.setRecoverydays(in.nextInt());
                }
                manager.setHospitals(test);

            }

            else if(choice.equals("2")){
                manager.deletePatient();
            }
            else if(choice.equals("3")){
                manager.deleteHospital();
            }
            else if(choice.equals("4")){
                int count= manager.getPatientCount();
                if(count==1||count==0) {
                    System.out.println(count + " patient");
                }
                else{
                    System.out.println(count+" patients");
                }
            }
            else if(choice.equals("5")){
                int count= manager.getHospitalCount();
                if(count==1||count==0) {
                    System.out.println(count + " institute is admitting patients currently");
                }
                else{
                    System.out.println(count+" institues are admitting patients currently ");
                }
            }
            else if(choice.equals("6")){
                String healthCareName=in.next();
                for(Hospital i:manager.getHospitals()){
                    if(i.getName().equals(healthCareName)){
                        i.display();
                    }
                }
            }
            else if(choice.equals("7")){
                int idNumber=in.nextInt();
                for(Patient i: manager.getPatients()){
                    if(i.getId()==idNumber){
                        i.display();
                    }
                }
            }
            else if(choice.equals("8")){
                for(Patient i: manager.getPatients()){
                    System.out.println(i.getId()+"  "+i.getName());
                }
            }
            else if(choice.equals("9")){
                String hospitalName=in.next();
                for(Hospital i: manager.getHospitals()){
                    if(i.getName().equals(hospitalName)) {
                        for (Patient j : i.getAllpatients()) {
                            System.out.println(j.getName() + ", Recovery time is " + j.getRecoverydays() + " days");
                        }
                    }
                }
            }
            else{
                System.out.println("Wrong key has been pushed, choose from the list given");
            }
        }
        System.out.println("All patients are admitted hence closing the application ");
        System.exit(0);

    }
}

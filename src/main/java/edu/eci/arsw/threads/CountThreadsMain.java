/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        Thread h1=new CountThread(1,999);
        Thread h2=new CountThread(1000,9999);
        Thread h3=new CountThread(10000,50000);
        h1.start();h2.start();h3.start();
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdatabasemanagement;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Yara
 */
public class Encryptor   {
    public String encryptPassword(String password) throws NoSuchAlgorithmException{
        MessageDigest md= MessageDigest.getInstance("MD5");
        byte[] messageDigest= md.digest(password.getBytes());
        BigInteger bigInt= new BigInteger( 1,messageDigest);
        return bigInt.toString(16);
    }
    
}

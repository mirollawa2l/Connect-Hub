/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Roles;

import Groups_Backend.Admin;
import Groups_Backend.Member;
import Groups_Backend.SubAdmin;
import userdatabasemanagement.User;

/**
 *
 * @author mirol
 */
public class RolesAssigner {
    public Member asMember(User user) {
        Member member = new Member(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getDateOfBirth(),user.getStatus());
        return member;
    }

    public Admin asAdmin(User user) {
        Admin admin = new Admin(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getDateOfBirth(),user.getStatus());
        return admin;
    }
    public SubAdmin asSubAdmin(User user)
    {
        SubAdmin subAdmin=new SubAdmin(user.getId(),user.getEmail(),user.getUsername(),user.getPassword(),user.getDateOfBirth(),user.getStatus());
        return subAdmin;
    }
}

    
   

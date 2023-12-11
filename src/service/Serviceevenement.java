/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.Iservice;
import tools.MyDB;
import entity.Evenement;
import entity.Participant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leith
 */
public class Serviceevenement implements Iservice<Evenement> {
    
    Connection con; 
    Statement ste;
    public Serviceevenement(){
        con = MyDB.getinstance().getCon();
    }
        @Override
    public void ajouterevenement(Evenement e) {
     try {
        String req = "INSERT INTO event(name, price, date, description, image,place) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pre = this.con.prepareStatement(req);
        pre.setString(1, e.getName());
        pre.setDouble(2, e.getPrice());
        pre.setString(3, e.getDate());
        pre.setString(4, e.getDescription());
        pre.setString(5, e.getImage());
        pre.setString(6, e.getPlace());
        pre.executeUpdate();
    } catch (SQLException var4) {
        System.out.println(var4);
    }
}

    @Override
    public void supprimerevenement(Evenement e) {
        try {
        String req = "DELETE FROM event WHERE id = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, e.getId());
        pre.executeUpdate();
        
        System.out.println("Event with ID " + e.getId() + " has been deleted successfully.");
    } catch (SQLException ex) {
        System.err.println("Error deleting event: " + ex.getMessage());
    }

    }

    @Override
    public void modifierevenement(Evenement e) {
        try {
        String req = "UPDATE event SET name=?, price=?, date=?, description=?, image=? , place=? WHERE id=?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, e.getName());
        pre.setDouble(2, e.getPrice());
        pre.setString(3, e.getDate());
        pre.setString(4, e.getDescription());
        pre.setString(5, e.getImage());
        pre.setString(6, e.getPlace());
        pre.setInt(7, e.getId());
        pre.executeUpdate();
        
        System.out.println("Event with ID " + e.getId() + " has been updated successfully.");
    } catch (SQLException ex) {
        System.err.println("Error updating event: " + ex.getMessage());
    }
    }

    @Override
    public List<Evenement> affihcerevenement() {
        List<Evenement> evenements = new ArrayList<>();
        String sql ="select * from event";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                Evenement p = new Evenement(rs.getInt("id"),rs.getString("name"), rs.getDouble("price"),rs.getString("date"),rs.getString("description"),rs.getString("image"),rs.getString("place"));
                evenements.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenements;   
    }
    
    public List<Participant> affihcerparticiapnt() {
        List<Participant> evenements = new ArrayList<>();
        String sql ="select * from participant";
        try {
            ste= con.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while(rs.next()){
                Participant p = new Participant(rs.getInt("id"),rs.getInt("id_event"), rs.getString("user_name"),rs.getString("user_email"));
                evenements.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenements;   
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.util.List;

/**
 *
 * @author leith
 */
public interface Iservice<E>{
    void ajouterevenement(E e);
    void supprimerevenement(E e);
    void modifierevenement(E e);
    List<E> affihcerevenement();
    
}

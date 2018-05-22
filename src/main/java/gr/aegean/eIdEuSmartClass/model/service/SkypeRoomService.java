/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service;

import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import java.util.List;

/**
 *
 * @author nikos
 */
public interface SkypeRoomService {

    public List<SkypeRoom> getAllRooms();

    public SkypeRoom getRoomFromId(String id);

    public void save(SkypeRoom room);

    public void delete(String id);
}

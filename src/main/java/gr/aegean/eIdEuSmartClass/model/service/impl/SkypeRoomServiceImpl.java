/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import gr.aegean.eIdEuSmartClass.model.dao.SkypeRoomRepository;
import gr.aegean.eIdEuSmartClass.model.dmo.SkypeRoom;
import gr.aegean.eIdEuSmartClass.model.service.SkypeRoomService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nikos
 */
@Service
public class SkypeRoomServiceImpl implements SkypeRoomService {

    @Autowired
    private SkypeRoomRepository roomRepo;

    @Override
    @Transactional
    public List<SkypeRoom> getAllRooms() {
        return roomRepo.findAll().stream().sorted( (room1, room2) -> 
        room2.getStart().compareTo(room1.getStart())).collect(Collectors.toList());
    }

    @Override
    public SkypeRoom getRoomFromId(String id) {
        Optional<SkypeRoom> room = roomRepo.findById(Long.parseLong(id));
        return (room.isPresent()) ? room.get() : null;
    }

    @Override
    public void save(SkypeRoom room) {
        roomRepo.save(room);
    }

    @Override
    @Transactional
    @Modifying
    public void delete(String id) {
        roomRepo.deleteRoom(Long.parseLong(id));
    }

}

package com.ecishifts.appcore.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import org.springframework.stereotype.Service;

import com.ecishifts.appcore.Model.Shift;
import com.ecishifts.appcore.Model.ShiftStatus;
import com.ecishifts.appcore.Model.User;
import com.ecishifts.appcore.Repositories.ShiftRepository;



@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final UserService userService;
    public ShiftService(ShiftRepository shiftRepository, UserService userService) {
        this.shiftRepository = shiftRepository;
        this.userService = userService;
    }

    public void generateShift(Shift shift){
        User User = userService.getUserByMail(shift.getUserId());
        String specialty = shift.getSpecialty();
        Prefix prefix;
        switch (specialty) {
            case "Psicologia":
                prefix = Prefix.PS;
                break;
            case "Medicina General":
                prefix = Prefix.MG;
                break;
            case "Ortopedia":
                prefix = Prefix.OR;
            default:
                prefix = Prefix.OD;
                break;
        }                 
    
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
    
        long count = shiftRepository.countBySpecialtyAndCreatedAtBetween(
                specialty, startOfDay, endOfDay);

        long nextNumber = count + 1;
        shift.setTurnCode(prefix + "-" + nextNumber);
        shift.setStatus(ShiftStatus.ASSIGNED);
        shift.setCreatedAt(LocalDateTime.now());
        shift.setUsername(User.getName());
        shift.setUserId(User.getMail());
        shift.setUserRole(User.getType());
        shiftRepository.insert(shift);
    }
    

    public List<Shift> getShifts(){
        return shiftRepository.findAll();
    }

    /**
     * This method first checks whether a shift with the given ID exists in the database.
     * If it does, it proceeds to delete it. If not, it throws an IllegalArgumentException.
     * 
     * We use optional since we are expecting one or no results by searching for the ID.
     * 
     * @param id the unique identifier of the shift to delete; must not be null or empty.
    */
    public void deleteShift(String id){
        Optional<Shift> shift = shiftRepository.findById(id);
        if (shift.isPresent()) {
            shiftRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No shift found with ID: " + id);
        }
    }

    /**
     * This method searches for all shifts starting from a role
     * @param role: this role is defined in the Shift class
     * @return a list of shifts that match the role
     */
    public List<Shift> getShiftsByRole(String role) {
        List<Shift> shifts = shiftRepository.findByUserRole(role);
        if (shifts == null || shifts.isEmpty()) {
            throw new IllegalArgumentException("No shifts found for role: " + role);
        }
        return shifts;
    }

    /**
     * This method searches for all shifts starting from a role
     * @param role: this role is defined in the Shift class
     * @return a list of shifts that match the role
     */
    public List<Shift> getShiftsByUserId(String UserID) {
        List<Shift> shifts = shiftRepository.findByUserId(UserID);
        if (shifts == null || shifts.isEmpty()) {
            throw new IllegalArgumentException("No shifts found for role: " + UserID);
        }
        return shifts;
    }

    public Shift getShiftByTurnCode(String code) {
        Optional<Shift> shift = shiftRepository.findByTurnCode(code);
        if (shift.isPresent()) {
            return shift.get(); 
        } else {
            throw new IllegalArgumentException("No shift found with ID: " + code);
        }
    }

    public String deleteShiftByTurnCode(String turnCode) {
        Optional<Shift> shift = shiftRepository.findByTurnCode(turnCode);
        if (!shift.isPresent()) {  
            throw new RuntimeException("No shift found with turnCode: " + turnCode);
        }    
        shiftRepository.delete(shift.get());
        return turnCode;
    }

    public Shift getShiftById(String id) {
        Optional<Shift> shift = shiftRepository.findById(id);
        if (shift.isPresent()) {
            return shift.get(); 
        } else {
            throw new IllegalArgumentException("No shift found with ID: " + id);
        }
    }
}
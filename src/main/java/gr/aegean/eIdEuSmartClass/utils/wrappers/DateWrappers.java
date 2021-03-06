/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.wrappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author nikos
 */
public class DateWrappers {

    public static LocalDate parseEidasDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return !StringUtils.isEmpty(date)? LocalDate.parse(date, formatter):null;
    }
    
    public static Timestamp getNowTimeStamp(){
        return Timestamp.valueOf(LocalDateTime.now());
    }
    
    
    public static LocalDateTime parseDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return !StringUtils.isEmpty(date)?LocalDateTime.parse(date, formatter):null;
    }

    
}

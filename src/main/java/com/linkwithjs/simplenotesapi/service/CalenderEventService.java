package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.BaseEntity;
import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.CalenderEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CalenderEventService {

    @Autowired
    private CalenderEventRepository calenderEventRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ReqRes createEvent(CalenderEventDTO events) {
        ReqRes resp = new ReqRes();
        BaseEntity baseEntity = new BaseEntity();
        try {
            CalenderEventEntity calenderEvent = new CalenderEventEntity();
            calenderEvent.setTitle(events.getTitle());
            calenderEvent.setDescription(events.getDescription());
            calenderEvent.setStartDateTime(events.getStartDateTime());
            calenderEvent.setEndDateTime(events.getEndDateTime());
            calenderEvent.setLocation(events.getLocation());
            calenderEvent.setCreatedAt(baseEntity.getCreatedAt());
            CalenderEventEntity saveEvent = calenderEventRepository.save(calenderEvent);
            if (saveEvent.getId() != null) {
                resp.setData(saveEvent);
                resp.setMessage("Calender event added successfully.");
            } else {
                resp.setMessage("Calender event could not add.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> readAllEvents() {
        try{
            List<CalenderEventDTO> events = calenderEventRepository.findAll().stream()
                    .map(event -> modelMapper.map(event, CalenderEventDTO.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Events fetched successfully!", events);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch events", e.getMessage()));
        }

    }

    public ReqRes deleteEvent(int id) {
        ReqRes resp = new ReqRes();
        try{
            CalenderEventEntity event = calenderEventRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Event not found for this id : " + id));
            calenderEventRepository.delete(event);
            resp.setStatusCode(200);
            resp.setData(event);
            resp.setMessage("Event Deleted Successfully.");
        }catch (CustomException e){
            resp.setStatusCode(404);
            resp.setError(e.getMessage());
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes updateEvent(int id, CalenderEventDTO events) {
        ReqRes resp = new ReqRes();
        BaseEntity baseEntity = new BaseEntity();
        try {
            CalenderEventEntity calenderEvent = calenderEventRepository.findById(id).
                    orElseThrow(() -> new CustomException("Error: Event not found for this id : " + id));

            calenderEvent.setTitle(events.getTitle());
            calenderEvent.setDescription(events.getDescription());
            calenderEvent.setStartDateTime(events.getStartDateTime());
            calenderEvent.setEndDateTime(events.getEndDateTime());
            calenderEvent.setLocation(events.getLocation());
            calenderEvent.setUpdatedAt(baseEntity.getUpdatedAt());
            CalenderEventEntity saveEvent = calenderEventRepository.save(calenderEvent);
            if (saveEvent.getId() != null) {
                resp.setData(saveEvent);
                resp.setMessage("Calendar event updated successfully.");
            } else {
                resp.setMessage("Calendar event could not update.");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes changeIsAllDay(int id){
        ReqRes resp = new ReqRes();

        try{
            CalenderEventEntity calenderEvent = calenderEventRepository.findById(id).
                    orElseThrow(() -> new CustomException("Error: Event not found for this id : " + id));
            calenderEvent.setAllDay(!calenderEvent.isAllDay());
            calenderEventRepository.save(calenderEvent);
            resp.setData(calenderEvent);
            resp.setMessage("Calendar event changed. ");
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

}

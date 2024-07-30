package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.CalenderEventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalenderEventService {

    @Autowired
    private CalenderEventRepository calenderEventRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ReqRes createEvent(CalenderEventDTO events) {
        ReqRes resp = new ReqRes();
        try {
            CalenderEventEntity calenderEvent = new CalenderEventEntity();
            calenderEvent.setTitle(events.getTitle());
            calenderEvent.setDescription(events.getDescription());
            calenderEvent.setStartDateTime(events.getStartDateTime());
            calenderEvent.setEndDateTime(events.getEndDateTime());
            calenderEvent.setLocation(events.getLocation());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // Check if the authentication object is not null and is authenticated
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                UserEntity user = (UserEntity) authentication.getPrincipal();
                calenderEvent.setUser(user);
                CalenderEventEntity saveEvent = calenderEventRepository.save(calenderEvent);
                if (saveEvent.getId() != null) {
                    resp.setData(saveEvent);
                    resp.setMessage("Calender event added successfully.");
                } else {
                    resp.setMessage("Calender event could not add.");
                }

            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;

    }

    public ResponseEntity<?> readAllEvents() {
//        try {
//            List<CalenderEventDTO> events = calenderEventRepository.findAll().stream()
//                    .map(event -> modelMapper.map(event, CalenderEventDTO.class))
//                    .collect(Collectors.toList());
//            return ReqRes.successResponse("Events fetched successfully!", events);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(ReqRes.successResponse("Failed to fetch events", e.getMessage()));
//        }

        try {
            List<CalenderEventDTO> events = calenderEventRepository.findAllEvents().stream()
                    .map(event ->
                            modelMapper.map(
                                    event,
                                    CalenderEventDTO.class))
                    .collect(Collectors.toList());

            return ReqRes.successResponse("Events fetched successfully!", events);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch events", e.getMessage()));
        }

    }
//    private CalenderEventDTO prepareEventData(CalenderEventEntity calenderEventEntity){
//        return CalenderEventDTO.builder().email(calenderEventEntity.getUser().getEmail()).build();
//    }

    public ReqRes deleteEvent(int id) {
        ReqRes resp = new ReqRes();
        try {
            CalenderEventEntity event = calenderEventRepository.findById(id)
                    .orElseThrow(() -> new CustomException("Event not found for this id : " + id));
            calenderEventRepository.delete(event);
            resp.setStatusCode(200);
            resp.setData(event);
            resp.setMessage("Event Deleted Successfully.");
        } catch (CustomException e) {
            resp.setStatusCode(404);
            resp.setError(e.getMessage());
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes updateEvent(int id, CalenderEventDTO events) {
        ReqRes resp = new ReqRes();
        try {
            CalenderEventEntity calenderEvent = calenderEventRepository.findById(id).
                    orElseThrow(() -> new CustomException("Error: Event not found for this id : " + id));

            calenderEvent.setTitle(events.getTitle());
            calenderEvent.setDescription(events.getDescription());
            calenderEvent.setStartDateTime(events.getStartDateTime());
            calenderEvent.setEndDateTime(events.getEndDateTime());
            calenderEvent.setLocation(events.getLocation());
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

    public ReqRes changeIsAllDay(int id) {
        ReqRes resp = new ReqRes();

        try {
            CalenderEventEntity calenderEvent = calenderEventRepository.findById(id).
                    orElseThrow(() -> new CustomException("Error: Event not found for this id : " + id));
            calenderEvent.setAllDay(!calenderEvent.isAllDay());
            calenderEventRepository.save(calenderEvent);
            resp.setData(calenderEvent);
            resp.setMessage("Calendar event changed. ");
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> getSingleEvent(int id) {
        try {
            Optional<CalenderEventEntity> event = calenderEventRepository.findById(id);
            CalenderEventDTO calenderEventDTO = new CalenderEventDTO();
            if (event.isPresent()) {
                CalenderEventEntity calenderEvent = event.get();
                calenderEventDTO.setTitle(calenderEvent.getTitle());
                calenderEventDTO.setStartDateTime(calenderEvent.getStartDateTime());
                calenderEventDTO.setEndDateTime(calenderEvent.getEndDateTime());
                calenderEventDTO.setAllDay(calenderEvent.isAllDay());
                calenderEventDTO.setLocation(calenderEvent.getLocation());
                calenderEventDTO.setDescription(calenderEvent.getDescription());
                calenderEventDTO.setEmail(calenderEvent.getUser().getEmail());
                calenderEventDTO.setCreatedAt(calenderEvent.getCreatedAt());
                calenderEventDTO.setDeleted(calenderEvent.isDeleted());
            }
            return ReqRes.successResponse("Event fetch successfully.", calenderEventDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch events", e.getMessage()));
        }
    }

    public ResponseEntity<?> getEventsForToday() {

        try{
            LocalDate today = LocalDate.now();
            LocalDateTime startOfDay = today.atStartOfDay();
            LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
            List<CalenderEventEntity> events = calenderEventRepository.findByCreatedAtBetween(startOfDay, endOfDay).stream()
                    .map(event-> modelMapper.map(event, CalenderEventEntity.class))
                    .collect(Collectors.toList());
            return ReqRes.successResponse("Today's events fecthed successfully.",events);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ReqRes.successResponse("Failed to fetch events", e.getMessage()));
        }
    }

}

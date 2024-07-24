package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.CalenderEventDTO;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.BaseEntity;
import com.linkwithjs.simplenotesapi.entity.CalenderEventEntity;
import com.linkwithjs.simplenotesapi.repository.CalenderEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class CalenderEventService {

    @Autowired
    private CalenderEventRepository calenderEventRepository;

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
}

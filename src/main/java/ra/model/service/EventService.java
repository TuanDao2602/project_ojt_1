package ra.model.service;

import ra.model.entity.Event;

import java.util.List;

public interface EventService {
    List<Event>finAll();
    Event getById(int eventId);
    Event saveAndUpdate(Event event);
    void delete(int eventId);
    List<Event> searchByContentContainingOrEventId(String content, int eventId);
}

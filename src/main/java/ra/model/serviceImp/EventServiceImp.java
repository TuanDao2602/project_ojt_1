package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.entity.Event;
import ra.model.repository.EventRepository;
import ra.model.service.EventService;

import java.util.List;
@Service
public class EventServiceImp implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Override
    public List<Event> finAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(int eventId) {
        return eventRepository.findById(eventId).get();
    }

    @Override
    public Event saveAndUpdate(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(int eventId) {
        eventRepository.deleteById(eventId);

    }

    @Override
    public List<Event> searchByContentContainingOrEventId(String content, int eventId) {
        return eventRepository.searchByContentContainingOrEventId(content,eventId);
    }


}

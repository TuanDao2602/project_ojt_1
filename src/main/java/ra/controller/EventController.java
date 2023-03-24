package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Banner;
import ra.model.entity.Event;
import ra.model.service.EventService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("api/v1/auth/event")
public class EventController {
    @Autowired
    private EventService eventService;
    @GetMapping
    public List<Event> getAllEvent(){
        return eventService.finAll();
    }
    @GetMapping("/{eventId}")
    public Event getById(@PathVariable("eventId") int eventId){
        return eventService.getById(eventId);
    }
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Event event){
        Event eventNew =new Event();
        eventNew.setContent(event.getContent());
        eventNew.setImage(event.getImage());
        eventNew.setStatus(true);
        eventNew=eventService.saveAndUpdate(eventNew);
        return ResponseEntity.ok(eventNew);
    }
    @PutMapping("/update/{eventId}")
    public Event update(@PathVariable("eventId")int eventId,@RequestBody Event event){
        Event eventUpdate = eventService.getById(eventId);
        eventUpdate.setStatus(event.isStatus());
        eventUpdate.setImage(event.getImage());
        eventUpdate.setContent(event.getContent());

        return eventService.saveAndUpdate(eventUpdate);
    }
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> delete(@PathVariable("eventId")int eventId){
        eventService.delete(eventId);
        return ResponseEntity.ok("đã xóa thành công");
    }
    @GetMapping("/search")
    public ResponseEntity<List<Event>>SearchByNameOrId(@RequestParam("content")String content,
                                                        @RequestParam("eventId")int eventId){
        List<Event>eventList=eventService.searchByContentContainingOrEventId(content,eventId);
        return new ResponseEntity<>(eventList, HttpStatus.OK);

    }

}

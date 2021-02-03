package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

//@RestController includes the ResponseBody annotation which automatically serializes objects into JS when they are returned from a handler method
@RestController
@RequestMapping("/time-entries") //because the urls in the apitest cases are all the same
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;

    //constructor
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    //PostMapping here because we are hitting postForEntitiy (line 35 in apitest)
    @PostMapping //(maps with @RequestBody)
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.created(null).body(timeEntry);

    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
        // initialize TimeEntry object,
        // the find is indicated in the test function, and we pass the timeEntryId into it
        TimeEntry timeEntryRead = timeEntryRepository.find(timeEntryId);
        if(timeEntryRead != null) {
            //check the assertion test to see which function to use when accessing ResponseEntity
            return ResponseEntity.ok().body(timeEntryRead);
        }
        else
            //In some cases we dont want to return anything with the function so we directly access .build
            return ResponseEntity.notFound().build();
    }


    @GetMapping //because there isnt any special url
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntry = timeEntryRepository.list();
        return ResponseEntity.ok().body(timeEntry);
    }

    @PutMapping("{id}")
    //@RequestBody -> use this for objects
    //@PathVariable -> use this for variables
    public ResponseEntity<TimeEntry> update(@PathVariable("id") long timeEntryId,@RequestBody TimeEntry timeEntryToUpdate) {
        //name the object based on the response type
        TimeEntry timeEntryUpdate = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);
        if(timeEntryUpdate != null) {
            return ResponseEntity.ok().body(timeEntryUpdate);
        }
        else
            return  ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long timeEntryId) {
         timeEntryRepository.delete(timeEntryId);
         return ResponseEntity.noContent().build();
    }
}

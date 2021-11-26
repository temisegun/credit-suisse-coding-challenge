package com.devpractises;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

public class LogFileParser {

    private String filePath;
    private JSONParser jsonParser;
    private String[] extractAttributes;

    public LogFileParser(String filePath) {
        this.filePath = filePath;
        this.jsonParser = new JSONParser();
        this.extractAttributes = new String[]{"id", "state", "type", "host", "timestamp"};
    }

    public HashMap<String, Event> parseFile() throws IOException {
        Stream<String> logs = Files.lines(Paths.get(filePath));
        HashMap<String, Event> events = new HashMap<>();

        logs.parallel().forEach(log -> {
            Optional<JSONObject> eventLog = Optional.empty();
            try {
                JSONObject logEntry = (JSONObject) jsonParser.parse(log);
                eventLog = Optional.ofNullable(logEntry);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            eventLog.ifPresent(data -> {
                HashMap<String, Optional<Object>> eventAttributes = new HashMap<>();
                for (String attr : extractAttributes) {
                    Object eventAttr = data.get(attr);
                    eventAttributes.put(attr, Optional.ofNullable(eventAttr));
                }

                //event log must have id, otherwise should throw exception
                String eventId = (String) eventAttributes.get("id").get();

                if(events.containsKey(eventId)) {
                    Event x = events.get(eventId);
                    x.updateEvent(eventId, eventAttributes);
                } else {
                    Event x = new Event(eventId, eventAttributes);
                    events.put(eventId, x);
                }
            });
        });

        return events;

    }
}

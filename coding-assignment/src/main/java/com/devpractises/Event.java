package com.devpractises;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class Event {

    private String eventId;
    private String type;
    private String host;
    private Long startTimestamp;
    private Long endTimestamp;
    private Long eventDuration;
    private boolean alert;

    enum State {
        STARTED,
        FINISHED,
    }


    public Event(String eventId, HashMap<String, Optional<Object>> eventAttributes) {
        this.eventId = eventId;
        adoptAttributes(eventAttributes);

    }

    public void updateEvent(String eventId, HashMap<String, Optional<Object>> eventAttributes) {
        //ensuring it is the correct event before we update info
        if(this.eventId.equals(eventId)) {
            adoptAttributes(eventAttributes);
            updateEventDuration();

        }
    }

    void adoptAttributes(HashMap<String, Optional<Object>> eventAttributes) {
        //ensuring state is either of value started or finished
        Predicate<String> startedOrFinished = x -> x.equals("STARTED") || x.equals("FINISHED");
        Optional<State> state = eventAttributes.get("state").map(x -> (String) x).filter(startedOrFinished).map(x -> State.valueOf(x));

        eventAttributes.get("timestamp").ifPresent(timestamp -> {
            state.ifPresent(value -> {
                switch (value) {
                    case STARTED:
                        startTimestamp = (Long) timestamp;
                        break;
                    case FINISHED:
                        endTimestamp = (Long) timestamp;
                        break;
                    default:
                }

            });
        });

        eventAttributes.get("type").ifPresent(value -> type = (String) value);
        eventAttributes.get("host").ifPresent(value -> host = (String) value);
    }

    void updateEventDuration() {
        //when updating, checking whether both start and endTimestamps have values to calculate other info
        Long timestamps[] = {startTimestamp,endTimestamp};
        boolean sufficientData = Arrays.stream(timestamps).map(x -> Optional.ofNullable(x)).allMatch(x -> x.isPresent());
        if(sufficientData) {
            setEventDuration(endTimestamp - startTimestamp);
            setAlert(eventDuration > 4 ? true : false);
        }
    }

    public void setEventDuration(Long eventDuration) {
        this.eventDuration = eventDuration;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getEventId() {
        return eventId;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public Long getEventDuration() {
        return eventDuration;
    }

    public boolean getAlert() {
        return alert;
    }

    public String toString() {
        return "Event id: " + getEventId()
                + ", startTimestamp: " + getStartTimestamp()
                + ", endTimestamp: " + getEndTimestamp()
                + ", eventDuration: " + getEventDuration() + " ms"
                + ", alert: " + getAlert()
                + ", type: " + getType()
                + ", host: " + getHost();
    }

}

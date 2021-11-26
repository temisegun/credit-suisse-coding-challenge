package com.devpractises;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

class EventTest {

    @Mock
    HashMap<String, Optional<Object>> mockEventAttributes = mock(HashMap.class);

    @Mock
    HashMap<String, Optional<Object>> mockUpdatedEventAttributes = mock(HashMap.class);

    @Test
    void updateEvent() {
    }

    @Test
    void setEventDuration() {
    }

    @Test
    void setAlert() {
    }

    @Test
    void getEventId() {
    }

    @Test
    void getType() {
    }

    @Test
    void getHost() {
    }

    @Test
    void getStartTimestamp() {
    }

    @Test
    void getEndTimestamp() {
    }

    @Test
    void getEventDuration() {
    }

    @Test
    void getAlert() {
    }
}
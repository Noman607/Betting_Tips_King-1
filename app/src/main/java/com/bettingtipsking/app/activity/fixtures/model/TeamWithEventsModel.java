package com.bettingtipsking.app.activity.fixtures.model;

import java.util.List;

public class TeamWithEventsModel {
    String id;
    String name;
    String logo;
    List<EventsModel> eventsList;

    public TeamWithEventsModel(String id, String name, String logo, List<EventsModel> eventsList) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.eventsList = eventsList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<EventsModel> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<EventsModel> eventsList) {
        this.eventsList = eventsList;
    }
}

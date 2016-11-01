package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.StateObject

interface EventData {
    String KEY = 'EVENT_DATA'
    StateObject getEntity();
    String getFromUser();
    String getComment();
    UUID getWorkitemId();
    String getIpAddress();
}
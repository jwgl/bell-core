package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

interface EventData {
    String KEY = 'EVENT_DATA'
    String getFromUser();
    String getIpAddress();
    IStateObject getEntity();
    UUID getWorkitemId();
}
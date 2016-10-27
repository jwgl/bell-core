package cn.edu.bnuz.bell.workflow.events

import cn.edu.bnuz.bell.workflow.IStateObject

interface EventData {
    String KEY = 'EVENT_DATA'
    IStateObject getEntity();
    String getFromUser();
    String getComment();
    UUID getWorkitemId();
    String getIpAddress();
}
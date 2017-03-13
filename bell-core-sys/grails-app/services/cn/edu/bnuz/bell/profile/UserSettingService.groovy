package cn.edu.bnuz.bell.profile

import cn.edu.bnuz.bell.config.ConfigDataType
import cn.edu.bnuz.bell.security.User
import grails.converters.JSON
import grails.transaction.Transactional
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.time.LocalDate
import java.time.LocalTime

@Transactional
class UserSettingService {
    Integer get(String userId, String key, Integer defaultValue) {
        getValue(userId, key, ConfigDataType.INTEGER, defaultValue)
    }

    void set(String userId, String key, Integer value) {
        setValue(userId, key, ConfigDataType.INTEGER, value)
    }

    BigDecimal getDecimal(String userId, String key) {
        getValue(userId, key, ConfigDataType.FLOAT, 0.0)
    }

    BigDecimal get(String userId, String key, BigDecimal defaultValue) {
        get(userId, key, defaultValue)
    }

    void set(String userId, String key, BigDecimal value) {
        setValue(userId, key, ConfigDataType.FLOAT, value)
    }

    Boolean get(String userId, String key, Boolean defaultValue) {
        getValue(userId, key, ConfigDataType.BOOLEAN, defaultValue)
    }

    void set(String userId, String key, Boolean value) {
        setValue(userId, key, ConfigDataType.BOOLEAN, value)
    }

    String get(String userId, String key, String defaultValue) {
        getValue(userId, key, ConfigDataType.STRING, defaultValue)
    }

    void set(String userId, String key, String value) {
        setValue(userId, key, ConfigDataType.STRING, value)
    }

    LocalDate get(String userId, String key, LocalDate defaultValue) {
        getValue(userId, key, ConfigDataType.DATE, defaultValue)
    }

    void set(String userId, String key, LocalDate value) {
        setValue(userId, key, ConfigDataType.DATE, value)
    }

    LocalTime get(String userId, String key, LocalTime defaultValue) {
        getValue(userId, key, ConfigDataType.TIME, defaultValue)
    }

    void set(String userId, String key, LocalTime value) {
        setValue(userId, key, ConfigDataType.TIME, value)
    }

    Map get(String userId, String key, Map defaultValue) {
        getValue(userId, key, ConfigDataType.MAP, defaultValue)
    }

    void set(String userId, String key, Map value) {
        setValue(userId, key, ConfigDataType.MAP, value)
    }

    private <T> T getValue(String userId, String key, char type, T defaultValue) {
        User user = User.load(userId)
        UserSetting setting = UserSetting.get(new UserSetting(user: user, key: key))
        if (setting) {
            assert setting.type == type
            switch (type) {
                case ConfigDataType.DATE:
                    return LocalDate.parse(setting.value) as T
                case ConfigDataType.TIME:
                    return LocalTime.parse(setting.value) as T
                case ConfigDataType.MAP:
                    return new JsonSlurper().parseText(setting.value) as T
                default:
                    return setting.value as T
            }
        } else {
            defaultValue
        }
    }

    private <T> void setValue(String userId, String key, char type, T value) {
        User user = User.load(userId)
        String valueString
        switch (type) {
            case ConfigDataType.MAP:
                valueString = JsonOutput.toJson(value)
                break
            default:
                valueString = value.toString()
        }

        UserSetting setting = UserSetting.get(new UserSetting(user: user, key: key))
        if (setting) {
            assert setting.type == type
            setting.value = valueString
        } else {
            setting = new UserSetting(
                    user: user,
                    key: key,
                    type: type,
                    value: valueString
            )
        }
        setting.save()
    }
}

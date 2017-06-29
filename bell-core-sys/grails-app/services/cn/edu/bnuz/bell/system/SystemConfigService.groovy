package cn.edu.bnuz.bell.system

import cn.edu.bnuz.bell.config.ConfigDataType
import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic

import java.time.LocalDate
import java.time.LocalTime

@CompileStatic
@Transactional
class SystemConfigService {

    Integer get(String key, Integer defaultValue) {
        getValue(key, ConfigDataType.INTEGER, defaultValue)
    }

    void set(String key, Integer value) {
        setValue(key, ConfigDataType.INTEGER, value)
    }

    BigDecimal get(String key, BigDecimal defaultValue) {
        getValue(key, ConfigDataType.FLOAT, defaultValue)
    }

    void set(String key, BigDecimal value) {
        setValue(key, ConfigDataType.FLOAT, value)
    }

    Boolean get(String key, Boolean defaultValue) {
        getValue(key, ConfigDataType.BOOLEAN, defaultValue)
    }

    void set(String key, Boolean value) {
        setValue(key, ConfigDataType.BOOLEAN, value)
    }

    String get(String key, String defaultValue) {
        getValue(key, ConfigDataType.STRING, defaultValue)
    }

    void set(String key, String value) {
        setValue(key, ConfigDataType.STRING, value)
    }

    LocalDate get(String key, LocalDate defaultValue) {
        getValue(key, ConfigDataType.DATE, defaultValue)
    }

    void set(String key, LocalDate value) {
        setValue(key, ConfigDataType.DATE, value)
    }

    LocalTime get(String key, LocalTime defaultValue) {
        getValue(key, ConfigDataType.TIME, defaultValue)
    }

    void set(String key, LocalTime value) {
        setValue(key, ConfigDataType.TIME, value)
    }

    Map get(String key, Map defaultValue) {
        getValue(key, ConfigDataType.MAP, defaultValue)
    }

    void set(String key, Map value) {
        setValue(key, ConfigDataType.MAP, value)
    }

    private <T> T getValue(String key, char type, T defaultValue) {
        SystemConfig config = SystemConfig.get(key)
        if (config) {
            assert config.type == type
            switch (type) {
                case ConfigDataType.DATE:
                    return LocalDate.parse(config.value) as T
                case ConfigDataType.TIME:
                    return LocalTime.parse(config.value) as T
                case ConfigDataType.MAP:
                    return new JsonSlurper().parseText(config.value) as T
                default:
                    return config.value as T
            }
        } else {
            defaultValue
        }
    }

    private <T> void setValue(String key, char type, T value) {
        String valueString
        switch (type) {
            case ConfigDataType.MAP:
                valueString = JsonOutput.toJson(value)
                break
            default:
                valueString = value.toString()
        }

        SystemConfig config = SystemConfig.get(key)
        if (config) {
            assert config.type == type
            config.value = valueString
            config.save()
        } else {
            throw new Exception("Requested key: ${key} does not exists")
        }
    }
}

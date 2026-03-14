package com.leon.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 地址簿实体
 */
@Data
public class AddressBook {
    private Integer id;
    private Integer studentId;
    private String contactName;
    private String contactPhone;
    private String campusArea;
    private String building;
    private String room;
    private String fullAddress;
    private String addressTag;
    /**
     * 是否默认地址：0-否 1-是
     */
    private Integer isDefault;
    private BigDecimal latitude;
    private BigDecimal Integeritude;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

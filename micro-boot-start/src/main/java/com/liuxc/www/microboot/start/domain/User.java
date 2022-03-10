package com.liuxc.www.microboot.start.domain;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class User {
    @XmlElement
    private Integer id;
    @XmlElement
    private String userName;
    @XmlElement
    private String passWord;
}

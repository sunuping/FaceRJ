package com.sunup.education.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lime
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Slf4j
public class Msg {
    private int code;
    private int count;
    private String msg;
    private Object data;
}

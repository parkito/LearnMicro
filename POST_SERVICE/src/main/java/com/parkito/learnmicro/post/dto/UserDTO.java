package com.parkito.learnmicro.post.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Artem Karnov @date 11/6/2017.
 * artem.karnov@t-systems.com
 */
@Builder
@Data
public class UserDTO implements Serializable {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> serials;
}

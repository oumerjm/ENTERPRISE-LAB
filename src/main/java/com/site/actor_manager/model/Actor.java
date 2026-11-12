package com.site.actor_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Actor {
    private String name;
    private String movieName;
    private int id;
}

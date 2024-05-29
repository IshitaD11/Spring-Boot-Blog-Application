package com.project.springbootblogapplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
public class Authority extends BaseModel implements Serializable {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long authorityId;

    @Enumerated(EnumType.ORDINAL)
    private AuthorityType authorityName;

    @ManyToMany(mappedBy = "authorities")
    private List<User> users = new ArrayList<>();

    @Override
    public String toString(){
        return "Authority{" + "name='" + authorityName + "'" + "}";
    }

}
